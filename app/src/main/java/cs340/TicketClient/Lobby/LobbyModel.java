package cs340.TicketClient.Lobby;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import common.DataModels.GameInfo;
import common.DataModels.GameID;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyModel
{
    private List<GameInfo> games;

    public LobbyModel(){
        games = new ArrayList<GameInfo>();
    }

    public LobbyModel(List<GameInfo> games) {
        this.games = games;
    }

    /**
     * Adds a game to the game list
     * @pre none
     * @post the game list will include the new game
     *
     * @param  game  The game to be added
     */
    public void addGame(GameInfo game){
        games.add(game);
    }

    /**
     * Adds a collection of games to the game list
     * @pre none
     * @post All the games in the collection will be added to the game list
     *
     * @param  games  The games to be added
     */
    public void addGame(Collection<GameInfo> games){
        this.games.addAll(games);
    }

    /**
     * Removes a game from the game list
     * @pre the game list is not empty, and the game id is from a game contained in the game list
     * @post the game will be removed from the game list
     *
     * @param  id  The id of the game to be removed
     */
    public void removeGame(GameID id) throws GameNotFoundException{
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
    public GameInfo getGame(GameID id) throws GameNotFoundException{
        for(GameInfo g: games){
            if(g.getID() == id){
                return g;
            }
        }
        throw new GameNotFoundException(id);
    }

    /**
     * @pre none
     * @post The value of the return object is a list of all the games in the model
     *
     * @return The list of games
     */
    public List<GameInfo> getAllGames(){
        return this.games;
    }

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
     * An exception that occurs when the model tries to access a game that is not found in the games list
     */
    public class GameNotFoundException extends Exception {
        public GameNotFoundException(GameID id){
            super("No game found with ID: " + id.getId());
        }
    }
}
