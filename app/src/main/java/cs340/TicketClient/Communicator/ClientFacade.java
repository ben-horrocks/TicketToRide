package cs340.TicketClient.Communicator;

import java.util.List;
import java.util.Set;

import common.DataModels.Game;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Signal;
import common.IClient;
import cs340.TicketClient.Lobby.LobbyPresenter;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ClientFacade implements IClient
{
  @Override
  public Signal updateGameList(List<GameInfo> gameList) {
    return null;
  }

}
