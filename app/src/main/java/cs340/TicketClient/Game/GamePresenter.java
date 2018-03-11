package cs340.TicketClient.Game;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import common.CommandParams;
import common.DataModels.ChatItem;
import common.DataModels.DestDrawRequest;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.PlayerColor;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.GameData.TurnQueue;
import common.DataModels.HandDestinationCards;
import common.DataModels.HandTrainCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.SignalType;
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
				updateTrainCardsOfOtherPlayers(-1);
				updateDestinationCardsOfOtherPlayers(2);

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


	private void updatePlayerPoints(int number)
	{
		model.addPoints(number);
	}

	private void addRemoveTrainCardsOfPlayer() {
		model.removeTrainCard();
	}

	private void addRemoveDestinationCardsOfPlayer() {
		model.removeDestCard();
	}

	private void updateTrainCardsOfOtherPlayers(int number) {

		model.addTrainToOpponant(number);

	}
	private void updateDestinationCardsOfOtherPlayers(int number) {
		model.addDestToOpponant(number);
	}

	private void updateVisibleAndInvisibleCardsInTrainCardDeck() {

	}

	private void updateNumOfCardsInDestinationDeck(int number) {
			model.updateDDeckCount(number);
	}

	private void AddClaimedRoute() {
		//TODO all of this function

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
				activity.startDestinationFragement((HandDestinationCards) signal.getObject());
			}else{
				System.out.println(signal.getObject());
			}

		}
	}
}
