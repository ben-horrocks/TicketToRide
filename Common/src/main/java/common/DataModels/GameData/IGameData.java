package common.DataModels.GameData;

import java.util.List;

import common.DataModels.*;

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
