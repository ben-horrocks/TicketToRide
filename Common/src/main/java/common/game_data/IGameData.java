package common.game_data;

import java.util.List;

import common.cards.DestinationCard;
import common.cards.TrainCard;
import common.chat.ChatItem;
import common.history.HistoryItem;
import common.map.Edge;
import common.player_info.Username;

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
