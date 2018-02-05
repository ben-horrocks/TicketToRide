package cs340.TicketClient.common.DataModels;

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

  public Game(Player startingPlayer){
    name = startingPlayer.getName() + "\'s Game";
    creator = startingPlayer;
    currentPlayers.add(startingPlayer);
  }

  public Game(String name, Player startingPlayer){
    this.name = name;
    this.creator = startingPlayer;
    this.currentPlayers.add(startingPlayer);
  }

  public void startGame()
  {
    gameStarted = true;
  }

  public String getName(){
    return name;
  }

  public String getCreatorName(){
    return creator.getName().getName(); //creator.getName() returns a Username not a String
  }

  public GameID getID() {
    return this.id;
  }

  public Set<Player> getPlayers() {
    return this.currentPlayers;
  }
  public Boolean isGameFull()
  {
    if(currentPlayers.size()==5) {
      return true;
    }
    return false;
  }
}
