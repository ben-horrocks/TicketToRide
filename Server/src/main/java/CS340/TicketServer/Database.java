package CS340.TicketServer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import common.DataModels.AuthToken;
import common.DataModels.Game;
import common.DataModels.GameID;
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
    private Map<GameID, Game> gameList;

    /**
     * Private constructor for singleton class
     */
    private Database() {
        playerList = new HashMap<>();
        gameList = new HashMap<>();
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
        playerList.put(player.getName(), player);
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
        if (playerList.containsKey(player.getName())) {
            playerList.put(player.getName(), player);
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
        if (playerList.containsKey(player.getName())) {
            playerList.remove(player.getName());
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
    public boolean addGame(Game game) {
        gameList.put(game.getId(), game);
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
    public Game getGameByID(GameID id) {
        if (gameList.containsKey(id)) {
            return gameList.get(id);
        }
        return null;
    }

    public Game getGameByName(String name) {
        for (Map.Entry<GameID, Game> entry : gameList.entrySet()) {
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
    public Set<Game> getAllGames() {
        Set<Game> gameSet = new HashSet<>();
        for (GameID id : gameList.keySet()) {
            gameSet.add(gameList.get(id));
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
    public boolean updateGame(Game game) {
        if (gameList.containsKey(game.getId().getId())) {
            gameList.put(game.getId(), game);
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
    public boolean deleteGame(Game game) {
        if (gameList.containsKey(game.getId())) {
            gameList.remove(game.getId());
            return true;
        }
        return false;
    }

}
