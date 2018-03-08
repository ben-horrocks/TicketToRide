package cs340.TicketClient.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import common.DataModels.ChatItem;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.Player;
import common.DataModels.GameData.PlayerColor;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;
import common.DataModels.TrainColor;
import common.DataModels.Username;

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

	public HandDestinationCards getDestinationCards() { return model.getInitialDCards(); }

	void test()
	{

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run()
			{

				/*
				displayPlayerColors();
				displayPlayerTurnOrder();
				displayPlayerHand();
				displayOpponentHandSizes();
				*/
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
		Queue<Player> queue = model.getGameData().getTurnQueue();
		Player[] players = queue.toArray(new Player[queue.size()]);
		activity.displayPlayerOrder(players);
	}

	/**
	 * Display the current player's cards. Mainly for testing.
	 */
	private void displayPlayerHand()
	{
		List<TrainCard> trainCards = model.getPlayer().getHand();
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
}
