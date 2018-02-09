package cs340.TicketClient.Communicator;

import java.util.Set;

import common.DataModels.Game;
import common.DataModels.Signal;
import common.IClient;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ClientFacade implements IClient
{
  @Override
  public Signal updateGameList(Set<Game> gameList)
  {
    return null;
  }
}
