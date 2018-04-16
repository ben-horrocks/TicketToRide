package daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.game_data.GameID;
import common.player_info.Player;
import common.player_info.Username;

/**
 * Created by Ben_D on 4/9/2018.
 */

public class FlatPlayerDAO implements IPlayerDAO, IDAO
{
    private Map<GameID, Map<Username, Player>> players = new HashMap<>();

    private static final String  PATH = "database" + File.separator
                                      + "FlatFile" + File.separator
                                      + "Player" + File.separator;
    private static final String EXTENSION = ".plr";
    private static final String LOGGER_TAG = "FlatPlayerDAO";

    public FlatPlayerDAO(boolean clearDatabase) throws IOException{
        this.players = new HashMap<>();
        File playerDirectory = new File(PATH);
        if(!playerDirectory.exists())
        {
            playerDirectory.mkdir();
            return;
        }
        if(!playerDirectory.isDirectory())
            throw new FileSystemException(PATH, null, PATH + " is not a directory!");
        if(clearDatabase)
        {
            clearData();
        } else
        {
            for(File folder : playerDirectory.listFiles()) //for each folder in the directory
            {
                GameID gameID = new GameID(folder.getName()); //the name of the folder is the GameID
                players.put(gameID, new HashMap<Username, Player>());
                if(folder.isDirectory() && folder.canRead()) //for each file in the folder
                {
                    for(File file : folder.listFiles())
                    {
                        if (file.isFile() && file.canRead())
                        {
                            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                            Player player = null;
                            try
                            {
                                player = (Player) is.readObject(); //turn the file into a player
                            } catch (ClassNotFoundException | ClassCastException e) {
                                throw new IOException("Error reading " + file.getName() + " as a Player. Abort import!", e);
                            }

                            //put the data into memory
                            Username name = player.getUsername();
                            players.get(gameID).put(name, player);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean addNewPlayer(GameID game, Player player)
    {
        Map gamePlayers = players.get(game);
        if(gamePlayers.get(player.getUsername()) != null)
        {
//            logger.log(Level.WARNING, "That player already exists. Perhaps you meant to update instead?");
            return false;
        }
        //Make sure the Directory exists for this game
        File gameDirectory = new File(createFilenameFor(game));
        if(!gameDirectory.exists())
        {
            gameDirectory.mkdir();
        }
        //Add the new Player file to the directory
        File newPlayer = new File(createFilenameFor(game, player.getUsername()));
        try
        {
            newPlayer.createNewFile();
            newPlayer.setWritable(true);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(newPlayer));
            os.writeObject(player);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();

            return false;
        }
        gamePlayers.put(player.getUsername(), player);
        return true;
    }

    @Override
    public Player getPlayer(GameID game, Username name)
    {
        Object[] params = {game, name};
        Player player = players.get(game).get(name);
        return player;
    }

    @Override
    public List<Player> getAllPlayers()
    {
        List<Player> allPlayers = new ArrayList<>();
        for(Map<Username, Player> game : players.values())
        {
            for(Player p : game.values())
            {
                allPlayers.add(p);
            }
        }
        return allPlayers;
    }

    @Override
    public List<Player> getAllPlayersInGame(GameID game)
    {
        List<Player> allPlayers = new ArrayList<>();
        for(Player p : players.get(game).values())
        {
            allPlayers.add(p);
        }
        return allPlayers;
    }

    @Override
    public boolean updatePlayer(GameID game, Player player)
    {
        if(players.get(game).get(player.getUsername()) == null)
        {
//            logger.log(Level.WARNING, "That player doesn't already exist. Perhaps you meant to add instead?");
            return false;
        }
        File updatedPlayerFile = new File(createFilenameFor(game, player.getUsername()));
        ObjectOutputStream os = null;
        try
        {
            os = new ObjectOutputStream(new FileOutputStream(updatedPlayerFile, false));
            os.writeObject(player);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        players.get(game).put(player.getUsername(), player);
        return true;
    }

    @Override
    public boolean deletePlayer(GameID game, Player player)
    {
        if(players.get(game).get(player.getUsername()) == null)
        {
//            logger.log(Level.SEVERE, "That user doesn't already exist.");
            return false;
        }
        File oldPlayer = new File(createFilenameFor(game, player.getUsername()));
        File gameFolder = new File(createFilenameFor(game));
        try
        {
            oldPlayer.delete();
            gameFolder.delete(); // if it was the last player this will work, otherwise it will just return false
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        players.get(game).remove(player.getUsername());
        return true;
    }

    private String createFilenameFor(GameID game)
    {
        return PATH + File.separator + game.getId();
    }

    private String createFilenameFor(GameID game, Username name)
    {
        return createFilenameFor(game) + File.separator + name + EXTENSION;
    }

    @Override
    public void clearData()
    {
        File dir = new File(PATH);
        for(File game : dir.listFiles())
        {
            for(File player : game.listFiles())
            {
                player.delete();
            }
            game.delete();
        }
    }
}
