package cs340.TicketClient.DataModels;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Game
{
  private Set<Player> currentPlayers = new HashSet<>();
  private GameID id = new GameID();
  private boolean gameStarted = false;

  public Game(Player startingPlayer)
  {
    currentPlayers.add(startingPlayer);
  }

  public void startGame()
  {
    gameStarted = true;
  }

}
