package cs340.TicketClient.Game;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import common.DataModels.ChatItem;
import common.DataModels.DestDrawRequest;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.Player;
import common.DataModels.GameData.PlayerColor;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.TrainCard;
import common.DataModels.TrainColor;
import common.DataModels.Username;
import cs340.TicketClient.CardFragments.DestinationCardFragment;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.R;

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
				updatePlayerPoints();

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

	private void updatePlayerPoints()
	{
		model.getPlayer().addPoints(250);
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
			FragmentManager manager = activity.getFragmentManager();
			Fragment fragment = manager.findFragmentById(R.id.fragment_destination_card);
			manager.beginTransaction().add(R.id.fragment_map, fragment)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
		}
	}
}
