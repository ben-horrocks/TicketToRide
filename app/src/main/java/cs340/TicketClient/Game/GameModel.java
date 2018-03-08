package cs340.TicketClient.Game;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.EdgeGraph;
import common.DataModels.GameData.ClientGameData;
import common.DataModels.GameData.Opponent;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;

public class GameModel
{
	private ClientGameData gameData;
	private static GameModel singleton;
	private ArrayList<DestinationCard> initialDCards;

	public static GameModel getInstance()
	{
		if (singleton == null)
			singleton = new GameModel();
		return singleton;
	}

	private GameModel()
	{

	}

	public void setGameData(ClientGameData gameData) {
		this.gameData = gameData;
	}

	public ClientGameData getGameData() {
		return gameData;
	}

	public void setInitialDCards(ArrayList<DestinationCard> initialDCards) {
		this.initialDCards = initialDCards;
	}

	public ArrayList<DestinationCard> getInitialDCards() {
		return initialDCards;
	}

	public void clearDCards()
	{
		initialDCards = null;
	}

	public List<ChatItem> getChatMessages()
	{
		return gameData.getChatMessages();
	}

	public void setChatMessages(List<ChatItem> chats)
	{
		for(ChatItem item : chats)
		{
			gameData.addChatMessage(item);
		}
	}

	public List<HistoryItem> getPlayHistory()
	{
		return gameData.getHistory();
	}

	public void setPlayHistory(List<HistoryItem> history)
	{
		for (HistoryItem event : history)
		{
			gameData.addHistoryItem(event);
		}
	}
}
