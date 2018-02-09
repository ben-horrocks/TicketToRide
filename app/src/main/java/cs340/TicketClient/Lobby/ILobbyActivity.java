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

    /**
     * A method for getting the user's input from the filter box (search bar) on the lobby
     * @pre none
     * @post The return value will be the text in the filter box
     * @return The text in the filter box
     */
    public String getSearchQuery();
}
