package CS340.TicketServer;

import java.util.ArrayList;

import common.DataModels.AuthToken;
import common.DataModels.DestinationCard;
import common.DataModels.GameData.ServerGameData;
import common.DataModels.GameID;
import common.DataModels.Password;
import common.DataModels.User;
import common.DataModels.ScreenName;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.Username;
import common.IServer;

public class ServerFacade implements IServer
{
	/**
	 * Static fields for the Server Facade class
	 * these fields make access to the database thread safe
	 */
	private static volatile ServerFacade SINGLETON;
	private static final Object mutex = new Object();

	/**
	 * Default constructor for the serverFacade class
	 * by constructing the server facade, which accesses the database, in this way
	 * it is prohibited to ever create more than one instance of the server facade
	 * it is also prohibited for any thread to advance beyond the synchronized keyword
	 * without available access to the mutex object
	 */
	private ServerFacade() {}

	public static ServerFacade getSINGLETON() {
		ServerFacade newServer = SINGLETON;
		if (newServer == null) {
			synchronized (mutex) {
				newServer = SINGLETON;
				if (newServer == null) {
					SINGLETON = newServer = new ServerFacade();
				}
			}
		}
		return newServer;
	}

	/**
	 * API call for player that has previously registered. returns a player object for
	 * the player logging in with an updated auth token.
	 * Returns an error signal if the player has not previously registered
	 * @param username The username in question
	 * @param password The password in question
	 * @return Return a signal with either the User that logged in or an Error message
	 */
	public Signal login(String username, String password) {
		Database database = Database.SINGLETON;
		Username uName = new Username(username);
		//Password pWord = new Password(password);

		//Check that user is already registered
		User user = database.getPlayer(uName);
		if (user == null) {
			String errorMsg = "Sorry, you are not registered yet";
			return new Signal(SignalType.ERROR, errorMsg);
		}
		//Check that the provided password matches the profile password
		if (!user.getPass().getPass().equals(password)) {
			String errorMsg = "Sorry, that's not the correct username or password";
			return new Signal(SignalType.ERROR, errorMsg);
		}
		AuthToken token = new AuthToken();
		user.setToken(token);
		return new Signal(SignalType.OK, user);
	}

	/**
	 * API call for a new player to register for the first time.
	 * Returns a player object with a new Auth Token.
	 * Returns an error message if the player's username has already registered
	 * @param username The username to register
	 * @param password The password to register
	 * @param screenName The screenName this client wants to be known as
	 * @return A signal with a player if the registration was okay or an error if the username is taken
	 */
	public Signal register(String username, String password, String screenName) {
		Database database = Database.SINGLETON;

		Username uName = new Username(username);
		Password pWord = new Password(password);
		ScreenName sName = new ScreenName(screenName);

		//Check that user is already registered
		User user = database.getPlayer(uName);
		if (user == null) {

			AuthToken token = new AuthToken();
			user = new User(uName, pWord, sName);
			user.setToken(token);
			database.addPlayer(user);

			return new Signal(SignalType.OK, user);
		}
		String errorMsg = "Sorry, this username is already taken";
		return new Signal(SignalType.ERROR, errorMsg);
	}

	/**
	 * API call to create a new game and add it to the game list of the lobby.
	 * If the name is already the name of a previously created game, it simply appends a number which
	 * is not taken in the game list and appends it on the end.
	 * Then it creates a game object to push back to all client listeners.
	 * @param gameName The name the startingUser wants to give the game
	 * @param startingUser The player initializing the game
	 * @return A signal stating that the game was added
	 */
	public Signal addGame (String gameName, User startingUser) {
		Database database = Database.SINGLETON;

		Integer increment = 1;
		StringBuilder finalName = new StringBuilder(gameName);

		//If a provided serverGameData name is already in the database, modify with a numeric symbol in parentheses
		//which is appended to the end of the name
		String suffix = "";
		while (database.getOpenGameByName(gameName + suffix) != null) {
			if (finalName.length() !=  gameName.length()) {
				finalName.deleteCharAt(finalName.length() - 1);
			}
			suffix = "(" + increment.toString() + ")";
			finalName.append(suffix);
			increment++;
		}

		//Created name that is not in the database.
		//create new serverGameData with new name and starting player
		ServerGameData serverGameData = new ServerGameData(finalName.toString(), startingUser);
		database.addOpenGame(serverGameData);
		return new Signal(SignalType.OK, serverGameData);
	}

	/**
	 * API call for user to join an existing game.
	 * return an updated game entry if the user has successfully joined the game.
	 * returns an error signal if the game cannot be joined.
	 * @param user The user attempting to join the specified game.
	 * @param id The id of the game trying to be joined.
	 * @return A signal specifying whether the user joined the game or an error occurred.
	 */
	public Signal joinGame(User user, GameID id) {
		Database database = Database.SINGLETON;
		ServerGameData serverGameData = database.getOpenGameByID(id);
		if (!serverGameData.isGameFull()) {
			//Check if serverGameData contains user
			if (serverGameData.getPlayers().contains(user)) {
				String errMsg = "Sorry, you have already joined this serverGameData.";
				return new Signal(SignalType.ERROR, errMsg);

			}
			serverGameData.addPlayer(user);
			return new Signal(SignalType.OK, serverGameData);
		}
		String errMsg = "Sorry, the serverGameData you have chosen is already full.";
		return new Signal(SignalType.ERROR, errMsg);
	}

	/**
	 * API Call to start a previously registered game
	 * returns a signal with the started game if successful.
	 * returns an error signal if the game was already started or the game did not exist.
	 * @param id The id of the game trying to be started.
	 * @return A signal stating whether the game started ok or an error occurred.
	 */
	public Signal startGame(GameID id) {
		Database database = Database.SINGLETON;
		ServerGameData serverGameData = database.getOpenGameByID(id);
		if (serverGameData == null) {
			String errMsg = "Sorry, this serverGameData does not exist";
			Signal signal = new Signal(SignalType.ERROR, errMsg);
			return signal;
		}
		if (!serverGameData.isGameStarted()) {
			//start serverGameData
			serverGameData.startGame();
			//remove the serverGameData from the list of open games and move to the list of running games
			database.deleteOpenGame(serverGameData);
			database.addRunningGame(serverGameData);
			//push start notification to all players
			ClientProxy.getSINGLETON().startGame(serverGameData.getId());
			//return start signal to player
			return new Signal(SignalType.OK, serverGameData);
		}
		String errMsg = "Sorry, this serverGameData has already started.";
		return new Signal(SignalType.ERROR, errMsg);
	}

	@Override
	public Signal getAvailableGameInfo() {
		return null;
	}

	@Override
	public Signal populate(){
		User[] users = new User[5];
		for(int i = 1; i <= 5; i++){
			String name = "Tester" + Integer.toString(i);
			String pass = "test";
			Signal s = register(name, pass, name);
			users[i-1] = (User)s.getObject();
		}

		Signal s = addGame("Game1", users[0]);
		GameID id = ((ServerGameData) s.getObject()).getId();
		addGame("Game2", users[0]);
		addGame("Game3", users[1]);

		for(User p: users){
			joinGame(p, id);
		}
		/*for(int i = 0; i < 2; i++){
			String name = "ServerGameData" + Integer.toString(i+2);
			addGame(name, users[i]);
		}*/

		Signal signal = new Signal(SignalType.OK, "Populated");
		return signal;
	}

	@Override
	public Signal returnDestinationCards(ArrayList<DestinationCard> pickedCards, ArrayList<DestinationCard> returnCards) {

		return null;
	}
}
