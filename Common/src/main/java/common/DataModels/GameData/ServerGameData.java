package common.DataModels.GameData;

import java.io.Serializable;
import java.util.*;

import common.DataModels.*;
import common.DataModels.GameData.Decks.DestinationCardDeck;
import common.DataModels.GameData.Decks.TrainCardDeck;

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
        this.gameBoard = new EdgeGraph();
        createEdges();
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

    public void edgeClaimed(Edge edge)
    {
        Username owner = edge.getOwner();
        getPlayer(owner).claimedEdge(edge);
        //TODO update gameBoard with the modified edge
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
                                          HandDestinationCards returned)
    {
        getPlayer(username).drewDestinationCards(drawn);
        //TODO implement returning cards to the destination deck -> talk to Ben
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

    /* Creates all the edges of the board game */
    private void createEdges()
    {
        City vancouver = new City(49.282729, -123.120738, CityName.VANCOUVER);
        City seattle = new City(47.606209, -122.332071, CityName.SEATTLE);
        City portland = new City(45.523062, -122.676482, CityName.PORTLAND);
        City sanFrancisco = new City(37.774929, -122.419416, CityName.SAN_FRANCISCO);
        City losAngeles = new City(34.052234, -118.243685, CityName.LOS_ANGELES);
        City calgary = new City(51.048615, -114.070846, CityName.CALGARY);
        City saltLakeCity = new City(40.760779, -111.891047, CityName.SALT_LAKE_CITY);
        City lasVegas = new City(36.169941, -115.139830, CityName.LAS_VEGAS);
        City phoenix = new City(33.448377, -112.074037, CityName.PHOENIX);
        City helena = new City(46.588371, -112.024505, CityName.HELENA);
        City denver = new City(39.739236, -104.990251, CityName.DENVER);
        City santaFe = new City(35.686975, -105.937799, CityName.SANTA_FE);
        City elPaso = new City(31.761878, -106.485022, CityName.EL_PASO);
        City winnipeg = new City(49.895136, -97.138374, CityName.WINNIPEG);
        City duluth = new City(46.786672, -92.100485, CityName.DULUTH);
        City omaha = new City(41.252363, -95.997988, CityName.OMAHA);
        City kansasCity = new City(39.099727, -94.578567, CityName.KANSAS_CITY);
        City oklahomaCity = new City(35.467560, -97.516428, CityName.OKLAHOMA_CITY);
        City dallas = new City(32.776664, -96.796988, CityName.DALLAS);
        City houston = new City(29.760427, -95.369803, CityName.HOUSTON);
        City newOrleans = new City(29.951066, -90.071532, CityName.NEW_ORLEANS);
        City littleRock = new City(34.746481, -92.289595, CityName.LITTLE_ROCK);
        City saintLouis = new City(38.627003, -90.199404, CityName.SAINT_LOUIS);
        City chicago = new City(41.878114, -87.629798, CityName.CHICAGO);
        City saultStMarie = new City(46.495300, -84.345317, CityName.SAULT_ST_MARIE);
        City toronto = new City(43.653226, -79.383184, CityName.TORONTO);
        City montreal = new City(45.501689, -73.567256, CityName.MONTREAL);
        City boston = new City(42.360082, -71.058880, CityName.BOSTON);
        City newYork = new City(40.712775, -74.005973, CityName.NEW_YORK_CITY);
        City pittsburgh = new City(40.440625, -79.995886, CityName.PITTSBURGH);
        City washingtonDC = new City(38.907192, -77.036871, CityName.WASHINGTON_DC);
        City raleigh = new City(35.779590, -78.638179, CityName.RALEIGH);
        City nashville = new City(36.162664, -86.781602, CityName.NASHVILLE);
        City atlanta = new City(33.748995, -84.387982, CityName.ATLANTA);
        City charleston = new City(32.776475, -79.931051, CityName.CHARLESTON);
        City miami = new City(25.761680, -80.191790, CityName.MIAMI);

        // Vancouver to Seattle
        addEdge(vancouver, seattle, TrainColor.GRAY, 1, true);

        // Seattle to Vancouver
        addEdge(seattle, vancouver, TrainColor.GRAY, 1, true);

        // Seattle to Portland
        addEdge(seattle, portland, TrainColor.GRAY, 1, true);

        // Portland to Seattle
        addEdge(portland, seattle, TrainColor.GRAY, 1, true);

        // Vancouver to Calgary
        addEdge(vancouver, calgary, TrainColor.GRAY, 3, false);

        // Seattle to Calgary
        addEdge(seattle, calgary, TrainColor.GRAY, 4, false);

        // Portland to San Francisco
        addEdge(portland, sanFrancisco, TrainColor.GREEN, 5, true);

        // San Francisco to Portland
        addEdge(sanFrancisco, portland, TrainColor.PINK, 5, true);

        // San Francisco to Los Angeles
        addEdge(sanFrancisco, losAngeles, TrainColor.YELLOW, 3, true);

        // Los Angeles to San Francisco
        addEdge(losAngeles, sanFrancisco, TrainColor.PINK, 3, true);

        // San Francisco to Salt Lake City
        addEdge(sanFrancisco, saltLakeCity, TrainColor.ORANGE, 5, true);

        // Salt Lake City to San Francisco
        addEdge(saltLakeCity, sanFrancisco, TrainColor.WHITE, 5, true);

        // Portland to Salt Lake City
        addEdge(portland, saltLakeCity, TrainColor.BLUE, 6, false);

        // Los Angeles to Las Vegas
        addEdge(losAngeles, lasVegas, TrainColor.GRAY, 2, false);

        // Los Angeles to Phoenix
        addEdge(losAngeles, phoenix, TrainColor.GRAY, 3, false);

        // Las Vegas to Salt Lake City
        addEdge(lasVegas, saltLakeCity, TrainColor.ORANGE, 3, false);

        // Calgary to Helena
        addEdge(calgary, helena, TrainColor.GRAY, 4, false);

        // Seattle to Helena
        addEdge(seattle, helena, TrainColor.YELLOW, 6, false);

        // Salt Lake City to Helena
        addEdge(saltLakeCity, helena, TrainColor.PINK, 3, false);

        // Los Angeles to El Paso
        addEdge(losAngeles, elPaso, TrainColor.BLACK, 6, false);

        // Phoenix to El Paso
        addEdge(phoenix, elPaso, TrainColor.GRAY, 3, false);

        // El Paso to Santa Fe
        addEdge(elPaso, santaFe, TrainColor.GRAY, 2, false);

        // Phoenix to Santa Fe
        addEdge(phoenix, santaFe, TrainColor.GRAY, 3, false);

        // Phoenix to Denver
        addEdge(phoenix, denver, TrainColor.WHITE, 5, false);

        // Santa Fe to Denver
        addEdge(santaFe, denver, TrainColor.GRAY, 2, false);

        // Salt Lake City to Denver
        addEdge(saltLakeCity, denver, TrainColor.RED, 3, true);

        // Denver to Salt Lake City
        addEdge(denver, saltLakeCity, TrainColor.YELLOW, 3, true);

        // Denver to Helena
        addEdge(denver, helena, TrainColor.GREEN, 4, false);

        // Calgary to Winnipeg
        addEdge(calgary, winnipeg, TrainColor.WHITE, 6, false);

        // Helena to Winnipeg
        addEdge(helena, winnipeg, TrainColor.BLUE, 4, false);

        // Winnipeg to Duluth
        addEdge(winnipeg, duluth, TrainColor.BLACK, 4, false);

        // Helena to Duluth
        addEdge(helena, duluth, TrainColor.ORANGE, 6, false);

        // Winnipeg to Saul St. Marie
        addEdge(winnipeg, saultStMarie, TrainColor.GRAY, 6, false);

        // Duluth to Saul St. Marie
        addEdge(duluth, saultStMarie, TrainColor.GRAY, 3, false);

        // Duluth to Omaha
        addEdge(duluth, omaha, TrainColor.GRAY, 2, true);

        // Omaha to Duluth
        addEdge(omaha, duluth, TrainColor.GRAY, 2, true);

        // Helena to Omaha
        addEdge(helena, omaha, TrainColor.RED, 5, false);

        // Denver to Omaha
        addEdge(denver, omaha, TrainColor.PINK, 4, false);

        // Omaha to Kansas City
        addEdge(omaha, kansasCity, TrainColor.GRAY, 1, true);

        // Kansas City to Omaha
        addEdge(kansasCity, omaha, TrainColor.GRAY, 1, true);

        // Denver to Kansas City
        addEdge(denver, kansasCity, TrainColor.BLACK, 4, true);

        // Kansas City to Denver
        addEdge(kansasCity, denver, TrainColor.ORANGE, 4, true);

        // Kansas City to Oklahoma City
        addEdge(kansasCity, oklahomaCity, TrainColor.GRAY, 2, true);

        // Oklahoma City to Kansas City
        addEdge(oklahomaCity, kansasCity, TrainColor.GRAY, 2, true);

        // Denver to Oklahoma City
        addEdge(denver, oklahomaCity, TrainColor.RED, 4, false);

        // Santa Fe to Oklahoma City
        addEdge(santaFe, oklahomaCity, TrainColor.BLUE, 3, false);

        // El Paso to Dallas
        addEdge(elPaso, dallas, TrainColor.RED, 4, false);

        // El Paso to Houston
        addEdge(elPaso, houston, TrainColor.GREEN, 6, false);

        // Dallas to Houston
        addEdge(dallas, houston, TrainColor.GRAY, 1, true);

        // Houston to Dallas
        addEdge(houston, dallas, TrainColor.GRAY, 1, true);

        // Oklahoma City to Dallas
        addEdge(oklahomaCity, dallas, TrainColor.GRAY, 2, true);

        // Dallas to Oklahoma City
        addEdge(dallas, oklahomaCity, TrainColor.GRAY, 2, true);

        // Oklahoma City to Little Rock
        addEdge(oklahomaCity, littleRock, TrainColor.GRAY, 2, false);

        // Dallas to Little Rock
        addEdge(dallas, littleRock, TrainColor.GRAY, 2, false);

        // Duluth to Chicago
        addEdge(duluth, chicago, TrainColor.RED, 3, false);

        // Omaha to Chicago
        addEdge(omaha, chicago, TrainColor.BLUE, 4, false);

        // Kansas City to Saint Louis
        addEdge(kansasCity, saintLouis, TrainColor.BLUE, 2, true);

        // Saint Louis to Kansas City
        addEdge(saintLouis, kansasCity, TrainColor.PINK, 2, true);

        // Saint Louis to Chicago
        addEdge(saintLouis, chicago, TrainColor.GREEN, 2, true);

        // Chicago to Saint Louis
        addEdge(chicago, saintLouis, TrainColor.WHITE, 2, true);

        // Saint Louis to Little Rock
        addEdge(saintLouis, littleRock, TrainColor.GRAY, 2, false);

        // Houston to New Orleans
        addEdge(houston, newOrleans, TrainColor.GRAY, 2, false);

        // Little Rock to New Orleans
        addEdge(littleRock, newOrleans, TrainColor.GREEN, 3, false);

        // Saul St. Marie to Toronto
        addEdge(saultStMarie, toronto, TrainColor.GRAY, 2, false);

        // Duluth to Toronto
        addEdge(duluth, toronto, TrainColor.PINK, 6, false);

        // Chicago to Toronto
        addEdge(chicago, toronto, TrainColor.WHITE, 4, false);

        // Toronto to Pittsburgh
        addEdge(toronto, pittsburgh, TrainColor.GRAY, 2, false);

        // Chicago to Pittsburgh
        addEdge(chicago, pittsburgh, TrainColor.ORANGE, 3, true);

        // Pittsburgh to Chicago
        addEdge(pittsburgh, chicago, TrainColor.BLACK, 3, true);

        // Saint Louis to Pittsburgh
        addEdge(saintLouis, pittsburgh, TrainColor.GREEN, 5, false);

        // Saint Louis to Nashville
        addEdge(saintLouis, nashville, TrainColor.GRAY, 2, false);

        // Little Rock to Nashville
        addEdge(littleRock, nashville, TrainColor.WHITE, 3, false);

        // Nashville to Pittsburgh
        addEdge(nashville, pittsburgh, TrainColor.YELLOW, 4, false);

        // Nashville to Atlanta
        addEdge(nashville, atlanta, TrainColor.GRAY, 1, false);

        // Atlanta to New Orleans
        addEdge(atlanta, newOrleans, TrainColor.YELLOW, 4, true);

        // New Orleans to Atlanta
        addEdge(newOrleans, atlanta, TrainColor.ORANGE, 4, true);

        // New Orleans to Miami
        addEdge(newOrleans, miami, TrainColor.RED, 6, false);

        // Atlanta to Miami
        addEdge(atlanta, miami, TrainColor.BLUE, 5, false);

        // Miami to Charleston
        addEdge(miami, charleston, TrainColor.PINK, 4, false);

        // Atlanta to Charleston
        addEdge(atlanta, charleston, TrainColor.GRAY, 2, false);

        // Nashville to Raleigh
        addEdge(nashville, raleigh, TrainColor.BLACK, 3, false);

        // Atlanta to Raleigh
        addEdge(atlanta, raleigh, TrainColor.GRAY, 2, true);

        // Raleigh to Atlanta
        addEdge(raleigh, atlanta, TrainColor.GRAY, 2, true);

        // Charleston to Raleigh
        addEdge(charleston, raleigh, TrainColor.GRAY, 2, false);

        // Raleigh to Washington D.C.
        addEdge(raleigh, washingtonDC, TrainColor.GRAY, 2, true);

        // Washington D.C. Raleigh
        addEdge(washingtonDC, raleigh, TrainColor.GRAY, 2, true);

        // Washington D.C. to Pittsburgh
        addEdge(washingtonDC, pittsburgh, TrainColor.GRAY, 2, false);

        // Pittsburgh to New York
        addEdge(pittsburgh, newYork, TrainColor.WHITE, 2, true);

        // New York to Pittsburgh
        addEdge(newYork, pittsburgh, TrainColor.GREEN, 2, true);

        // Pittsburgh to Raleigh
        addEdge(pittsburgh, raleigh, TrainColor.GRAY, 2, false);

        // New York to Washington D.C.
        addEdge(newYork, washingtonDC, TrainColor.ORANGE, 2, true);

        // Washington D.C. to New York
        addEdge(washingtonDC, newYork, TrainColor.BLACK, 2, true);

        // Boston to New York
        addEdge(boston, newYork, TrainColor.YELLOW, 2, true);

        // New York to Boston
        addEdge(newYork, boston, TrainColor.RED, 2, true);

        // Montreal to Boston
        addEdge(montreal, boston, TrainColor.GRAY, 2, true);

        // Boston to Montreal
        addEdge(boston, montreal, TrainColor.GRAY, 2, true);

        // Saul St. Marie to Montreal
        addEdge(saultStMarie, montreal, TrainColor.BLACK, 5, false);

        // Toronto to Montreal
        addEdge(toronto, montreal, TrainColor.GRAY, 3, false);

        // New York to Montreal
        addEdge(newYork, montreal, TrainColor.BLUE, 3, false);
    }

    private void addEdge(City city1, City city2, TrainColor color, int length, boolean isDoubleEdge)
    {
        Edge edge = new Edge(city1, city2, color, length, isDoubleEdge);
        gameBoard.addEdge(city1, edge);
    }

    private boolean isLastTurn(Player currentPlayer)
    {
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
