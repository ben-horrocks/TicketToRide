package cs340.TicketClient.Game;

import android.app.Activity;

import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.HistoryItem;
import common.DataModels.TrainColor;

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

	void claimRoute(TrainColor color, int number) throws InsufficientCardsException
	{
		// TODO: implement
	}

	public List<ChatItem> getChatMessages() { return model.getChatMessages(); }

	public void setChatMessages(List<ChatItem> chatMessages) { model.setChatMessages(chatMessages); }

	public List<HistoryItem> getPlayHistory() { return model.getPlayHistory(); }

	public void setPlayHistory(List<HistoryItem> playHistory) { model.setPlayHistory(playHistory); }

	void test()
	{

	}
}
