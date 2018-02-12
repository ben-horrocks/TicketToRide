package common;

import java.util.List;
import java.util.Set;

import common.DataModels.Game;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Signal;

public interface IClient {

    /**
     * A function call for all clients to update their game list with the provided game list
     * @param gameList list of games to be pushed to all clients waiting in the lobby
     */
    void updateGameList(List<GameInfo> gameList);

    /**
     * A function call for only the clients that have joined the particular game of the provided
     * game id
     * @param id a particular game id is provided to identify which game has been started
     */
    void startGame(GameID id);
}
