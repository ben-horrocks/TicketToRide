package cs340.TicketClient.Game;

import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.GameData.ClientGameData;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.Player;
import common.DataModels.GameID;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;
import common.DataModels.Username;
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

	public void decrementDestinationCount(int count)
	{
		gameData.decDestinationCardsLeft(count);
	}

	public void setGameData(ClientGameData gameData) { this.gameData = gameData; }

	public ClientGameData getGameData() { return gameData; }

	public void setInitialDCards(HandDestinationCards initialDCards) {
		this.initialDCards = initialDCards;
	}

	public void replaceFaceUp(int index, TrainCard replacement)
	{
		gameData.getFaceUp().set(index, replacement);
	}

	public void addChatItem(ChatItem item)
	{
		gameData.getChat().add(item);
		if (ChatPresenter.getSINGLETON() != null) {
			ChatPresenter.getSINGLETON().updateChatList();
		}
	}

	public void addHistoryItem(HistoryItem item)
	{
		gameData.getHistory().add(item);
		if (HistoryPresenter.getSINGLETON() != null) {
			HistoryPresenter.getSINGLETON().updateHistoryList();
		}
	}
	public HandDestinationCards getInitialDCards()
	{
		HandDestinationCards cards = initialDCards;
		return cards;
	}

	public void clearDCards() { initialDCards = null; }

	public List<ChatItem> getChatMessages() { return gameData.getChat(); }

	public void setChatMessages(List<ChatItem> chats)
	{
		for(ChatItem item : chats)
		{
			gameData.addChatMessage(item);

		}
	}

	public List<HistoryItem> getPlayHistory() { return gameData.getHistory(); }

	public void setPlayHistory(List<HistoryItem> history)
	{
		for (HistoryItem event : history)
		{
			gameData.addHistoryItem(event);

		}
	}

	public Player getPlayer() { return gameData.getPlayer(); }

	public List<Opponent> getOpponents() { return gameData.getOpponents(); }

	public GameID getGameID() { return gameData.getId(); }

	public Username whoseTurn() { return gameData.whoseTurn(); }

	public boolean isMyTurn() { return whoseTurn().equals(getPlayer().getUser().getUsername()); }

	public void addPoints(int number)
	{
		getPlayer().addPoints(number);
	}

	public void removeTrainCard()
	{
		getPlayer().getDestinationCards().remove(0);
	}
	public void removeDestCard()
	{
		getPlayer().getDestinationCards().remove(0);
	}

	public void addTrainToOpponant(int number)
	{
		getOpponents().get(0).addHandCard(number);
	}
	public void addDestToOpponant(int number)
	{
		getOpponents().get(0).addDestinationCards(number);
	}
}
