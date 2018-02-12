package CS340.TicketServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.DataModels.Game;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Player;
import common.DataModels.Username;

public class Database {

    /**
     * Singleton instance of the database
     */
    public static final Database SINGLETON = new Database();

    /**
     * Fields for the server database
     * these fields will most likely be turned into persistent tables via SQL in the future
     * all maps are name strings which map to specific objects.
     * player list is a map from Username object to player object
     * auth list is a map from Username to auth token object
     * game list is a map from the game name to a game object
     */
    private Map<Username, Player> playerList;
    private Map<GameID, Game> openGameList;
    private Map<GameID, Game> runningGameList;

    /**
     * Private constructor for singleton class
     */
    private Database() {
        playerList = new HashMap<>();
        openGameList = new HashMap<>();
        runningGameList = new HashMap<>();
    }

    /**
     * Add a new player to the database
     * @param player
     * @return boolean true if player was successfully added
     * @pre requires that one has previously checked to make sure
     * that the username is not already present in the username list
     * @post the player will definitely be in the database
     */
    public boolean addPlayer(Player player) {
        playerList.put(player.getUsername(), player);
        return true;
    }

    /**
     * Gets a specific player from the database, according
     * to the username object key
     * @param name
     * @return Player object, null
     * @pre none
     * @post will return the player if found, null if the
     * player is not present in the database
     */
    public Player getPlayer(Username name) {
        if (playerList.containsKey(name)) {
            return playerList.get(name);
        }
        return null;
    }

    /**
     * Update a specific player in the database
     * @param player
     * @return true if the player was successfully updated,
     * false if the player was not found in the database
     * @pre none (though recommended to check the database
     * if the player is already present first)
     * @post will update the player if found and return true,
     * otherwise returns false if player not found
     */
    public boolean updatePlayer(Player player) {
        if (playerList.containsKey(player.getUsername())) {
            playerList.put(player.getUsername(), player);
            return true;
        }
        return false;
    }

    /**
     * removes a specified player from the database
     * removes according to the provided player's username
     * @param player
     * @return boolean (true if player is found, false if player is not found)
     * @pre none (though recommended that you check for presence first)
     * @post the player will definitely not be in the database
     */
    public boolean deletePlayer(Player player) {
        if (playerList.containsKey(player.getUsername())) {
            playerList.remove(player.getUsername());
            return true;
        }
        return false;
    }

    /**
     * Add a new game to the database
     * @param game
     * @return boolean true if game was successfully added
     * @pre requires that one has previously checked to make sure
     * the game is not already present in the list
     * @post the game will definitely be in the database
     */
    public boolean addOpenGame(Game game) {
        openGameList.put(game.getId(), game);
        return true;
    }

    /**
     * Gets a specific game from the database, according
     * to the gameID object key
     * @param id
     * @return game object, null
     * @pre none
     * @post will return the game if found, null if the
     * game is not present in the database
     */
    public Game getOpenGameByID(GameID id) {
        if (openGameList.containsKey(id)) {
            return openGameList.get(id);
        }
        return null;
    }

    /**
     * Get a specific game from the database according
     * to the game's associated game name
     * @param name
     * @return
     */
    public Game getOpenGameByName(String name) {
        for (Map.Entry<GameID, Game> entry : openGameList.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get all the games currently available in the database
     * @return
     * @pre none
     * @post none
     */
    public List<GameInfo> getAllOpenGames() {
        List<GameInfo> gameSet = new ArrayList<>();
        for (GameID id : openGameList.keySet()) {
            GameInfo info = new GameInfo(openGameList.get(id));
            gameSet.add(info);
        }
        return gameSet;
    }

    /**
     * Update a specific game in the database
     * @param game
     * @return true if the game was successfully updated,
     * false if the game was not found in the database
     * @pre none (though recommended to check the database
     * if the game is already present first)
     * @post will update the game if found and return true,
     * otherwise returns false if game not found
     */
    public boolean updateOpenGame(Game game) {
        if (openGameList.containsKey(game.getId().getId())) {
            openGameList.put(game.getId(), game);
            return true;
        }
        return false;
    }

    /**
     * removes a specified game from the database
     * removes according to the provided game's gameID
     * @param game
     * @return boolean (true if game is found, false if game is not found)
     * @pre none (though recommended that you check for presence first)
     * @post the game will definitely not be in the database
     */
    public boolean deleteOpenGame(Game game) {
        if (openGameList.containsKey(game.getId())) {
            openGameList.remove(game.getId());
            return true;
        }
        return false;
    }

    /**
     * Add a new game to the database
     * @param game
     * @return boolean true if game was successfully added
     * @pre requires that one has previously checked to make sure
     * the game is not already present in the list
     * @post the game will definitely be in the database
     */
    public boolean addRunningGame(Game game) {
        runningGameList.put(game.getId(), game);
        return true;
    }

    /**
     * Gets a specific game from the database, according
     * to the gameID object key
     * @param id
     * @return game object, null
     * @pre none
     * @post will return the game if found, null if the
     * game is not present in the database
     */
    public Game getRunningGameByID(GameID id) {
        if (runningGameList.containsKey(id)) {
            return runningGameList.get(id);
        }
        return null;
    }

    /**
     * Get a specific game from the database according
     * to the game's associated game name
     * @param name
     * @return
     */
    public Game getRunningGameByName(String name) {
        for (Map.Entry<GameID, Game> entry : runningGameList.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get all the games currently available in the database
     * @return
     * @pre none
     * @post none
     */
    public List<GameInfo> getAllRunningGames() {
        List<GameInfo> gameSet = new ArrayList<>();
        for (GameID id : runningGameList.keySet()) {
            GameInfo info = new GameInfo(runningGameList.get(id));
            gameSet.add(info);
        }
        return gameSet;
    }

    /**
     * Update a specific game in the database
     * @param game
     * @return true if the game was successfully updated,
     * false if the game was not found in the database
     * @pre none (though recommended to check the database
     * if the game is already present first)
     * @post will update the game if found and return true,
     * otherwise returns false if game not found
     */
    public boolean updateRunningGame(Game game) {
        if (runningGameList.containsKey(game.getId().getId())) {
            runningGameList.put(game.getId(), game);
            return true;
        }
        return false;
    }

    /**
     * removes a specified game from the database
     * removes according to the provided game's gameID
     * @param game
     * @return boolean (true if game is found, false if game is not found)
     * @pre none (though recommended that you check for presence first)
     * @post the game will definitely not be in the database
     */
    public boolean deleteRunningGame(Game game) {
        if (runningGameList.containsKey(game.getId())) {
            runningGameList.remove(game.getId());
            return true;
        }
        return false;
    }

}
