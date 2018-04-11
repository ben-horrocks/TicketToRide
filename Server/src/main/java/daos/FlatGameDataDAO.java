package daos;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
import common.game_data.GameID;
import common.game_data.ServerGameData;

public class FlatGameDataDAO implements IGameDataDAO
{
    private static final Logger logger = LogKeeper.getSingleton().getLogger();

    private Map<GameID, ServerGameData> games = new HashMap<>();
    public static final String  GAMEDATAPATH = "database" + File.separator + "Command";
    @Override
    public boolean addNewGameData(ServerGameData gameData)
    {
        logger.entering("FlatGameDataDAO", "addNewGameData");
        if(games.get(gameData.getId()) != null)
        {
            logger.log(Level.WARNING, "That game already exists. Perhaps you meant to update instead?");
            return false;
        }
        File newGame = new File(GAMEDATAPATH + gameData.getId().getId());
        try
        {
            newGame.createNewFile();
            newGame.setWritable(true);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(newGame));
            os.writeObject(gameData);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();

            return false;
        }
        games.put(gameData.getId(), gameData);
        logger.exiting("FlatGameDataDAO", "addNewGameData");
        return true;

    }

    @Override
    public ServerGameData getGameData(ServerGameData gameData)
    {
        logger.entering("FlatGameDataDAO", "getGameData");
        //Why do we need to get a game data if we already have it???
        return gameData;
    }

    @Override
    public List<ServerGameData> getAllGameData()
    {
        logger.entering("FlatGameDataDAO", "getAllGameData");
        List<ServerGameData> returnGames = new ArrayList<>();
        for(ServerGameData game : games.values())
        {
            returnGames.add(game);
        }
        logger.exiting("FlatGameDataDAO", "getAllGameData");
        return returnGames;
    }

    @Override
    public boolean updateGameData(ServerGameData gameData)
    {
        logger.entering("FlatGameDataDAO", "updateGameData");
        if(games.get(gameData.getId()) == null)
        {
            logger.log(Level.WARNING, "That game doesn't already exist. Perhaps you meant to add instead?");
            return false;
        }
        File newGame = new File(GAMEDATAPATH + gameData.getId().getId());
        try
        {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(newGame));
            os.writeObject(gameData);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        games.put(gameData.getId(), gameData);
        logger.exiting("FlatGameDataDAO", "updateGameData");
        return true;
    }

    @Override
    public boolean deleteGameData(ServerGameData gameData)
    {
        logger.entering("FlatGameDataDAO", "deleteGameData");
        if(games.get(gameData.getId()) == null)
        {
            logger.log(Level.SEVERE, "That game doesn't already exist.");
            return false;
        }
        File newGame = new File(GAMEDATAPATH + gameData.getId().getId());
        try
        {
            newGame.delete();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        games.remove(gameData.getId());
        logger.exiting("FlatGameDataDAO", "deleteGameData");
        return true;
    }
}
