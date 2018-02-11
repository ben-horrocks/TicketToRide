package cs340.TicketClient.Communicator;

import java.util.List;

import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.IClient;
import cs340.TicketClient.Lobby.LobbyPresenter;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ClientFacade implements IClient
{
  @Override
  public void updateGameList(List<GameInfo> gameList) {
    LobbyPresenter.getInstance().addGames(gameList);
  }

  @Override
  public void startGame(GameID id) {

  }

}
