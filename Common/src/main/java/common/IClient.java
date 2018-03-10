package common;

import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.GameInfo;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.TrainCard;
import common.DataModels.Username;

public interface IClient {

    /**
     * A function call for all clients to update their game list with the provided game list
     * @param gameList list of games to be pushed to all clients waiting in the lobby
     */
    Signal updateGameList(List<GameInfo> gameList);

    /**
     * A function call for only the clients that have joined the particular game of the provided
     * game id
     * @param packet The packet holding all necessary information for a client to start a game.
     */
    Signal startGame(StartGamePacket packet);

    /**
     * A function call for all clients to update the amount of destination cards of an opponent
     * @param name the username for the opponent who drew the card(s)
     * @param amount the amount of cards drawn
     */
    Signal opponentDrewDestinationCards(Username name, int amount);

    Signal opponentDrewFaceUpCard(Username name, int index, TrainCard replacement);

    Signal opponentDrewDeckCard(Username name);

    Signal playerDrewDestinationCards(Username name, HandDestinationCards cards);

    Signal addChatItem(Username name, ChatItem item);

    Signal addHistoryItem(Username name, HistoryItem item);
}
