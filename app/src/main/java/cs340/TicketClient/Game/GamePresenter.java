package cs340.TicketClient.Game;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.CommandParams;
import common.DataModels.ChatItem;
import common.DataModels.City;
import common.DataModels.DestDrawRequest;
import common.DataModels.DestinationCard;
import common.DataModels.Edge;
import common.DataModels.GameData.CityName;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.PlayerColor;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.GameData.TurnQueue;
import common.DataModels.HandDestinationCards;
import common.DataModels.HandTrainCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.TrainCard;
import common.DataModels.TrainColor;
import common.DataModels.Username;
import cs340.TicketClient.Communicator.ServerProxy;

public class GamePresenter
{
	private static final String TAG = "GAME";
	private GameActivity activity;
	private GameModel model;

	class InsufficientCardsException extends Exception {}

	GamePresenter(GameActivity gameActivity)
	{
		this.activity = gameActivity;
		model = GameModel.getInstance();
	}

	void fillModel(StartGamePacket packet)
	{
		model.setGameData(packet.getClientGameData());
		model.setInitialDCards(packet.getInitialDestinationCards());
	}

	void claimRoute(TrainColor color, int number) throws InsufficientCardsException
	{
		// TODO: implement

	}

	public List<ChatItem> getChatMessages() { return model.getChatMessages(); }

	public void setChatMessages(List<ChatItem> chatMessages) { model.setChatMessages(chatMessages); }

	public List<HistoryItem> getPlayHistory() { return model.getPlayHistory(); }

	public void setPlayHistory(List<HistoryItem> playHistory) { model.setPlayHistory(playHistory); }

	public HandDestinationCards getDestinationCards() {
		if (model.getInitialDCards() != null)
		{
			HandDestinationCards cards = model.getInitialDCards();
			GameModel.getInstance().clearDCards();
			return cards;
		}
		else
		{
			DestDrawRequest request = new DestDrawRequest(GameModel.getInstance().getGameID(), GameModel.getInstance().getPlayer().getUser().getUsername());
			DrawDestinationTask task = new DrawDestinationTask(activity);
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
			return null;
		}
	}

