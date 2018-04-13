package daos;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
import common.communication.Command;
import common.game_data.GameID;

public class FlatCommandDAO implements ICommandDAO
{
    private Map<GameID, List<Command>> commands = new HashMap<>();
    private static final String COMMANDPATH = "database" + File.separator + "FlatFile" + File.separator + "Command";
    private static final String suffix = ".cmd";
    private static final Logger logger= LogKeeper.getSingleton().getLogger();

    public FlatCommandDAO() throws IOException
    {
        commands = new HashMap<>();
        File FlatGameDirectory = new File(COMMANDPATH);
        if(!FlatGameDirectory.exists())
        {
            new File(COMMANDPATH).mkdir();
        } else if(!FlatGameDirectory.isDirectory())
        {
            throw new FileSystemException(COMMANDPATH, null, COMMANDPATH + "is not a directory!");
        }
        for (File game : FlatGameDirectory.listFiles())
        {
            if(game.isDirectory() && game.canRead())
            {
                List<Command> commandList = new ArrayList<>();
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
                        commandList.add(command);
                    }
                }
                this.commands.put(id, commandList);
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
            new File(getDirectoryFileName(id)).mkdir();
            File file = new File(getCommandFileName(id, 0));
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
            File file = new File(getCommandFileName(id, commandNum));
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
        File dir = new File(getDirectoryFileName(id));
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

    private String getDirectoryFileName(GameID id)
    {
        return COMMANDPATH + File.separator + id.getId();
    }

    private String getCommandFileName(GameID id, int num)
    {
        return COMMANDPATH + File.separator + id.getId() + File.separator + Integer.toString(num);
    }
}
