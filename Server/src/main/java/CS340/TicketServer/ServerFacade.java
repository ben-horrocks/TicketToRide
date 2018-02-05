package CS340.TicketServer;

import com.sun.security.ntlm.Server;

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
	public boolean login(String username, String password) {
		//check if player exists in database
			//If no, return false
			//if yes, add new authtoken and return player information
		return false;
	}

	/**
	 *
	 * @param username
	 * @param password
	 * @param displayName
	 * @return
	 */
	public boolean register(String username, String password, String displayName) {
		//check if player exists in database
		//If no, create player and return information
		//if yes, return error message
		return true;
	}

	/**
	 *
	 * @param gameName
	 * @return
	 */
	public boolean createGame (String gameName) {
		//check if game exists in database
		//if no, create new game and add to database
		//if yes, return error message
		return true;
	}

	/**
	 *
	 */
	public void startGame() {
		//Switch gameStart field to true
	}
}
