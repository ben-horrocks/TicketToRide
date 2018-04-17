package CS340.TicketServer;


import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import javax.xml.crypto.Data;

import common.chat.ChatItem;
import common.communication.Command;
import common.communication.CommandParams;
import common.communication.IServer;
import common.cards.DestinationCard;
import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.ClientGameData;
import common.game_data.GameID;
import common.game_data.GameInfo;
import common.game_data.ServerGameData;
import common.game_data.StartGamePacket;
import common.game_data.TurnQueue;
import common.history.HistoryItem;
import common.map.Edge;
import common.player_info.AuthToken;
import common.player_info.Password;
import common.player_info.Player;
import common.player_info.PlayerList;
import common.player_info.TrainPieces;
import common.player_info.User;
import common.player_info.Username;
import communicators.ServerCommunicator;
import plugin.IDatabasePlugin;
import plugin.PluginDescriptor;
import plugin.PluginRegistry;

public class ServerFacade implements IServer
{
    /**
     * Static fields for the Server Facade class
     * these fields make access to the database thread safe
     */
    private static volatile ServerFacade SINGLETON;
    private static volatile IDatabasePlugin DATABASE;
    private static final Object dbMutex = new Object();
    private static final Object mutex = new Object();
    private static final Logger logger = LogKeeper.getSingleton().getLogger();
    public static PluginRegistry registry = new PluginRegistry();
    private boolean lastTurnEmmitted = false;
    private boolean startingUp;

    /**
     * Default constructor for the serverFacade class
     * by constructing the server facade, which accesses the database, in this way
     * it is prohibited to ever create more than one instance of the server facade
     * it is also prohibited for any thread to advance beyond the synchronized keyword
     * without available access to the mutex object
     */
    private ServerFacade()
    {
        if (SINGLETON != null)
        {
            throw new InstantiationError("Creating of this object is not allowed.");
        }
        registry.scanForPlugins("Server" + File.separator + "libs");
    }

    public void setPlugin(String pluginName, String pluginClassName, int numberOfCommands, boolean clear) throws Exception
    {
        DATABASE = registry.loadPlugin(pluginName, pluginClassName, numberOfCommands, clear);
        startingUp = true;
        startUp();
        startingUp = false;
    }

    public boolean isStartingUp() {
        return startingUp;
    }

    /**
     * Retrieves the SINGLETON of this class.
     *
     * @return Returns the SINGLETON of this class.
     * @pre None.
     * @post Will return the SINGLETON.
     */
    public static ServerFacade getSINGLETON()
    {
        logger.entering("ServerFacade", "getSINGLETON");
        ServerFacade newServer = SINGLETON;
        if (newServer == null)
        {
            synchronized (mutex)
            {
                newServer = SINGLETON;
                if (newServer == null)
                {
                    logger.finest("Making new ServerFacade");
                    SINGLETON = newServer = new ServerFacade();
                }
            }
        }
        logger.exiting("ServerFacade", "getSINGLETON");
        return newServer;
    }

    synchronized public static IDatabasePlugin getDATABASE()
    {
        return DATABASE;
    }

