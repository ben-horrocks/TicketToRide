package daos;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
import common.communication.Command;
import common.game_data.GameID;

/**
 * Created by Ben_D on 4/9/2018.
 */

public class FlatCommandDAO implements ICommandDAO
{
    private Map<GameID, List<Command>> commands = new HashMap<>();
    private static final String COMMANDPATH = "database" + File.separator + "FlatFile" + File.separator + "Command";
    private static final String suffix = ".cmd";
    private static final Logger logger= LogKeeper.getSingleton().getLogger();
    private static FlatCommandDAO SINGLETON;

    synchronized public FlatCommandDAO getSINGLETON()
    {
        if(SINGLETON == null){
            SINGLETON = new FlatCommandDAO();
        }
        return SINGLETON;
    }

    public FlatCommandDAO() throws IOException
    {
        commands = new HashMap<>();
        File FlatGameDirectory = new File(COMMANDPATH);
        if(!FlatGameDirectory.exists())
        {
            throw new FileSystemException(COMMANDPATH, null, COMMANDPATH + "Doesn't Exist!");
        } else if(!FlatGameDirectory.isDirectory())
        {
            throw new FileSystemException(COMMANDPATH, null, COMMANDPATH + "is not a directory!");
        }
        for (File game : FlatGameDirectory.listFiles())
        {
            if(game.isDirectory() && game.canRead())
            {
                List<Command> commands = new ArrayList<>();
                GameID id = new GameID(game.getName());
                for(File cmd : game.listFiles())
                {
                    if(cmd.isFile() && cmd.canRead())
                    {
                        ObjectInputStream is = new ObjectInputStream(new FileInputStream(cmd));
                        Command command = null;
                        try
                        {
                            command = (Command) is.readObject();
                        } catch (ClassNotFoundException | ClassCastException e)
                        {
                            throw new IOException("Error reading " + cmd.getName() + " as a Command. Abort import!", e);
                        }
                    }
                }
            }
        }
    }


    @Override
    public boolean addNewCommand(Command command)
    {
        logger.entering("FlatCommandDAO","addNewCommand");
        GameID id = getGameIdFromCommand(command);
        List<Command> cmds = commands.get(id);
        if(cmds == null)
        {
            new File(COMMANDPATH + id.getId()).mkdir();
            File file = new File(COMMANDPATH + id.getId() + "_0" + suffix);
            try
            {
                file.createNewFile();
                file.setWritable(true);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
                os.writeObject(command);
                os.close();
            } catch(IOException e)
            {
                e.printStackTrace();
                return false;
            }
            List<Command> newCommands = new ArrayList<>();
            newCommands.add(command);
            commands.put(id, newCommands);
        } else
        {
            int commandNum = cmds.size();
            File file = new File(COMMANDPATH + id.getId() + "_" + Integer.toString(commandNum) + suffix);
            try
            {
                file.createNewFile();
                file.setWritable(true);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
                os.writeObject(command);
                os.close();
            } catch(IOException e)
            {
                e.printStackTrace();
                return false;
            }
            cmds.add(command);
        }
        logger.exiting("FlatCommandDAO","addNewCommand");
        return true;
    }

    @Override
    public List<Command> getCommandsByGameId(GameID id)
    {
        logger.entering("FlatCommandDAO","addNewCommand");
        List<Command> cmds = commands.get(id);
        logger.exiting("FlatCommandDAO","addNewCommand");
        return cmds;
    }

    @Override
    public boolean deleteCommandsByGameId(GameID id)
    {
        logger.entering("FlatCommandDAO","addNewCommand");
        if(commands.get(id) == null)
        {
            logger.log(Level.WARNING, "That gameID doesn't already exist in the database.");
            return false;
        }
        File dir = new File(COMMANDPATH + id + suffix);
        if(!dir.exists() || !dir.isDirectory())
        {
            logger.log(Level.SEVERE, "COULD FIND COMMANDS TO DELETE.");
            return false;
        }
        //We have to delete each individual file before we can delete the directory.
        for(File file : dir.listFiles())
        {
            file.delete();
        }
        dir.delete();
        commands.remove(id);
        logger.exiting("FlatCommandDAO","addNewCommand");
        return false;
    }

    private GameID getGameIdFromCommand(Command command) {
        String[] typeNames = command.getParameterTypeNames();
        GameID id = null;
        for (int i = 0; i < typeNames.length; i++) {
            if (typeNames[i].equals(GameID.class.getName())) {
                id = ((GameID) command.getParameters()[i]);
            }
        }
        return id;
    }

}
