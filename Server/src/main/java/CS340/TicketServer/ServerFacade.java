package CS340.TicketServer;

import common.DataModels.AuthToken;
import common.DataModels.Game;
import common.DataModels.Password;
import common.DataModels.Player;
import common.DataModels.ScreenName;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.Username;
import common.IServer;

/**
 * Created by Kavika F.
 */

public class ServerFacade implements IServer
{
	/**
	 * Static fields for the Server Facade class
	 * these fields make access to the database thread safe
	 */
	private static volatile ServerFacade SINGLETON;
	private static Object mutex = new Object();

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
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public AbstractSignal login(String username, String password) {
		Database database = Database.SINGLETON;

		//Check that player is already registered
		Player player = database.getPlayer(username);
		if (player == null) {
			String errorMsg = "error: you are not yet registered. Please register first.";
			Signal signal = new Signal(SignalType.ERROR, errorMsg);
			return signal;
		}
		else {
			AuthToken token = new AuthToken();
			player.setToken(token);
			Signal signal = new Signal(SignalType.OK, player);
			return signal;
		}
	}

	/**
	 *
	 * @param username
	 * @param password
	 * @param displayName
	 * @return
	 */
	public AbstractSignal register(String username, String password, String screenName) {
		Database database = Database.SINGLETON;

		//Check that player is already registered
		Player player = database.getPlayer(username);
		if (player == null) {

			AuthToken token = new AuthToken();
			Username uName = new Username(username);
			Password pass = new Password(password);
			ScreenName sName = new ScreenName(screenName);
			player = new Player(uName, pass, sName);
			player.setToken(token);

			Signal signal = new Signal(SignalType.OK, player);
			return signal;
		}
		else {

			String errorMsg = "error: you are not yet registered. Please register first.";
			Signal signal = new Signal(SignalType.ERROR, errorMsg);
			return signal;
		}
	}

	/**
	 *
	 * @param gameName
	 * @return
	 */
	public AbstractSignal createGame (String gameName, Player startingPlayer) {
		Database database = Database.SINGLETON;

		Integer increment = 1;
		StringBuilder finalName = new StringBuilder(gameName);

		//If a provided game name is already in the database, modify with a numeric symbol in parentheses
		//which is appended to the end of the name
		while (database.getGame(gameName) != null) {
			if (finalName.length() !=  gameName.length()) {
				finalName.deleteCharAt(finalName.length() - 1);
			}
			finalName.append("(").append(increment.toString()).append(")");
			increment++;
		}

		//Created name that is not in the database
		Game game = new Game(startingPlayer);
		//TODO: add the name of the game to the game itself
		database.addGame(game);
		Signal signal = new Signal(SignalType.OK, game);
		return signal;


		return null;
	}

	/**
	 *
	 */
	public AbstractSignal startGame() {
		//TODO: need to be passed a game id or name to start
		//Check to see if the game is already started. if yes, return error signal
		//Change the value of game started to true of the specified game in the database
		//Create a signal with the game and return it
		return null;
	}
}
