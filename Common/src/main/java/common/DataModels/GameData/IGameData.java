package common.DataModels.GameData;

import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.Edge;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;
import common.DataModels.Username;

/**
 * Created by Kavika F.
 */

public interface IGameData
{
	void edgeClaimed(Edge edge);
	void deckDraw(Username username, List<TrainCard> drawn);
	void faceUpDraw(Username username, List<TrainCard> drawn, List<TrainCard> replacements);
	void destinationDraw(Username username, List<DestinationCard> drawn);
	void addHistoryItem(HistoryItem historyItem);
	void addChatMessage(ChatItem chatItem);
}
