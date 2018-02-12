package cs340.TicketClient.Lobby;

import java.util.List;

import common.DataModels.GameInfo;

public interface ILobbyActivity {
    /**
     * A method that passes in a list of GameInfo for the LobbyActivity to display
     * @pre none
     * @post The list of games in the lobby will be updated and displayed.
     *
     */
    public void updateGameList();
}
