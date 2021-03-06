package common.communication;

import java.util.List;

import common.cards.HandTrainCards;
import common.chat.ChatItem;
import common.game_data.*;
import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.history.HistoryItem;
import common.map.Edge;
import common.player_info.PlayerList;
import common.player_info.Username;

public interface IClient
{

    /**
     * A function call for all clients to update their game list with the provided game list
     *
     * @param gameList list of games to be pushed to all clients waiting in the lobby
     */
    Signal updateGameList(Username user, List<GameInfo> gameList);

    /**
     * A function call for only the clients that have joined the particular game of the provided
     * game id
     *
     * @param packet The packet holding all necessary information for a client to start a game.
     */
    Signal startGame(StartGamePacket packet);

    /**
     * A function call for all clients to update the amount of destination cards of an opponent
     *
     * @param amount the amount of cards drawn
     */
    Signal opponentDrewDestinationCards(Username recipient, Username opponent, int amount);

    Signal opponentDrewFaceUpCard(Username recipient, Username opponent, int index, TrainCard replacement);

    Signal opponentDrewDeckCard(Username recipient, Username opponent);

    Signal playerClaimedEdge(Username name, Edge edge);

    Signal playerDrewDestinationCards(Username name, HandDestinationCards cards, GameID gameID);

    Signal addChatItem(Username name, ChatItem item);

    Signal addHistoryItem(Username name, HistoryItem item);

    Signal lastTurn(Username name);

    Signal updateTurnQueue(Username username);

    Signal gameEnded(Username name, EndGame players);

    Signal startTurn(Username name);

    Signal resumeGame(Username username);

    Signal updateFaceUpCards(Username username, HandTrainCards newFaceUps);

    Signal EndGame(Username user, PlayerList players);
}
