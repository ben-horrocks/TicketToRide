package daos;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
import common.game_data.GameID;
import common.game_data.ServerGameData;

public class FlatGameDataDAO implements IGameDataDAO
{

    private Map<GameID, ServerGameData> games;

    private static final String  GAMEDATAPATH = "database" + File.separator + "FlatFile" + File.separator + "GameData";
    private static final String suffix = ".game";
    private static final Logger logger = LogKeeper.getSingleton().getLogger();

    public FlatGameDataDAO() throws IOException
    {
        games = new HashMap<>();
        File FlatGameDirectory = new File(GAMEDATAPATH);
        if(!FlatGameDirectory.exists())
        {
            throw new FileSystemException(GAMEDATAPATH, null, GAMEDATAPATH + "Doesn't Exist!");
        } else if(!FlatGameDirectory.isDirectory())
        {
            throw new FileSystemException(GAMEDATAPATH, null, GAMEDATAPATH + "is not a directory!");
        }
        for (File game : FlatGameDirectory.listFiles())
        {
            if(game.isFile() && game.canRead())
            {
                GameID id = new GameID(game.getName());
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(game));
                ServerGameData gameData = null;
                try
                {
                    gameData = (ServerGameData) is.readObject(); //turn the file into a player
                } catch (ClassNotFoundException | ClassCastException e) {
                    throw new IOException("Error reading " + game.getName() + " as a Game. Abort import!", e);
                }
                games.put(id, gameData);
            }

        }
    }

    @Override
    public boolean addNewGameData(ServerGameData gameData)
    {
        logger.entering("FlatGameDataDAO", "addNewGameData");
        if(games.get(gameData.getId()) != null)
        {
            logger.log(Level.WARNING, "That game already exists. Perhaps you meant to update instead?");
            return false;
        }
        File newGame = new File(GAMEDATAPATH + gameData.getId().getId() + suffix);
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
    public ServerGameData getGameData(GameID id)
    {
        logger.entering("FlatGameDataDAO", "getGameData");
        ServerGameData game = games.get(id);
        logger.exiting("FlatGameDataDAO", "getGameData", game);
        return game;
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
        File newGame = new File(GAMEDATAPATH + gameData.getId().getId() + suffix);
        if(!newGame.exists())
        {
            logger.log(Level.SEVERE, "Unable to find " + newGame.getName() + "in the file system.");
            return false;
        }
        ObjectOutputStream os = null;
        try
        {
            os = new ObjectOutputStream(new FileOutputStream(newGame, false));
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
        if(!newGame.exists())
        {
            logger.log(Level.SEVERE, "Unable to find " + newGame.getName() + "in the file system.");
            return false;
        }
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
