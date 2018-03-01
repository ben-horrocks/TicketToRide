package common.DataModels.GameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.DataModels.DestinationCard;
import common.DataModels.Edge;
import common.DataModels.GameData.Decks.DestinationCardDeck;
import common.DataModels.GameData.Decks.TrainCardDeck;
import common.DataModels.GameID;
import common.DataModels.TrainCard;
import common.DataModels.User;

public class ServerGameData implements Serializable
{
  private static final Integer PLAYERLIMIT = 5;
  private String name;
  private User creator;
  private List<Player> players;
  private GameID id = new GameID();
  private boolean gameStarted = false;

  //private EdgeGraph gameboard
  private TrainCardDeck deck;
  private DestinationCardDeck destinations;
  //private History history
  //private Chat chat

  public ServerGameData(User startingUser)
  {
    this.name = startingUser.getUsername() + "\'s game";
    this.creator = startingUser;
    this.players = new ArrayList<Player>();
    this.players.add(new Player(startingUser, getNextColor()));
  }

  public ServerGameData(String name, User startingUser){
    this.name = name;
    creator = startingUser;
    this.players = new ArrayList<Player>();
    this.players.add(new Player(startingUser, getNextColor()));
  }

  public void startGame() {
    gameStarted = true;
  }

  public GameID getId() {
    return id;
  }

  public void setId(GameID id) {
    this.id = id;
  }

  public String getName(){
    return name;
  }

  public String getCreatorName() {
    return creator.getName();
  }

  public Set<User> getUsers() {
    HashSet<User> users = new HashSet<User>();
    for(Player p: players){
      users.add(p.getUser());
    }
    return users;
  }

  public boolean isGameStarted() {
    return gameStarted;
  }

  public void addPlayer(User user) {
    players.add(new Player(user, getNextColor()));
  }

  public boolean isGameFull()
  {
    return players.size() >= PLAYERLIMIT;
  }

  public void edgeClaimed(Edge edge){
    User owner = edge.getOwner();
    getPlayer(owner).claimedEdge(edge);
    //TODO update gameboard with the modified edge
  }

  public void playerDrewTrainCard(String username, List<TrainCard> drawn){
    getPlayer(username).drewTrainCards(drawn);
  }

  public void playerDrewDestinationCard(String username, List<DestinationCard> drawn, List<DestinationCard> returned){
    getPlayer(username).drewDestinationCards(drawn);
    //TODO implement returning cards to the destination deck -> talk to Ben
  }

  //TODO public void addHistoryItem(GameEvent event){}
  //TODO public void addChatMessage(Message message){}

  private Player getPlayer(String name){
    for(Player p: players){
      if(p.getName() == name)
        return p;
    }
    return null;
  }

  private Player getPlayer(User user){
    for(Player p: players){
      if(p.getUser().getName().equals(user.getName()))
        return p;
    }
    return null;
  }

  private PlayerColor getNextColor(){
    switch(players.size()){
      case 0:
        return PlayerColor.BLACK;
      case 1:
        return PlayerColor.BLUE;
      case 2:
        return PlayerColor.GREEN;
      case 3:
        return PlayerColor.RED;
      default:
        return PlayerColor.YELLOW;
    }
  }
}