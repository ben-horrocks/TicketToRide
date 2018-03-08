package cs340.TicketClient.Game;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.GameData.ClientGameData;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.Player;
import common.DataModels.GameID;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import cs340.TicketClient.GameMenu.ChatPresenter;
import cs340.TicketClient.GameMenu.HistoryPresenter;

public class GameModel
{
	private ClientGameData gameData;
	private static GameModel singleton;
	private HandDestinationCards initialDCards;

	public static GameModel getInstance()
	{
		if (singleton == null)
			singleton = new GameModel();
		return singleton;
	}

	private GameModel() {}

	public void setGameData(ClientGameData gameData) { this.gameData = gameData; }

	public ClientGameData getGameData() { return gameData; }

	public void setInitialDCards(HandDestinationCards initialDCards) {
		this.initialDCards = initialDCards;
	}

	public HandDestinationCards getInitialDCards()
	{
		HandDestinationCards cards = initialDCards;
		this.clearDCards();
		return cards;
	}

	public void clearDCards() { initialDCards = null; }

	public List<ChatItem> getChatMessages() { return gameData.getChat(); }

	public void setChatMessages(List<ChatItem> chats)
	{
		for(ChatItem item : chats)
		{
			gameData.addChatMessage(item);
			if (ChatPresenter.getSINGLETON() != null) {
				ChatPresenter.getSINGLETON().updateChatList();
			}
		}
	}

	public List<HistoryItem> getPlayHistory() { return gameData.getHistory(); }

	public void setPlayHistory(List<HistoryItem> history)
	{
		for (HistoryItem event : history)
		{
			gameData.addHistoryItem(event);
			if (HistoryPresenter.getSINGLETON() != null) {
				HistoryPresenter.getSINGLETON().updateHistoryList();
			}
		}
	}

	public Player getPlayer() { return gameData.getPlayer(); }

	public List<Opponent> getOpponents() { return gameData.getOpponents(); }

	public GameID getGameID() { return gameData.getId(); }

	public Player whoseTurn() { return gameData.whoseTurn(); }

	public boolean isMyTurn() { return whoseTurn().equals(getPlayer()); }
}
