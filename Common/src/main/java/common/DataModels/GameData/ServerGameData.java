package common.DataModels.GameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.Edge;
import common.DataModels.EdgeGraph;
import common.DataModels.GameData.Decks.DestinationCardDeck;
import common.DataModels.GameData.Decks.TrainCardDeck;
import common.DataModels.GameID;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;
import common.DataModels.User;
import common.DataModels.Username;

public class ServerGameData
{
	private static final Integer PLAYER_LIMIT = 5;
	private String name;
	private User creator;
	private List<Player> players;
	private Queue<Player> turnList;
	private GameID id = new GameID();
	private boolean gameStarted = false;

	private EdgeGraph gameBoard;
	private TrainCardDeck deck;
	private DestinationCardDeck destinations;
	private List<HistoryItem> history;
	private List<ChatItem> chat;

	public ServerGameData(User startingUser)
	{
		this.name = startingUser.getUsername() + "\'s game";
		this.creator = startingUser;
		this.players = new ArrayList<>();
		this.players.add(new Player(startingUser, getNextColor()));
		this.deck = new TrainCardDeck();
		this.destinations = new DestinationCardDeck();
	}

	public ServerGameData(String name, User startingUser)
	{
		this.name = name;
		creator = startingUser;
		this.players = new ArrayList<>();
		this.players.add(new Player(startingUser, getNextColor()));
		this.deck = new TrainCardDeck();
		this.destinations = new DestinationCardDeck();
	}

	private void createTurnList()
	{
		turnList = new ArrayBlockingQueue<>(players.size());
		turnList.addAll(players);
	}

	public void startGame()
	{
		createTurnList();
		gameStarted = true;
	}

	public GameID getId() { return id; }

	public void setId(GameID id) { this.id = id; }

	public String getName() { return name; }

	public String getCreatorName() { return creator.getStringUserName(); }

	public Set<User> getUsers()
	{
		HashSet<User> users = new HashSet<>();
		for (Player p : players)
		{
			users.add(p.getUser());
		}
		return users;
	}

	public EdgeGraph getGameBoard()
	{
		return gameBoard;
	}

	public List<TrainCard> getFaceUpCards()
	{
		return deck.getFaceUpCards();
	}

	public List<HistoryItem> getHistory()
	{
		return history;
	}

	public TrainCard drawFromTrainDeck()
	{
	  return deck.drawFaceDown();
	}

	public TrainCard faceUpDraw(int index)
	{
	  return deck.drawFaceUp(index);
	}

	public List<DestinationCard> destinationDraw()
	{
	  return destinations.draw();
	}

	public void edgeClaimed(Edge edge)
	{
		User owner = edge.getOwner();
		getPlayer(owner).claimedEdge(edge);
		//TODO update gameBoard with the modified edge
	}

	public void addHistoryItem(HistoryItem event)
	{
		//TODO: implement
	}

	public void addChatMessage(ChatItem message)
	{
		//TODO: implement
	}

	public boolean isGameStarted() { return gameStarted; }

	public void addPlayer(User user)
	{
		Player player = new Player(user, getNextColor());
		players.add(player);
	}

	public boolean isGameFull() { return players.size() >= PLAYER_LIMIT; }


	//TODO: documentation - what is this doing?
	public void playerDrewTrainCard(String username, List<TrainCard> drawn)
	{
		getPlayer(username).drewTrainCards(drawn);
	}

	public void playerDrewDestinationCard(String username, List<DestinationCard> drawn, List<DestinationCard> returned)
	{
		getPlayer(username).drewDestinationCards(drawn);
		//TODO implement returning cards to the destination deck -> talk to Ben
	}


	private Player getPlayer(String name)
	{
		for (Player p : players)
		{
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	private Player getPlayer(User user)
	{
		for (Player p : players)
		{
			if (p.getUser().getStringUserName().equals(user.getStringUserName()))
				return p;
		}
		return null;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	private PlayerColor getNextColor()
	{
		switch (players.size())
		{
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