    /**
     * API call for player that has previously registered. returns a player object for
     * the player logging in with an updated auth token.
     * Returns an error signal if the player has not previously registered
     *
     * @param username The username in question
     * @param password The password in question
     * @return Return a signal with either the User that logged in or an Error message
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    public Signal login(String username, String password)
    {
        logger.entering("ServerFacade", "login", new Object[]{username, password});
        //Database database = Database.SINGLETON;
        Username uName = new Username(username);
        //Password pWord = new Password(password);

        //Check that user is already registered
        //User user = database.getPlayer(uName);
        User user = getDATABASE().getUser(uName);

        if (user == null)
        {
            String errorMsg = "Sorry, you are not registered yet";
            Signal signal = new Signal(SignalType.ERROR, errorMsg);
            logger.exiting("ServerFacade", "login", signal);
            return signal;
        }
        //Check that the provided password matches the profile password
        if (!user.getPassword().getPass().equals(password))
        {
            String errorMsg = "Sorry, that's not the correct username or password";
            Signal signal = new Signal(SignalType.ERROR, errorMsg);
            logger.exiting("ServerFacade", "login", signal);
            return signal;
        }
        AuthToken token = new AuthToken();
        user.setToken(token);
        Signal signal = new Signal(SignalType.OK, user);
        logger.exiting("ServerFacade", "login", signal);
        return signal;
    }

    /**
     * API call for a new player to register for the first time.
     * Returns a player object with a new Auth Token.
     * Returns an error message if the player's username has already registered
     *
     * @param username   The username to register
     * @param password   The password to register
     * @return A signal with a player if the registration was okay or an error if the username is taken
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    public Signal register(String username, String password)
    {
        logger.entering("ServerFacade", "register", new Object[]{username, password});
        //Database database = Database.SINGLETON;

        Username uName = new Username(username);
        Password pWord = new Password(password);

        //Check that user is already registered
        //User user = database.getPlayer(uName);
        User user = getDATABASE().getUser(uName);
        if (user == null)
        {

            AuthToken token = new AuthToken();
            user = new User(uName, pWord);
            user.setToken(token);
            //database.addPlayer(user);
            getDATABASE().addUser(user);
            Signal signal = new Signal(SignalType.OK, user);
            logger.exiting("ServerFacade", "register", signal);
            return signal;
        }
        String errorMsg = "Sorry, this username is already taken";
        Signal signal = new Signal(SignalType.ERROR, errorMsg);
        logger.exiting("ServerFacade", "register", signal);
        return signal;
    }

    /**
     * API call to create a new game and add it to the game list of the lobby.
     * If the name is already the name of a previously created game, it simply appends a number which
     * is not taken in the game list and appends it on the end.
     * Then it creates a game object to push back to all client listeners.
     *
     * @param gameName     The name the startingUser wants to give the game
     * @param startingUser The player initializing the game
     * @return A signal stating that the game was added
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    public Signal addGame(String gameName, User startingUser)
    {
        logger.entering("ServerFacade", "addGame", new Object[]{gameName, startingUser});
        //Database database = Database.SINGLETON;

        Integer increment = 1;
        StringBuilder finalName = new StringBuilder(gameName);

        //If a provided serverGameData name is already in the database, modify with a numeric symbol in parentheses
        //which is appended to the end of the name
        /*String suffix = "";
        while (database.getOpenGameByName(gameName + suffix) != null)
        {
            if (finalName.length() != gameName.length())
            {
                finalName.deleteCharAt(finalName.length() - 1);
            }
            suffix = "(" + increment.toString() + ")";
            finalName.append(suffix);
            increment++;
        }*/

