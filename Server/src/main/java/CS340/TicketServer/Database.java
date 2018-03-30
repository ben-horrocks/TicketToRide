package CS340.TicketServer;

import java.util.*;

import common.game_data.GameID;
import common.game_data.GameInfo;
import common.game_data.ServerGameData;
import common.player_info.User;
import common.player_info.Username;

public class Database
{

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
    private Map<Username, User> playerList;
    private Map<GameID, ServerGameData> openGameList;
    private Map<GameID, ServerGameData> runningGameList;

    /**
     * Private constructor for singleton class
     */
    private Database()
    {
        playerList = new HashMap<>();
        openGameList = new HashMap<>();
        runningGameList = new HashMap<>();
    }

    /**
     * Add a new user to the database
     *
     * @param user The user to be added to the database.
     * @return boolean true if user was successfully added
     * @pre requires that one has previously checked to make sure
     * that the username is not already present in the username list
     * @post the user will definitely be in the database
     */
    boolean addPlayer(User user)
    {
        playerList.put(user.getUsername(), user);
        return true;
    }

    /**
     * Gets a specific player from the database, according
     * to the username object key
     *
     * @param name The username of the player being queried from the database.
     * @return Return a player object if successfully found; otherwise, null.
     * @pre none
     * @post will return the player if found, null if the
     * player is not present in the database
     */
    public User getPlayer(Username name)
    {
        if (playerList.containsKey(name))
        {
            return playerList.get(name);
        }
        return null;
    }

    public Set<Username> getAllUsernames()
    {
        return playerList.keySet();
    }

