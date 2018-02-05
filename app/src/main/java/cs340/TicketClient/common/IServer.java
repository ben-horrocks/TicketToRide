package cs340.TicketClient.common;

import cs340.TicketClient.common.DataModels.Game;
import cs340.TicketClient.common.DataModels.GameID;

/**
 * Created by Kavika F.
 */

public interface IServer
{
  public Signal login(String username, String password);

  public Signal register(String username, String password, String displayName);

  public void startGame();

  public void addGame(Game newgame);

  public Signal JoinGame(GameID id);
}
