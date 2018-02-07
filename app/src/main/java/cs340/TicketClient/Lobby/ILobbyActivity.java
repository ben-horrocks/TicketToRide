package cs340.TicketClient.Lobby;

import java.util.List;

import common.DataModels.GameInfo;

/**
 * Created by Ryan on 2/7/2018.
 */

public interface ILobbyActivity {
    public void updateGameList(List<GameInfo> gameList);
    public String getSearchQuery();
}
