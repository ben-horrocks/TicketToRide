package daos;

import java.util.List;

import common.player_info.User;
import common.player_info.Username;

/**
 * Created by Kavika F.
 */

public interface IUserDAO
{
	boolean addNewUser(User user);
	User getUser(Username username);
	List<User> getAllUsers();
	boolean updateUser(User user);
	boolean deleteUser(User user);
}
