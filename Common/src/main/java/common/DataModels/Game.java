package common.DataModels;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Game implements Serializable
{
  private static final Integer PLAYERLIMIT = 5;
  private String name;
  private User creator;
  private Set<User> currentUsers = new HashSet<>();
  private GameID id = new GameID();
  private boolean gameStarted = false;

  public Game(User startingUser)
  {
    name = startingUser.getUsername() + "\'s game";
    creator = startingUser;
    currentUsers.add(startingUser);
  }

  public Game(String name, User startingUser){
    this.name = name;
    creator = startingUser;
    currentUsers.add(startingUser);
  }

  public void startGame() { gameStarted = true; }

  public GameID getId() { return id; }

  public void setId(GameID id) { this.id = id; }

  public String getName(){ return name;}

  public String getCreatorName() {return creator.getName();}

  public Set<User> getPlayers() {return currentUsers;}

  public boolean isGameStarted() { return gameStarted; }

  public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }

  public void addPlayer(User user) { currentUsers.add(user); }

  public boolean isGameFull()
  {
    return currentUsers.size() >= PLAYERLIMIT;
  }
}
