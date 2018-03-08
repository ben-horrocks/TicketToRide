package common;

import java.util.List;

import common.DataModels.GameInfo;
import common.DataModels.Signal;
import common.DataModels.GameData.StartGamePacket;
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
     * @param id a particular game id is provided to identify which game has been started
     */
    Signal startGame(StartGamePacket packet);

    /**
     * A function call for all clients to update the amount of destination cards of an opponent
     * @param name the username for the opponent who drew the card(s)
     * @param amount the amount of cards drawn
     */
    Signal playerDrewDestinationCards(Username name, int amount);
}
