package daos;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.*;

import common.game_data.GameID;
import common.game_data.ServerGameData;

public class GameDataDAO implements IGameDataDAO, IDAO
{

    private Map<GameID, ServerGameData> games;

    private static final String  GAMEDATAPATH = "database" + File.separator + "FlatFile" + File.separator + "GameData";
    private static final String suffix = ".game";

    public GameDataDAO(boolean clearDatabase) throws IOException
    {
        games = new HashMap<>();
        File FlatGameDirectory = new File(GAMEDATAPATH);
        if(!FlatGameDirectory.exists())
        {
            new File(GAMEDATAPATH).mkdir();
        } else if(!FlatGameDirectory.isDirectory())
        {
            throw new FileSystemException(GAMEDATAPATH, null, GAMEDATAPATH + "is not a directory!");
        }
        if(clearDatabase)
        {
            clearData();
        }
        else
        {
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
    }

    @Override
    public boolean addNewGameData(ServerGameData gameData)
    {
        if(games.get(gameData.getId()) != null)
        {
//            logger.log(Level.WARNING, "That game already exists. Perhaps you meant to update instead?");
            return false;
        }
        File newGame = new File(getGameFileName(gameData.getId()));
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
        return true;

    }

    @Override
    public ServerGameData getGameData(GameID id)
    {
        ServerGameData game = games.get(id);
        return game;
    }

    @Override
    public List<ServerGameData> getAllGameData()
    {
        List<ServerGameData> returnGames = new ArrayList<>();
        for(ServerGameData game : games.values())
        {
            returnGames.add(game);
        }
        return returnGames;
    }

    @Override
    public boolean updateGameData(ServerGameData gameData)
    {
        if(games.get(gameData.getId()) == null)
        {
//            logger.log(Level.WARNING, "That game doesn't already exist. Perhaps you meant to add instead?");
            return false;
        }
        File newGame = new File(getGameFileName(gameData.getId()));
        if(!newGame.exists())
        {
//            logger.log(Level.SEVERE, "Unable to find " + newGame.getName() + "in the file system.");
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
        return true;
    }

    @Override
    public boolean deleteGameData(ServerGameData gameData)
    {
        if(games.get(gameData.getId()) == null)
        {
//            logger.log(Level.SEVERE, "That game doesn't already exist.");
            return false;
        }
        File newGame = new File(getGameFileName(gameData.getId()));
        if(!newGame.exists())
        {
//            logger.log(Level.SEVERE, "Unable to find " + newGame.getName() + "in the file system.");
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
        return true;
    }

    private String getGameFileName(GameID id)
    {
        return GAMEDATAPATH + File.separator + id.getId() + suffix;
    }

    @Override
    public void clearData()
    {
        File dir = new File(GAMEDATAPATH);
        for(File game : dir.listFiles())
        {
            game.delete();
        }
    }
}
