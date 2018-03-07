package cs340.TicketClient.Game;

import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.EdgeGraph;
import common.DataModels.GameData.Opponent;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;

public class GameModel
{
	List<Opponent> opponents;
	EdgeGraph gameBoard;
	List<TrainCard> faceUp;
	// History history;
	List<HistoryItem> playHistory;
	// Chat chat;
	List<ChatItem> chatMessages;

	boolean isValidDesinationCard(DestinationCard destinationCard)
	{
		// TODO: implement isValidDestinationCard
		return false;
	}

	boolean canDrawCard(TrainCard trainCard)
	{
		// TODO: implement canDrawCard
		return false;
	}

	public List<HistoryItem> getPlayHistory() {
		return playHistory;
	}

	public void setPlayHistory(List<HistoryItem> playHistory) {
		this.playHistory = playHistory;
	}

	public List<ChatItem> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<ChatItem> chatMessages) {
		this.chatMessages = chatMessages;
	}
}
