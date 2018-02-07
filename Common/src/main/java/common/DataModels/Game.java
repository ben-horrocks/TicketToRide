package common.DataModels;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Game
{
  private String name;
  private Player creator;
  private Set<Player> currentPlayers = new HashSet<>();
  private GameID id = new GameID();
  private boolean gameStarted = false;

  public Game(Player startingPlayer)
  {
    name = startingPlayer.getName() + "\'s game";
    creator = startingPlayer;
    currentPlayers.add(startingPlayer);
  }

  public Game(String name, Player startingPlayer){
    this.name = name;
    creator = startingPlayer;
    currentPlayers.add(startingPlayer);
  }

  public void startGame()
  {
    gameStarted = true;
  }

  public GameID getId() { return id; }

  public void setId(GameID id) { this.id = id; }

  public String getName(){ return name;

  public String getCreatorName() {return creator.getName().getName();}

  public Set<Player>getPlayers() {return currentPlayers;}

  public boolean isGameStarted() { return gameStarted; }

  public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }

}
