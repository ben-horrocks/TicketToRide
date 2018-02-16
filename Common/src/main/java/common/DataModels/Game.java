package common.DataModels;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Game implements Serializable
{
  private static final Integer PLAYERLIMIT = 5;
  private String name;
  private Player creator;
  private Set<Player> currentPlayers = new HashSet<>();
  private GameID id = new GameID();
  private boolean gameStarted = false;

  public Game(Player startingPlayer)
  {
    name = startingPlayer.getUsername() + "\'s game";
    creator = startingPlayer;
    currentPlayers.add(startingPlayer);
  }

  public Game(String name, Player startingPlayer){
    this.name = name;
    creator = startingPlayer;
    currentPlayers.add(startingPlayer);
  }

  public void startGame() { gameStarted = true; }

  public GameID getId() { return id; }

  public void setId(GameID id) { this.id = id; }

  public String getName(){ return name;}

  public String getCreatorName() {return creator.getName();}

  public Set<Player> getPlayers() {return currentPlayers;}

  public boolean isGameStarted() { return gameStarted; }

  public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }

  public void addPlayer(Player player) { currentPlayers.add(player); }

  public boolean isGameFull()
  {
    return currentPlayers.size() < PLAYERLIMIT;
  }
}