        //Created name that is not in the database.
        //create new serverGameData with new name and starting player
        ServerGameData serverGameData = new ServerGameData(finalName.toString(), startingUser);
        startingUser.addGame(serverGameData.getId());
        //boolean openGameAdded = database.addOpenGame(serverGameData);
        boolean openGameAdded = getDATABASE().addGame(serverGameData);
        if (openGameAdded)
        {
            Signal signal = new Signal(SignalType.OK, serverGameData);
            broadcastGameListChange();
            logger.exiting("ServerFacade", "addGame", signal);
            return signal;
        } else
        {
            String errorMessage = "Game " + finalName.toString() + " not added to open game.";
            Signal signal = new Signal(SignalType.ERROR, errorMessage);
            logger.exiting("ServerFacade", "addGame", signal);
            return signal;
        }
    }

    /**
     * API call for user to join an existing game.
     * return an updated game entry if the user has successfully joined the game.
     * returns an error signal if the game cannot be joined.
     *
     * @param user The user attempting to join the specified game.
     * @param id   The id of the game trying to be joined.
     * @return A signal specifying whether the user joined the game or an error occurred.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    public Signal joinGame(User user, GameID id)
    {
        logger.entering("ServerFacade", "joinGame", new Object[]{user, id});
        //Database database = Database.SINGLETON;
        user.addGame(id);
        //ServerGameData serverGameData = database.getOpenGameByID(id);
        ServerGameData serverGameData = getDATABASE().getGame(id);
        if (!serverGameData.isGameFull())
        {
            //Check if serverGameData contains user
            if (serverGameData.getUsers().contains(user))
            {
                String errMsg = "Sorry, you have already joined this serverGameData.";
                Signal signal = new Signal(SignalType.ERROR, errMsg);
                logger.exiting("ServerFacade", "joinGame", signal);
                return signal;

            }
            serverGameData.addPlayer(user);
            Signal signal = new Signal(SignalType.OK, serverGameData);
            broadcastGameListChange();
            logger.exiting("ServerFacade", "joinGame", signal);
            return signal;
        }
        String errMsg = "Sorry, the serverGameData you have chosen is already full.";
        Signal signal = new Signal(SignalType.ERROR, errMsg);
        logger.exiting("ServerFacade", "joinGame", signal);
        return signal;
    }

    /**
     * API Call to start a previously registered game
     * returns a signal with the started game if successful.
     * returns an error signal if the game was already started or the game did not exist.
     *
     * @param id The id of the game trying to be started.
     * @return A signal stating whether the game started ok or an error occurred.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    public Signal startGame(GameID id)
    {
        logger.entering("ServerFacade", "startGame", id);
        //Database database = Database.SINGLETON;
        //ServerGameData serverGameData = database.getOpenGameByID(id);
        ServerGameData serverGameData = getDATABASE().getGame(id);
        if (serverGameData == null)
        {
            String errMsg = "Sorry, this serverGameData does not exist";
            Signal signal = new Signal(SignalType.ERROR, errMsg);
            logger.exiting("ServerFacade", "startGame", signal);
            return signal;
        }
        if (!serverGameData.isGameStarted())
        {
            //start serverGameData
            serverGameData.startGame();
            //remove the serverGameData from the list of open games and move to the list of running games
            //boolean gameDeleted = database.deleteOpenGame(serverGameData);
            boolean gameDeleted = getDATABASE().getOpenGames().remove(serverGameData);
            if (gameDeleted)
            {
                //database.addRunningGame(serverGameData);
                getDATABASE().getRunningGames().add(serverGameData);
                broadcastGameListChange();
                //Initialize player hands
                HashMap<Username, HandTrainCards> hands = new HashMap<>();
                HashMap<Username, HandDestinationCards> destCards = new HashMap<>();
                for (User p : serverGameData.getUsers())
                {
                    //drawing the players hand
                    ArrayList<TrainCard> hand = new ArrayList<>();
                    if (serverGameData.getName().equals("test"))
                    {
                        for (int i = 0; i < 10; i++)
                        {
                            hand.add(serverGameData.drawFromTrainDeck());
                        }
                        for (Player player : serverGameData.getPlayers())
                        {
                            player.setPieces(new TrainPieces(5));
                        }
                    } else if(serverGameData.getName().equals("test1"))
                    {
                        for (int i = 0; i < 50; i++)
                        {
                            hand.add(serverGameData.drawFromTrainDeck());
                        }
                        for (Player player : serverGameData.getPlayers())
                        {
                            player.setPieces(new TrainPieces(15));
                        }
                    }
                    else
                    {
                        for (int i = 0; i < 4; i++)
                        {
                            hand.add(serverGameData.drawFromTrainDeck());
                        }
                    }


                    serverGameData.playerDrewTrainCard(p.getStringUserName(), new HandTrainCards(hand));
                    hands.put(p.getUsername(), new HandTrainCards(hand));
                    //drawing players initial destination cards
                    List<DestinationCard> dest = serverGameData.destinationDraw();
                    destCards.put(p.getUsername(), new HandDestinationCards(dest));
                }

                //Update all the players
                logger.finest("Entering StartGame Alert Loop");
                for (User u : serverGameData.getUsers())
                {
                    u.setInGame(true);
                    //create ClientGameData
                    ClientGameData gameData = new ClientGameData(serverGameData, u.getUsername());
                    //create the packet
                    StartGamePacket packet = new StartGamePacket(destCards.get(u.getUsername()),
                                                                 hands.get(u.getUsername()),
                                                                 gameData);
                    Signal s = ClientProxy.getSINGLETON().startGame(packet);
                    if (s.getSignalType().equals(SignalType.ERROR))
                    {
                        logger.exiting("ServerFacade", "startGame", s);
                        return s;
                    }
                }
                logger.finest("Exiting StartGame Alert Loop");
                //return start signal to player
                Signal signal = new Signal(SignalType.OK, "Accepted");
                logger.exiting("ServerFacade", "startGame", signal);
                return signal;
            } else
            {
                String errorMessage = "Game " + serverGameData.getId() + " not deleted.";
                Signal signal = new Signal(SignalType.ERROR, errorMessage);
                logger.exiting("ServerFacade", "startGame", signal);
                return signal;
            }
        }
        String errMsg = "Sorry, this serverGameData has already started.";
        Signal signal = new Signal(SignalType.ERROR, errMsg);
        logger.exiting("ServerFacade", "startGame", signal);
        return signal;
    }

	/**
     * @return A signal specifying whether or not the function worked.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    @Override
    public Signal getAvailableGameInfo(Username user)
    {
        /*List<GameInfo> games = Database.SINGLETON.getAllOpenGames();
        for(GameInfo game: Database.SINGLETON.getAllRunningGames())
        {
            if(game.hasUser(user))
            {
                games.add(game);
            }
        }*/
        List<ServerGameData> games = getDATABASE().getOpenGames();
        List<ServerGameData> runningGames = getDATABASE().getRunningGames(user);
        games.addAll(runningGames);
        return new Signal(SignalType.OK, games);
    }

    /**
     * A testing function for making debugging easier. Adds 5 new users to the database and
     * creates 3 new games, each with 1 user (the one who made the game).
     *
     * @return A signal specifying whether or not the function worked.
     * @pre The server must be running
     * @post Will populate the database with users
     */
    @Override
    public Signal populate()
    {
        logger.entering("ServerFacade", "populate");
        User[] users = new User[5];
        for (int i = 1; i <= 5; i++)
        {
            String name = "Tester" + Integer.toString(i);
            String pass = "test";
            Signal s = register(name, pass);
            users[i - 1] = (User) s.getObject();
        }

        Signal s = addGame("Game1", users[0]);
        GameID id = ((ServerGameData) s.getObject()).getId();
        addGame("Game2", users[0]);
        addGame("Game3", users[1]);

        for (User p : users)
        {
            joinGame(p, id);
        }
        /*for(int i = 0; i < 2; i++){
			String name = "ServerGameData" + Integer.toString(i+2);
			addGame(name, users[i]);
		}*/

        Signal signal = new Signal(SignalType.OK, "Populated");
        logger.exiting("ServerFacade", "populate", signal);
        return signal;
    }

    /**
     * Updates the database and notifies all clients of the draw.
     *
     * @param id          The game id associated with the draw.
     * @param user        The username of the user that made the draw.
     * @param pickedCards list of destination cards the player picked (must be at least 2)
     * @param returnCards list of destination cards the player didn't pick (can be 0)
     * @return A signal specifying whether or not the operation was successful.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    @Override
    public Signal returnDestinationCards(GameID id, Username user, HandDestinationCards pickedCards,
                                         HandDestinationCards returnCards)
    {
        logger.entering("ServerFacade", "returnDestinationCards",
                        new Object[]{id, user, pickedCards, returnCards});
        //Data setup
        //Database database = Database.SINGLETON;
        //ServerGameData game = database.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        //Tell the game to update
		boolean isMyTurn = game.getTurnQueue().isMyTurn(user);
        game.playerDrewDestinationCard(user.getName(), pickedCards, returnCards, isMyTurn);
        //Tell the clients to update
        ClientProxy.getSINGLETON().playerDrewDestinationCards(user, pickedCards, id);
        Set<User> otherPlayers = game.getUsers();
		//User agent = database.getPlayer(user);
		User agent = getDATABASE().getUser(user);
        otherPlayers.remove(agent);
        for (User u : otherPlayers)
        {
            Signal s = ClientProxy.getSINGLETON()
                    .opponentDrewDestinationCards(u.getUsername(), user,  pickedCards.size());
            if (s.getSignalType().equals(SignalType.ERROR))
            {
                logger.exiting("ServerFacade", "returnDestinationCards", s);
                return s;
            }
        }
        // Tell the sender that the operation was successful
        Signal signal = new Signal(SignalType.OK, "Accepted");
        logger.exiting("ServerFacade", "returnDestinationCards", signal);
        return signal;
    }

    /**
     * Updates the database with the chat message and notify all clients apart of the game.
     *
     * @param id   The game id associated with the game.
     * @param item The chat item that was sent.
     * @return A signal specifying whether or not the operation was successful.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    @Override
    public Signal sendChat(GameID id, ChatItem item)
    {
        logger.entering("ServerFacade", "sendChat", new Object[]{id, item});
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        game.addChatMessage(item);
        for (User user : game.getUsers())
        {
            Signal s = ClientProxy.getSINGLETON().addChatItem(user.getUsername(), item);
            if (s.getSignalType().equals(SignalType.ERROR))
            {
                logger.exiting("ServerFacade", "sendChat", s);
                return s;
            }
        }
        Signal signal = new Signal(SignalType.OK, "Message added to chat successfully");
        logger.exiting("ServerFacade", "sendChat", signal);
        return signal;
    }

    /**
     * Updates the database with the face up draw and notify all clients.
     *
     * @param id    The game id associated with the game.
     * @param user  The username of the user who made the draw.
     * @param index The index of the card in the list of face up cards.
     * @return A signal specifying whether or not the operation was successful.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    @Override
    public Signal drawFaceUp(GameID id, Username user, int index)
    {
        // int in Object[] may throw an exception
        logger.entering("ServerFacade", "drawFaceUp", new Object[]{id, user, index});

        //Data Setup
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        //Update GameData
        TrainCard card = game.faceUpDraw(index);
        //Alert Clients
        Set<User> otherUsers = game.getUsers();
        otherUsers.remove(/*Database.SINGLETON.getPlayer(user)*/ getDATABASE().getUser(user));
        for (User u : otherUsers)
        {
            Signal s =
                    ClientProxy.getSINGLETON().opponentDrewFaceUpCard(u.getUsername(), user, index, card);
            if (s.getSignalType().equals(SignalType.ERROR))
            {
                logger.exiting("ServerFacade", "drawFaceUp", s);
                return s;
            }
        }

        //update everyone's FaceUpCards
        HandTrainCards faceUps = new HandTrainCards(game.getFaceUpCards());
        ClientProxy.getSINGLETON().updateFaceUpCards(user, faceUps);
        for(User u :otherUsers)
        {
            Signal s =
                    ClientProxy.getSINGLETON().updateFaceUpCards(u.getUsername(), faceUps);
            if (s.getSignalType().equals(SignalType.ERROR))
            {
                logger.exiting("ServerFacade", "drawFaceUp", s);
                return s;
            }
        }
        //Give the card to the requester
        Signal signal = new Signal(SignalType.OK, card);
        logger.exiting("ServerFacade", "drawFaceUp", signal);
        return signal;
    }

    /**
     * Update the database with the destination card draw and notify all clients.
     *
     * @param id   The game id of the game.
     * @param user The username of the user who performed the draw.
     * @return A signal specifying whether or not the operation was successful.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    @Override
    public Signal drawDestinationCards(GameID id, Username user)
    {
        logger.entering("ServerFacade", "drawDestinationCards", new Object[]{id, user});
        //Data Setup
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        //Update GameData
        List<DestinationCard> cards = game.destinationDraw();
        if(cards.size() == 0)
        {
            Signal signal = new Signal(SignalType.ERROR, "No more Destination Cards");
            logger.exiting("ServerFacade", "drawDestinationCards", signal);
            return signal;
        }
        HandDestinationCards hand = new HandDestinationCards(cards);
        //Give the Cards to the requester
        Signal signal = new Signal(SignalType.OK, hand);
        logger.exiting("ServerFacade", "drawDestinationCards", signal);
        return signal;
    }

    /**
     * Update the database with the train card drawn and notifies all clients.
     *
     * @param id   The game id of the game.
     * @param user The username of the user that performed the draw.
     * @return A signal specifying whether or not the operation was successful.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    @Override
    public Signal drawDeck(GameID id, Username user)
    {
        logger.entering("ServerFacade", "drawDeck", new Object[]{id, user});
        //Data Setup
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        //Update GameData
        TrainCard card = game.drawFromTrainDeck();
        //Alert Opponents
        Set<User> otherUsers = game.getUsers();
        otherUsers.remove(getDATABASE().getUser(user)/*Database.SINGLETON.getPlayer(user)*/);
        for (User u : otherUsers)
        {
            Signal s = ClientProxy.getSINGLETON().opponentDrewDeckCard(u.getUsername(), user);
            if (s.getSignalType().equals(SignalType.ERROR))
            {
                logger.exiting("ServerFacade", "drawDeck", s);
                return s;
            }
        }
        //Give the card to the requester
        Signal signal = new Signal(SignalType.OK, card);
        logger.exiting("ServerFacade", "drawDeck", signal);
        return signal;
    }

    /**
     * Updates the server info for a specified Game, User and Edge. Also alerts the other players
     * of the change
     *
     * @param id   The id of the game we're trying to access.
     * @param user The username of the player trying to claim an edge.
     * @param edge The edge that user wants to claim.
     * @return A signal specifying whether or not the claim worked.
     * @pre The Edge passed in has already been validated (Player had the appropriate number of
     * cards to be able to claim the edge) and the edge has already been modified to have the
     * specified player as it's owner.
     * @post Will return a signal of success or error
     */
    @Override
    public Signal playerClaimedEdge(GameID id, Username user, Edge edge, HandTrainCards spent)
    {
        logger.entering("ServerFacade", "claimEdge", new Object[]{id, user, edge});
        //Data Setup
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        Player player = game.getPlayer(user.getName());
        player.addPoints(edge.computePointValue());
        //Update GameData
        game.edgeClaimed(edge,spent.getTrainCards());
        //Alert Opponents
        Set<User> opponents = game.getUsers();
        opponents.remove(/*Database.SINGLETON.getPlayer(user)*/ getDATABASE().getUser(user));
        for (User u : opponents)
        {
            ClientProxy.getSINGLETON().playerClaimedEdge(u.getUsername(), edge);
        }
        Signal signal = new Signal(SignalType.OK, edge);
        logger.exiting("ServerFacade", "claimEdge", signal);
        return signal;
    }

    /**
     * Sends a history item specifying the command that was performed to all clients.
     *
     * @param cmd The command parameters of the command that was executed.
     * @pre Parameters must be non-null
     * @post Will return a signal of success or error
     */
    void reportCommand(CommandParams cmd)
    {
        logger.entering("ServerFacade", "reportCommand", cmd);
        //Make the History Item
        HistoryItem item = new HistoryItem(cmd);
        //Report the command (if applicable)
        Command commandReport = new Command(cmd, ServerFacade.class.getName());
        shouldStore(commandReport);
        getDATABASE().addCommand(commandReport);

        if (item.shouldReport())
        {
            //ServerGameData game = Database.SINGLETON.getRunningGameByID(item.getGame());
            ServerGameData game = getDATABASE().getGame(item.getGame());
            game.addHistoryItem(item);
            Set<User> players = game.getUsers();
            for (User u : players)
            {
                Signal s = ClientProxy.getSINGLETON().addHistoryItem(u.getUsername(), item);
                if (s.getSignalType().equals(SignalType.ERROR))
                {
                    logger.warning("Error from addHistoryItem( " + item.toString() + " )");
                }
            }
        }
        logger.exiting("ServerFacade", "reportCommand");
    }

    private boolean shouldStore(Command cmd)
    {
        switch (cmd.getMethodName())
        {
            case "lastTurn":
            case "startGame":
            case "nextTurn":
            case "endGame":
            case "playerClaimedEdge":
            case "drawDeck":
            case "drawDestinationCards":
            case "drawFaceUp":
            case "sendChat":
            case "returnDestinationCards":
            case "joinGame":
                return true;
            default:
                return false;
        }
    }

    public Signal lastTurn(GameID id)
    {
        logger.entering("ServerFacade", "lastTurn", id);
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        for(User u: game.getUsers())
        {
            ClientProxy.getSINGLETON().lastTurn(u.getUsername());
        }
        game.lastTurn();
        Signal signal = new Signal(SignalType.OK, "LastTurn");
        logger.exiting("ServerFacade", "lastTurn", signal);
        return signal;
    }

    public Signal nextTurn(GameID gameID)
	{
		//ServerGameData game = Database.SINGLETON.getRunningGameByID(gameID);
        ServerGameData game = getDATABASE().getGame(gameID);
		game.nextTurn();
		for (User u : game.getUsers())
		{
			Signal s = ClientProxy.getSINGLETON().updateTurnQueue(u.getUsername());
			if (s.getSignalType().equals(SignalType.ERROR))
			{
				logger.exiting("ServerFacade", "nextTurn", s);
				return s;
			}
		}
		if (game.getNextPlayer() == null)
        {
            for (Player p : game.getPlayers())
            {
                p.getPoints().setDestinationPoints(p.getDestinationCards());
            }
            EndGame(gameID);
            return new Signal(SignalType.OK, "ENDING Game");
        }
        ClientProxy.getSINGLETON().startTurn(game.getNextPlayer());
		Signal signal = new Signal(SignalType.OK, "TurnQueues updated");
		logger.exiting("ServerFacade", "nextTurn", signal);
		return signal;
	}


    @Override
    public Signal EndGame(GameID id) {
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        PlayerList players = new PlayerList((ArrayList<Player>)game.getPlayers());
        Signal s = null;
        for (User u : game.getUsers())
        {
           s = ClientProxy.getSINGLETON().EndGame(u.getUsername(), players);
        }
        //Database.SINGLETON.deleteOpenGame(game);
        getDATABASE().deleteGame(id);
        return s;
    }

    public Signal turnEnded(GameID id, Username name)
    {
        logger.entering("ServerFacade", "turnEnded", id);
        //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
        ServerGameData game = getDATABASE().getGame(id);
        if(game.isLastTurn() && !lastTurnEmmitted)
        {
            nextTurn(id);
            game.lastTurn();
            lastTurnEmmitted = true;
            ServerFacade.getSINGLETON().lastTurn(id);
            Signal signal = new Signal(SignalType.OK, "turnEnded");
            logger.exiting("ServerFacade", "turnEnded", signal);
            return signal;
        }
        nextTurn(id);
        Signal signal = new Signal(SignalType.OK, "turnEnded");
        logger.exiting("ServerFacade", "turnEnded", signal);
        return signal;
    }

    @Override
    public Signal returnToLobby(Username user)
    {
        logger.entering("ServerFacade", "returnToLobby", user);
        Signal signal = new Signal(SignalType.OK, user);
        logger.exiting("ServerFacade", "login", signal);
        return signal;
    }

	private void broadcastGameListChange()
    {
        /*Set<Username> users = Database.SINGLETON.getAllUsernames();
        for(Username u: users)
        {
            List<GameInfo> games = Database.SINGLETON.getAllOpenGames();
            for(GameInfo g: Database.SINGLETON.getAllRunningGames())
            {
                if(g.hasUser(u))
                {
                    games.add(g);
                }
            }
            ClientProxy.getSINGLETON().updateGameList(u, games);
        }*/

        List<User> users = getDATABASE().getAllUsers();
        for(User u: users)
        {
            List<ServerGameData> games = getDATABASE().getOpenGames();
            for(ServerGameData g: getDATABASE().getRunningGames())
            {
                if(g.getUsers().contains(u))
                {
                    games.add(g);
                }
            }
            ArrayList<GameInfo> gameInfos = new ArrayList<>();
            for (ServerGameData g : games)
            {
                gameInfos.add(new GameInfo(g));
            }
            ClientProxy.getSINGLETON().updateGameList(u.getUsername(), gameInfos);
        }
    }

    public Signal exitGame(Username user, GameID id)
    {
        //Database.SINGLETON.setUserInGame(user, false);
        getDATABASE().getUser(user).setInGame(false);
        return new Signal(SignalType.OK, "User exited");
    }

    public Signal resumeGame(User user, GameID id)
    {
        try {
            //Database.SINGLETON.setUserInGame(user.getUsername(), true);
            getDATABASE().getUser(user.getUsername()).setInGame(true);
            //ServerGameData game = Database.SINGLETON.getRunningGameByID(id);
            ServerGameData game = getDATABASE().getGame(id);
            ClientGameData resumeGame = new ClientGameData(game, user.getUsername());
            return new Signal(SignalType.OK, resumeGame);
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            return new Signal(SignalType.ERROR, "Couldnt find game");
        }
    }

    private void startUp()
    {
        List<ServerGameData> games = getDATABASE().getAllGames();
        for (ServerGameData game : games)
        {
            List<Command> cmds = getDATABASE().getCommands(game.getId());
            for (Command cmd : cmds)
            {
                cmd.execute();
            }
        }
    }
}
