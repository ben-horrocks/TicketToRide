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
import common.player_info.User;
import common.player_info.Username;

public class UserDAO implements IUserDAO, IDAO
{

    private Map<Username, User> users;

    private static final String  PATH = "database" + File.separator
                                      + "FlatFile" + File.separator
                                      + "User";
    private static final String EXTENSION = ".usr";
    private static final String LOGGER_TAG = "UserDAO";

    public UserDAO(boolean clearDatabase) throws IOException {
        this.users = new HashMap<>();
        File userDirectory = new File(PATH);
        if(!userDirectory.exists())
        {
            userDirectory.mkdir();
            return;
        }
        if(!userDirectory.isDirectory())
            throw new FileSystemException(PATH, null, PATH + " is not a directory!");
        if(clearDatabase)
        {
            clearData();
        } else
        {
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
    }

    @Override
    public boolean addNewUser(User user)
    {
        if(users.get(user.getUsername()) != null)
        {
//            logger.log(Level.WARNING, "That user already exists. Perhaps you meant to update instead?");
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
        return true;
    }

    @Override
    public User getUser(Username username)
    {
        User user = users.get(username);
        return user;
    }

    @Override
    public List<User> getAllUsers()
    {
        List<User> allUsers = new ArrayList<>();
        for(User u : users.values())
        {
            allUsers.add(u);
        }
        return allUsers;
    }

    @Override
    public boolean updateUser(User user)
    {
        if(users.get(user.getUsername()) == null)
        {
//            logger.log(Level.WARNING, "That user doesn't already exist. Perhaps you meant to add instead?");
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
        return true;
    }

    @Override
    public boolean deleteUser(User user)
    {
        if(users.get(user.getUsername()) == null)
        {
//            logger.log(Level.SEVERE, "That user doesn't already exist.");
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
        return true;
    }

    private String createFilenameFromUsername(Username name)
    {
        return PATH + File.separator + name.toString() + EXTENSION;
    }

    @Override
    public void clearData()
    {
        File dir = new File(PATH);
        for(File user : dir.listFiles())
        {
            user.delete();
        }
    }
}
