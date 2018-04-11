package daos;

import java.util.List;

import common.player_info.User;
import common.player_info.Username;

public class FlatUserDAO implements IUserDAO
{
    @Override
    public boolean addNewUser(User user)
    {
        return false;
    }

    @Override
    public User getUser(Username username)
    {
        return null;
    }

    @Override
    public List<User> getAllUsers()
    {
        return null;
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
