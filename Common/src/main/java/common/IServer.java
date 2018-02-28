package common;

import common.DataModels.*;

public interface IServer
{
	/**
	 * checks the database for the username ans password and logs in if the username and password match one on the server
	 * @param username username of player attempting to login
	 * @param password password of player attempting to login
	 * @return Signal containing error or ok message and Player object
	 */
	Signal login(String username, String password);

	/**
	 * registers a new player with the given credentials, rejects invalid or preexiting credentials
	 * @param username username of new player (primary key)
	 * @param password password of new player
	 * @param displayName name to be displayed for the public
	 * @return Signal containing error or ok message and Player object
	 */
	Signal register(String username, String password, String displayName);

	/**
	 * adds game to the database
	 * @param gameName name of the game
	 * @param player what player owns the gain
	 * @return Signal containing error or ok message
	 */
	Signal addGame(String gameName, Player player);

	/**
	 * joins a game in the lobby
	 * @param player player who is joining game
	 * @param id game the player wants to join
	 * @return Signal containing error or ok message
	 */
	Signal joinGame(Player player , GameID id);

	/**
	 * starts the game
	 * @param id game to start
	 * @return Signal containing error or ok message
	 */
	Signal startGame(GameID id);

	/**
	 * Accesses game info from the serve
	 * @return Signal containing error or ok message and gameInfo object
	 */
	Signal getAvailableGameInfo();

	/**
	 * Debug method for pre-populating a server with data
	 */
	Signal populate();
}
