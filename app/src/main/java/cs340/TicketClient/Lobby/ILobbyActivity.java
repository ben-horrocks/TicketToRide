package cs340.TicketClient.Lobby;

import java.util.List;

import common.DataModels.GameInfo;

/**
 * Created by Ryan on 2/6/2018.
 */

public interface ILobbyActivity {
    /**
     * A method that passes in a list of GameInfo for the LobbyActivity to display
     * @pre none
     * @post The list of games in the lobby will be updated and displayed.
     *
     * @param gameList The new list of games to display
     */
    public void updateGameList(List<GameInfo> gameList);
}
