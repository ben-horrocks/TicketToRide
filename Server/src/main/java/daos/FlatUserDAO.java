package daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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

import CS340.TicketServer.LogKeeper;
import common.player_info.User;
import common.player_info.Username;

public class FlatUserDAO implements IUserDAO
{

    private Map<Username, User> users;

    private static final String  PATH = "database" + File.separator
                                      + "FlatFile" + File.separator
                                      + "User";
    private static final String EXTENSION = ".usr";
    private static final Logger logger = LogKeeper.getSingleton().getLogger();
    private static final String LOGGER_TAG = "FlatUserDAO";

    public FlatUserDAO() throws IOException {
        this.users = new HashMap<>();
        File userDirectory = new File(PATH);
        if(!userDirectory.isDirectory())
            throw new FileSystemException(PATH, null, PATH + " is not a directory!");
        for(File file : userDirectory.listFiles())
        {
            if(file.isFile() && file.canRead())
            {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                User input = null;
                try
                {
                    input = (User) is.readObject();
                } catch (ClassNotFoundException | ClassCastException e) {
                    throw new IOException("Error reading " + file.getName() + " as a User. Abort import!", e);
                }

                Username name = input.getUsername();
                users.put(name, input);
            }
        }
    }

    @Override
    public boolean addNewUser(User user)
    {
        logger.entering(LOGGER_TAG, "addNewUser", user);
        if(users.get(user.getUsername()) != null)
        {
            logger.log(Level.WARNING, "That user already exists. Perhaps you meant to update instead?");
            return false;
        }
        File newUserFile = new File(createFilenameFromUsername(user.getUsername()));
        try
        {
            newUserFile.createNewFile();
            newUserFile.setWritable(true);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(newUserFile));
            os.writeObject(user);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();

            return false;
        }
        users.put(user.getUsername(), user);
        logger.exiting(LOGGER_TAG, "addNewUser", true);
        return true;
    }

    @Override
    public User getUser(Username username)
    {
        logger.entering(LOGGER_TAG, "getUser", username);
        User user = users.get(username);
        logger.exiting(LOGGER_TAG, "getUser", user);
        return user;
    }

    @Override
    public List<User> getAllUsers()
    {
        logger.entering(LOGGER_TAG, "getAllUsers");
        List<User> allUsers = new ArrayList<>();
        for(User u : users.values())
        {
            allUsers.add(u);
        }
        logger.exiting(LOGGER_TAG, "getAllUsers", allUsers);
        return allUsers;
    }

    @Override
    public boolean updateUser(User user)
    {
        logger.entering(LOGGER_TAG, "updateUser", user);
        if(users.get(user.getUsername()) == null)
        {
            logger.log(Level.WARNING, "That user doesn't already exist. Perhaps you meant to add instead?");
            return false;
        }
        File updatedUserFile = new File(createFilenameFromUsername(user.getUsername()));
        ObjectOutputStream os = null;
        try
        {
            os = new ObjectOutputStream(new FileOutputStream(updatedUserFile, false));
            os.writeObject(user);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        users.put(user.getUsername(), user);
        logger.exiting(LOGGER_TAG, "updateUser", true);
        return true;
    }

    @Override
    public boolean deleteUser(User user)
    {
        logger.entering(LOGGER_TAG, "deleteUser", user);
        if(users.get(user.getUsername()) == null)
        {
            logger.log(Level.SEVERE, "That user doesn't already exist.");
            return false;
        }
        File oldUser = new File(createFilenameFromUsername(user.getUsername()));
        try
        {
            oldUser.delete();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        users.remove(user.getUsername());
        logger.exiting(LOGGER_TAG, "deleteUser", true);
        return true;
    }

    private String createFilenameFromUsername(Username name)
    {
        return PATH + File.separator + name.toString() + EXTENSION;
    }
}