	void test()
	{
		//TODO call make choices for all the chagnes FROM THE MODEL.(Observer/observable)
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run()
			{
				// Display setup information. Can be found in Players tab.
				displayPlayerColors();
				displayPlayerTurnOrder();
				displayPlayerHand();
				displayOpponentHandSizes();

				// Update player info
				updatePlayerPoints(25);
				addRemoveTrainCardsOfPlayer();
				addRemoveDestinationCardsOfPlayer();

				//Update Other Player Info
				updateTrainCardsOfOtherPlayers(20, 4);
				updateDestinationCardsOfOtherPlayers(4, 1);

				//Update Decks Info
				updateVisibleAndInvisibleCardsInTrainCardDeck();
				updateNumOfCardsInDestinationDeck(3);

				//Update Game Info
				AddClaimedRoute();
				AddChatMessages();
				AddGameHistoryEntries();
			}
		});
	}

	/**
	 * Display the color associated with each player. Mainly for testing.
	 */
	private void displayPlayerColors()
	{
		List<PlayerColor> playerColors = new ArrayList<>();
		List<Username> usernames = new ArrayList<>();
		playerColors.add(model.getPlayer().getColor());
		usernames.add(model.getPlayer().getUser().getUsername());
		List<Opponent> opponents = model.getOpponents();
		for (Opponent o : opponents)
		{
			playerColors.add(o.getColor());
			usernames.add(o.getUsername());
		}
		activity.displayColors(usernames, playerColors);
	}

	/**
	 * Display the turn order of the game. Mainly for testing.
	 */
	private void displayPlayerTurnOrder()
	{
		TurnQueue queue = model.getGameData().getTurnQueue();
		Username[] usernames = queue.toArray();
		activity.displayPlayerOrder(usernames);
	}

	/**
	 * Display the current player's cards. Mainly for testing.
	 */
	private void displayPlayerHand()
	{
		HandTrainCards trainCards = model.getPlayer().getHand();
		activity.displayPlayerTrainCards(trainCards);
	}

	/**
	 * Display the opponent's hand sizes. Can't access their cards from this player. Mainly for testing.
	 */
	private void displayOpponentHandSizes()
	{
		List<Opponent> opponents = model.getOpponents();
		activity.displayOpponentHandSize(opponents);
	}


	/**
	 * Add the given amount of points to the player's score. Update display. Mainly for testing.
	 * @param number How many points to increase the player's score by.
	 */
	private void updatePlayerPoints(int number)
	{
		int beforePoints = model.getPlayer().getScore();
		model.addPoints(number);
		String text = "Updated player points from " + beforePoints +
				" to " + model.getPlayer().getScore();
		activity.makeLargerToast(text);
	}

	/**
	 * Add and remove train cards from the player's hand. Update display. Mainly for testing.
	 */
	private void addRemoveTrainCardsOfPlayer()
	{
		model.addTrainCard(TrainColor.RED);
		String text = "Added 1 red train card to the hand.";
		activity.makeLargerToast(text);
		model.removeTrainCard();
		model.removeTrainCard();
		text = "Removed two cards from the hand.";
		activity.makeLargerToast(text);
	}

	/**
	 * Add and remove destination cards from the player's hand. Update display. Mainly for testing.
	 */
	private void addRemoveDestinationCardsOfPlayer()
	{
		model.addDestCard(new DestinationCard(CityName.KANSAS_CITY, CityName.NEW_YORK_CITY, 200));
		String text = "Added a fake destination card from Kansas City to New York for 200 points.";
		activity.makeLargerToast(text);
		model.addDestCard(new DestinationCard(CityName.DALLAS, CityName.SANTA_FE, 1));
		text = "Added a fake destination card from Dallas to Santa Fe for 1 point.";
		activity.makeLargerToast(text);
		model.removeDestCard();
		text = "Removed a destination card from the player.";
		activity.makeLargerToast(text);
	}

	/**
	 * Increases and decreases the number of cards the opponents have.
	 * @param increase The amount to increase their count by.
	 * @param decrease The amount to decrease their count by.
	 */
	private void updateTrainCardsOfOtherPlayers(int increase, int decrease)
	{
		model.addTrainToOpponent(increase);
		String text = "Increased opponent's number of train cards by " + increase;
		activity.makeLargerToast(text);
		model.removeTrainFromOpponent(decrease);
		text = "Decreased opponent's number of train cards by " + decrease;
		activity.makeLargerToast(text);
	}

	private void updateDestinationCardsOfOtherPlayers(int increase, int decrease)
	{
		model.addDestToOpponent(increase);
		String text = "Increased opponent's number of destination cards by " + increase;
		activity.makeLargerToast(text);
		model.removeDestFromOpponent(decrease);
		text = "Decreased opponent's number of destination cards by " + decrease;
		activity.makeLargerToast(text);
	}

	private void updateVisibleAndInvisibleCardsInTrainCardDeck()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Current Face Up Cards: ");
		List<TrainCard> cards = model.getGameData().getFaceUp();
		for (int i = 0; i < cards.size(); i++)
		{
			sb.append(cards.get(i));
			if (i + 1 < cards.size())
			{
				sb.append(", ");
			}
		}
		sb.append("\nCurrent Deck Size: ");
		sb.append(model.getTrainCardDeckSize());
		activity.makeLargerToast(sb.toString());

		// Draw Card from Deck
		TrainColor color = model.drawFromTrainCardDeck().getType();
		model.addTrainCard(color);
		String text = "Drew a " + color + " train card from the deck and updated deck size.";
		activity.makeLargerToast(text);

		color = model.drawFromFaceUp().getType();
		model.addTrainCard(color);
		text = "Drew a " + color + " train card from the face up pile, " +
				"updated face up list, and updated deck size.";
		activity.makeLargerToast(text);
	}

	private void updateNumOfCardsInDestinationDeck(int number) { model.updateDDeckCount(number); }

	private void AddClaimedRoute()
	{
		City duluthSub = new City(46.786672, -92.100485, CityName.DULUTH);
		List<Edge> duluthEdges = model.getGameData().getGameboard().getGraph().get(duluthSub);
		for (Edge e : duluthEdges)
		{
			e.setOwner(model.getPlayer());
		}
		String text = "Claimed all edges \"coming from\" Duluth";
		activity.makeLargerToast(text);
	}

	private void AddChatMessages() {
		ChatItem chatItem = new ChatItem(model.getPlayer(), "Test button");
		model.updateChat(chatItem);
	}

	private void AddGameHistoryEntries() {
		CommandParams cmd = new CommandParams("TEST", null, null);
		HistoryItem historyItem = new HistoryItem(cmd);
		model.updateHistory(historyItem);
	}

	class DrawDestinationTask extends AsyncTask<DestDrawRequest, Integer, Signal>
	{
		GameActivity activity;

		DrawDestinationTask(GameActivity activity)
		{
			this.activity = activity;
		}

		@Override
		protected Signal doInBackground(DestDrawRequest... destDrawRequests)
		{
			Signal s = ServerProxy.getInstance().drawDestinationCards(destDrawRequests[0].getId(), destDrawRequests[0].getUser());
			return s;
		}

		@Override
		protected void onPostExecute(Signal signal)
		{
			super.onPostExecute(signal);
			if(signal.getSignalType() == SignalType.OK) {
				activity.startDestinationFragment((HandDestinationCards) signal.getObject());
			}else{
				System.out.println(signal.getObject());
			}

		}
	}
}
