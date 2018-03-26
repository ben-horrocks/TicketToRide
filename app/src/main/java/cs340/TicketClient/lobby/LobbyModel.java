package cs340.TicketClient.lobby;

import java.util.*;

import common.game_data.GameID;
import common.game_data.GameInfo;
import common.player_info.User;

/**
 * Abstract: The class that holds all the data that the LobbyActivity needs to display a list of
 * games, create games, and join games.
 *
 * @domain Map <GameID, GameInfo>    games   the set of games that can be displayed
 * GameID   joinedGame    the id of the currently joined game
 * User   user        the User object associated with this client
 */
public class LobbyModel
{
	private static LobbyModel SINGLETON;

	public static LobbyModel getSingleton()
	{
		if (SINGLETON == null)
		{
			SINGLETON = new LobbyModel();
		}
		return SINGLETON;
	}


    /**
     * The list of available games on the server
     */
    private Map<GameID, GameInfo> games;

    /**
     * The GameID of the currently joined game
     */
    private List<GameID> joinedGames = new ArrayList<>();

    /**
     * The user object associated with this instance of the client
     */
    private User user;

    /**
     * Constructor for creating a new model with initialized games
     *
     * @pre the map of games must be valid on the server
     * @post creates a new model instance with the passed in values
     */
    private LobbyModel()
	{
		games = new HashMap<>();
	}

    /**
     * Adds a game to the game list
     *
     * @param game The game to be added
     * @pre none
     * @post the game list will include the new game
     */
    public void addGame(GameInfo game)
    {
        games.put(game.getID(), game);
    }

    /**
     * Adds a list of games to the game list
     *
     * @param games The games to be added
     * @pre none
     * @post All the games in the collection will be added to the game list
     */
    public void addGames(List<GameInfo> games)
    {
        for (GameInfo g : games)
        {
            addGame(g);
        }
    }

    /**
     * Removes a game from the game list
     *
     * @param id The id of the game to be removed
     * @pre the game list is not empty, and the game id is from a game contained in the game list
     * @post the game will be removed from the game list
     */
    void removeGame(GameID id) throws GameNotFoundException
    {
        GameInfo g = getGame(id);
        games.remove(g);
    }

    /**
     * Searches through the games list for a game with the specified GameID
     *
     * @param id The id for the game that is being looked for
     * @pre none;
     * @post Returns a reference to the specified game, or throws a GameNotFoundException
     */
    GameInfo getGame(GameID id) throws GameNotFoundException
    {
        if (games.containsKey(id))
        {
            return games.get(id);
        }
        throw new GameNotFoundException(id);
    }

    /**
     * @return The list of games
     * @pre none
     * @post The value of the return object is a list of all the games in the model
     */
    Collection<GameInfo> getAllGames()
    {
        return this.games.values();
    }

    /**
     * Returns whether or not a specified game is full (has 5 players)
     *
     * @param id The id for the game that is being checked
     * @pre none
     * @post If the game had 5 players return false otherwise return true. If no game was found with the id, throws a GameNotFoundException
     */
    public boolean isGameFull(GameID id) throws GameNotFoundException
    {
        GameInfo g = getGame(id);
        return g.isFull();
    }

    /**
     * Sets the data in the model to the provided list of GameInfo.
     *
     * @param games The new list of GameInfo
     * @pre none
     * @post The model will add any new games in the list and remove any games that are not in the
     * list.
     */
    public void setGames(List<GameInfo> games)
    {
        HashMap<GameID, GameInfo> newGames = new HashMap<>();
        for (GameInfo g : games)
        {
            newGames.put(g.getID(), g);
        }
        this.games = newGames;
        LobbyPresenter.getInstance().updateGameList();
    }

    /**
     * Gets the id of the currently joined game. Note in the future we may need to join multiple
     * games so the implementation of this method will change
     *
     * @return The joined game id
     * @pre none
     * @post the id of the joined game will be returned, if there is no joined games the return will
     * be null
     */
    List<GameID> getJoinedGames()
    {
        return joinedGames;
    }

    /**
     * Sets the value of the currently joined game. Note in the future we may need to join multiple
     * games so this method may be removed in favor of an addJoinedGame() method.
     *
     * @param joinedGame the game id of the game to set as the joined game
     * @pre none
     * @post the GameID for joined game will be the same as the passed in value
     */
    public void addJoinedGame(GameID joinedGame)
    {
        this.joinedGames.add(joinedGame);
    }

    /**
     * Gets the user data object
     *
     * @return the user associated with the client
     * @pre none
     * @post the user will be returned
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Sets the user associated with the client
     *
     * @param user The user object from the server
     * @pre none
     * @post The user will be stored in the model
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * An exception that occurs when the model tries to access a game that is not found in the games list
     */
    class GameNotFoundException extends Exception
    {
        GameNotFoundException(GameID id)
        {
            super("No game found with ID: " + id.getId());
        }
    }
}