    /**
     * Update a specific user in the database
     *
     * @param user The user to be updated in the database.
     * @return true if the user was successfully updated,
     * false if the user was not found in the database
     * @pre none (though recommended to check the database
     * if the user is already present first)
     * @post will update the user if found and return true,
     * otherwise returns false if user not found
     */
    public boolean updatePlayer(User user)
    {
        if (playerList.containsKey(user.getUsername()))
        {
            playerList.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    /**
     * removes a specified user from the database
     * removes according to the provided user's username
     *
     * @param user The user to be deleted from the database.
     * @return boolean (true if user is found, false if user is not found)
     * @pre none (though recommended that you check for presence first)
     * @post the user will definitely not be in the database
     */
    public boolean deletePlayer(User user)
    {
        if (playerList.containsKey(user.getUsername()))
        {
            playerList.remove(user.getUsername());
            return true;
        }
        return false;
    }

    /**
     * Add a new serverGameData to the database
     *
     * @param serverGameData The serverGameData to be added and marked as "open".
     * @return boolean true if serverGameData was successfully added
     * @pre requires that one has previously checked to make sure
     * the serverGameData is not already present in the list
     * @post the serverGameData will definitely be in the database
     */
    boolean addOpenGame(ServerGameData serverGameData)
    {
        openGameList.put(serverGameData.getId(), serverGameData);
        return true;
    }

    /**
     * Gets a specific game from the database, according
     * to the gameID object key
     *
     * @param id The id of the game to be queried from the list of open-games.
     * @return game object, null
     * @pre none
     * @post will return the game if found, null if the
     * game is not present in the database
     */
    ServerGameData getOpenGameByID(GameID id)
    {
        if (openGameList.containsKey(id))
        {
            return openGameList.get(id);
        }
        return null;
    }

    /**
     * Get a specific game from the database according
     * to the game's associated game name
     *
     * @param name The name of the game being queried.
     * @return Return the ServerGameData related to the game name. Return null if not found.
     */
    ServerGameData getOpenGameByName(String name)
    {
        for (Map.Entry<GameID, ServerGameData> entry : openGameList.entrySet())
        {
            if (entry.getValue().getName().equals(name))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get all the games currently available in the database
     *
     * @return Return all of the games that are marked as "open".
     * @pre none
     * @post none
     */
    List<GameInfo> getAllOpenGames()
    {
        List<GameInfo> gameSet = new ArrayList<>();
        for (GameID id : openGameList.keySet())
        {
            GameInfo info = new GameInfo(openGameList.get(id));
            gameSet.add(info);
        }
        return gameSet;
    }

    /**
     * Update a specific serverGameData in the database
     *
     * @param serverGameData The serverGameData to be updated from the list of open games.
     * @return true if the serverGameData was successfully updated,
     * false if the serverGameData was not found in the database
     * @pre none (though recommended to check the database
     * if the serverGameData is already present first)
     * @post will update the serverGameData if found and return true,
     * otherwise returns false if serverGameData not found
     */
    public boolean updateOpenGame(ServerGameData serverGameData)
    {
        if (openGameList.containsKey(serverGameData.getId().getId()))
        {
            openGameList.put(serverGameData.getId(), serverGameData);
            return true;
        }
        return false;
    }

    /**
     * removes a specified serverGameData from the database
     * removes according to the provided serverGameData's gameID
     *
     * @param serverGameData The serverGameData to be deleted from the list of open games.
     * @return boolean (true if serverGameData is found, false if serverGameData is not found)
     * @pre none (though recommended that you check for presence first)
     * @post the serverGameData will definitely not be in the database
     */
    boolean deleteOpenGame(ServerGameData serverGameData)
    {
        if (openGameList.containsKey(serverGameData.getId()))
        {
            openGameList.remove(serverGameData.getId());
            return true;
        }
        return false;
    }

    /**
     * Add a new serverGameData to the database
     *
     * @param serverGameData The serverGameData to be added to the list of running games.
     * @pre requires that one has previously checked to make sure
     * the serverGameData is not already present in the list
     * @post the serverGameData will definitely be in the database
     */
    void addRunningGame(ServerGameData serverGameData)
    {
        runningGameList.put(serverGameData.getId(), serverGameData);
    }

    /**
     * Gets a specific game from the database, according
     * to the gameID object key
     *
     * @param id The id of the game to be queried from the list of running games.
     * @return game object, null
     * @pre none
     * @post will return the game if found, null if the
     * game is not present in the database
     */
    ServerGameData getRunningGameByID(GameID id)
    {
        if (runningGameList.containsKey(id))
        {
            return runningGameList.get(id);
        }
        return null;
    }

    /**
     * Get a specific game from the database according
     * to the game's associated game name
     *
     * @param name The name of the game being queried by the list of running games.
     * @return Return the game with the specified name. If not found, return null.
     */
    public ServerGameData getRunningGameByName(String name)
    {
        for (Map.Entry<GameID, ServerGameData> entry : runningGameList.entrySet())
        {
            if (entry.getValue().getName().equals(name))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get all the games currently available in the database
     *
     * @return Return all games in the list of running games.
     * @pre none
     * @post none
     */
    public List<GameInfo> getAllRunningGames()
    {
        List<GameInfo> gameSet = new ArrayList<>();
        for (GameID id : runningGameList.keySet())
        {
            GameInfo info = new GameInfo(runningGameList.get(id));
            gameSet.add(info);
        }
        return gameSet;
    }

    /**
     * Update a specific serverGameData in the database
     *
     * @param serverGameData The serverGameData to update that is being queried by the list of running games.
     * @return true if the serverGameData was successfully updated,
     * false if the serverGameData was not found in the database
     * @pre none (though recommended to check the database
     * if the serverGameData is already present first)
     * @post will update the serverGameData if found and return true,
     * otherwise returns false if serverGameData not found
     */
    public boolean updateRunningGame(ServerGameData serverGameData)
    {
        if (runningGameList.containsKey(serverGameData.getId().getId()))
        {
            runningGameList.put(serverGameData.getId(), serverGameData);
            return true;
        }
        return false;
    }

    /**
     * removes a specified serverGameData from the database
     * removes according to the provided serverGameData's gameID
     *
     * @param serverGameData The serverGameData to be deleted from the list of running games.
     * @return boolean (true if serverGameData is found, false if serverGameData is not found)
     * @pre none (though recommended that you check for presence first)
     * @post the serverGameData will definitely not be in the database
     */
    public boolean deleteRunningGame(ServerGameData serverGameData)
    {
        if (runningGameList.containsKey(serverGameData.getId()))
        {
            runningGameList.remove(serverGameData.getId());
            return true;
        }
        return false;
    }

}
