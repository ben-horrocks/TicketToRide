package common.game_data;

import java.io.Serializable;
import java.util.*;

import common.cards.DestinationCard;
import common.cards.decks.DestinationCardDeck;
import common.cards.decks.TrainCardDeck;
import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.chat.ChatItem;
import common.history.HistoryItem;
import common.map.City;
import common.map.CityName;
import common.map.Edge;
import common.map.EdgeGraph;
import common.map.InitializedGameGraph;
import common.map.LongestRoute;
import common.player_info.Player;
import common.player_info.PlayerColor;
import common.player_info.User;
import common.player_info.Username;

public class ServerGameData implements Serializable
{
    private static final Integer PLAYER_LIMIT = 5;
    private String name;
    private User creator;
    private List<Player> players;
    private TurnQueue turnQueue;
    private GameID id = new GameID();
    private boolean gameStarted = false;

    private EdgeGraph gameBoard;
    private TrainCardDeck deck;
    private DestinationCardDeck destinations;
    private List<HistoryItem> history = new ArrayList<>();
    private List<ChatItem> chat = new ArrayList<>();
    private LongestRoute longestRoute;

    public ServerGameData(String name, User startingUser)
    {
        this.name = name;
        creator = startingUser;
        this.players = new ArrayList<>();
        this.players.add(new Player(startingUser, getNextColor()));
        this.deck = new TrainCardDeck();
        this.destinations = new DestinationCardDeck();
        this.gameBoard = new InitializedGameGraph();
        this.chat = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    private void createTurnQueue()
    {
        turnQueue = new TurnQueue(getUserNames());
    }

    public TurnQueue getTurnQueue()
    {
        return turnQueue;
    }

    public void startGame()
    {
        createTurnQueue();
        gameStarted = true;
    }

    public List<ChatItem> getChat()
    {
        return chat;
    }

    public GameID getId()
    {
        return id;
    }

    public void setId(GameID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getCreatorName()
    {
        return creator.getStringUserName();
    }

    public Set<User> getUsers()
    {
        HashSet<User> users = new HashSet<>();
        for (Player p : players)
        {
            users.add(p.getUser());
        }
        return users;
    }

    public Set<Username> getUserNames()
    {
        HashSet<Username> usernames = new HashSet<>();
        for (Player p : players)
        {
            usernames.add(p.getUser().getUsername());
        }
        return usernames;
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

    public void edgeClaimed(Edge edge, List<TrainCard> spent)
    {
        Username owner = edge.getOwner();
        getPlayer(owner).claimedEdge(edge, spent);
        gameBoard.claimed(edge);
    }

    public void addHistoryItem(HistoryItem event)
    {
        history.add(event);
    }

    public void addChatMessage(ChatItem message)
    {
        chat.add(message);
    }

    public boolean isGameStarted()
    {
        return gameStarted;
    }

    public void addPlayer(User user)
    {
        Player player = new Player(user, getNextColor());
        players.add(player);
    }

    public boolean isGameFull()
    {
        return players.size() >= PLAYER_LIMIT;
    }

    public int getTrainCardsLeft()
    {
        return deck.size();
    }

    //TODO: documentation - what is this doing?
    public void playerDrewTrainCard(String username, HandTrainCards drawn)
    {
        getPlayer(username).drewTrainCards(drawn);
    }

    public void playerDrewDestinationCard(String username, HandDestinationCards drawn,
                                          HandDestinationCards returned, boolean isMyTurn)
    {
        getPlayer(username).drewDestinationCards(drawn, isMyTurn);
        //TODO: implement returning cards to the destination deck
    }

    private Player getPlayer(String name)
    {
        for (Player p : players)
        {
            if (p.getName().equals(name))
            {
                return p;
            }
        }
        return null;
    }

    private Player getPlayer(Username user)
    {
        for (Player p : players)
        {
            if (p.getUser().getStringUserName().equals(user.getName()))
            {
                return p;
            }
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

    public boolean isLastTurn()
    {
        Player currentPlayer = getPlayer(turnQueue.peek());
        if (currentPlayer.getTrainPiecesRemaining() == 2 ||
            currentPlayer.getTrainPiecesRemaining() == 1 ||
            currentPlayer.getTrainPiecesRemaining() == 0)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public void computeLongestRoute() {
        List<Username> longestPathOwners = new ArrayList<>();
        int longestPathLength = 0;
        for(Player player : players)
        {
            int playerLongestPath = player.computeLongestPath();
            if( playerLongestPath > longestPathLength)
            {
                longestPathOwners.clear();
                longestPathOwners.add(player.getUser().getUsername());
                longestPathLength = playerLongestPath;
            } else if(playerLongestPath == longestPathLength)
            {
                longestPathOwners.add(player.getUser().getUsername());
            }
        }
        longestRoute.setLongestRoute(longestPathOwners, longestPathLength);
    }
}
