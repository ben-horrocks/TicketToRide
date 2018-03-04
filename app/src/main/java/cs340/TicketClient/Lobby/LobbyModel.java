package cs340.TicketClient.Lobby;

import java.util.*;
import java.util.Collection;
import java.util.List;

import common.DataModels.*;

/**
 * Abstract: The class that holds all the data that the LobbyActivity needs to display a list of
 * games, create games, and join games.
 * @domain Map<GameID, GameInfo>    games   the set of games that can be displayed
 * GameID   joinedGame    the id of the currently joined game
 * User   user        the User object associated with this client
 */
public class LobbyModel
{
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
     * Default constructor
     * @pre none
     * @post creates a new model instance with an empty list of games
     */
    public LobbyModel() { games = new HashMap<>(); }

    /**
     * Constructor for creating a new model with initialized games
     * @pre the map of games must be valid on the server
     * @post creates a new model instance with the passed in values
     * @param games The map of games to be used in initialization
     */
    LobbyModel(Map<GameID, GameInfo> games) { this.games = games; }

    /**
     * Adds a game to the game list
     * @pre none
     * @post the game list will include the new game
     *
     * @param  game  The game to be added
     */
    void addGame(GameInfo game) { games.put(game.getID(), game); }

    /**
     * Adds a list of games to the game list
     * @pre none
     * @post All the games in the collection will be added to the game list
     *
     * @param  games  The games to be added
     */
    void addGames(List<GameInfo> games){
        for(GameInfo g: games){
            addGame(g);
        }
    }

    /**
     * Removes a game from the game list
     * @pre the game list is not empty, and the game id is from a game contained in the game list
     * @post the game will be removed from the game list
     *
     * @param  id  The id of the game to be removed
     */
    void removeGame(GameID id) throws GameNotFoundException{
        GameInfo g = getGame(id);
        games.remove(g);
    }

    /**
     * Searches through the games list for a game with the specified GameID
     * @pre none;
     * @post Returns a reference to the specified game, or throws a GameNotFoundException
     *
     * @param  id  The id for the game that is being looked for
     */
    GameInfo getGame(GameID id) throws GameNotFoundException{
        if(games.containsKey(id))
            return games.get(id);
        throw new GameNotFoundException(id);
    }

    /**
     * @pre none
     * @post The value of the return object is a list of all the games in the model
     *
     * @return The list of games
     */
    Collection<GameInfo> getAllGames() { return this.games.values(); }

    /**
     * Returns whether or not a specified game is full (has 5 players)
     * @pre none
     * @post If the game had 5 players return false otherwise return true. If no game was found with the id, throws a GameNotFoundException
     *
     * @param   id  The id for the game that is being checked
     */
    public boolean isGameFull(GameID id) throws GameNotFoundException {
        GameInfo g = getGame(id);
        return g.isFull();
    }

    /**
     * Sets the data in the model to the provided list of GameInfo.
     * @pre none
     * @post The model will add any new games in the list and remove any games that are not in the
     * list.
     * @param games The new list of GameInfo
     */
    public void setGames(List<GameInfo> games) {
        HashMap<GameID, GameInfo> newGames  = new HashMap<>();
        for(GameInfo g: games){
            newGames.put(g.getID(), g);
        }
        this.games = newGames;
    }

    /**
     * Gets the id of the currently joined game. Note in the future we may need to join multiple
     * games so the implementation of this method will change
     * @pre none
     * @post the id of the joined game will be returned, if there is no joined games the return will
     * be null
     * @return The joined game id
     */
    List<GameID> getJoinedGames() { return joinedGames; }
    /**
     * Sets the value of the currently joined game. Note in the future we may need to join multiple
     * games so this method may be removed in favor of an addJoinedGame() method.
     * @pre none
     * @post the GameID for joined game will be the same as the passed in value
     * @param joinedGame the game id of the game to set as the joined game
     */
    public void addJoinedGame(GameID joinedGame) { this.joinedGames.add(joinedGame); }

    /**
     * Gets the user data object
     * @pre none
     * @post the user will be returned
     * @return the user associated with the client
     */
    public User getUser() { return user; }

    /**
     * Sets the user associated with the client
     * @pre none
     * @post The user will be stored in the model
     * @param user The user object from the server
     */
    public void setUser(User user) { this.user = user; }

    /**
     * An exception that occurs when the model tries to access a game that is not found in the games list
     */
    public class GameNotFoundException extends Exception {
        public GameNotFoundException(GameID id){
            super("No game found with ID: " + id.getId());
        }
    }
}
