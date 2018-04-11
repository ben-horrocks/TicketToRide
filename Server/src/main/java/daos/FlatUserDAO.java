package daos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    private Map<Username, User> users = new HashMap<>();

    private static final String  PATH = "database" + File.separator + "FlatFile" + File.separator + "User";
    private static final Logger logger = LogKeeper.getSingleton().getLogger();
    private static final String LOGGER_TAG = "FlatUserDAO";
    private static FlatUserDAO SINGLETON;

    synchronized public FlatUserDAO getSINGLETON()
    {
        if(SINGLETON == null){
            SINGLETON = new FlatUserDAO();
        }
        return SINGLETON;
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
        File newGame = new File(PATH + user.getUsername().toString());
        try
        {
            newGame.createNewFile();
            newGame.setWritable(true);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(newGame));
            os.writeObject(user);
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();

            return false;
        }
        users.put(user.getUsername(), user);
        logger.exiting(LOGGER_TAG, "addNewUser");
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
        return false;
    }

    @Override
    public boolean deleteUser(User user)
    {
        return false;
    }
}
