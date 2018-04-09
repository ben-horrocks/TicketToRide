package daos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
import common.player_info.User;
import common.player_info.Username;

/**
 * Created by Kavika F.
 */
public class UserDAO extends AbstractDAO implements IUserDAO
{
	private static final Logger logger = LogKeeper.getSingleton().getLogger();
	private Connection connection;

	public UserDAO(Connection connection)
	{
		this.connection = connection;
	}

	static class UserEntry
	{
		static final String TABLE_NAME = "Users";
		static final String COLUMN_NAME_USERNAME = "username";
		static final String COLUMN_NAME_USER = "user";
	}

	@Override
	boolean createTable()
	{
		logger.entering("UserDAO", "createTable");
		final String CREATE_USERS_TABLE =
				"CREATE TABLE " + UserEntry.TABLE_NAME + " ( '" +
						UserEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL UNIQUE, '" +
						UserEntry.COLUMN_NAME_USER + "' BLOB NOT NULL UNIQUE, PRIMARY KEY('username') )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			logger.warning(e + " - creating table " + UserEntry.TABLE_NAME);
			logger.exiting("UserDAO", "createTable", false);
			return false;
		}
		logger.exiting("UserDAO", "createTable", true);
		return true;
	}

	@Override
	boolean deleteTable()
	{
		logger.entering("UserDAO", "deleteTable");
		final String DELETE_USERS_TABLE = "DROP TABLE " + UserEntry.TABLE_NAME;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(DELETE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			logger.warning(e + " - deleting table " + UserEntry.TABLE_NAME);
			logger.exiting("UserDAO", "deleteTable", false);
			return false;
		}
		logger.exiting("UserDAO", "deleteTable", true);
		return true;
	}

	private byte[] objectToByteArray(Object object) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		User user = (User)object;
		oos.writeObject(user);
		oos.close();
		return baos.toByteArray();
	}

	private Object byteArrayToObject(byte[] bytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream inputStream = new ObjectInputStream(bais);
		Object object = inputStream.readObject();
		inputStream.close();
		return object;
	}

	@Override
	public boolean addNewUser(User user)
	{
		logger.entering("UserDAO", "addNewUser", user);
		final String INSERT_USER =
				"INSERT INTO Users (" + UserEntry.COLUMN_NAME_USERNAME +
						", " + UserEntry.COLUMN_NAME_USER +
						") VALUES (?,?)";
		try
		{
			byte[] userAsBytes = objectToByteArray(user);
			// ByteArrayInputStream bais = new ByteArrayInputStream(userAsBytes);

			PreparedStatement statement = connection.prepareStatement(INSERT_USER);
			statement.setString(1, user.getStringUserName());
			statement.setObject(2, userAsBytes);
			statement.executeUpdate();
		}
		catch (SQLException | IOException e)
		{
			logger.warning(e + " - adding new user " + user.getStringUserName());
			logger.exiting("UserDAO", "addNewUser", false);
			return false;
		}
		logger.exiting("UserDAO", "addNewUser", true);
		return true;
	}

	@Override
	public User getUser(Username username)
	{
		logger.entering("UserDAO", "getUser", username);
		final String GET_USER =
				"SELECT " + UserEntry.COLUMN_NAME_USER +
						" FROM " + UserEntry.TABLE_NAME +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(GET_USER);
			statement.setString(1, username.getName());
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				if (bytes == null)
				{
					logger.fine("Nothing found with username: " + username);
				}
				else
				{
					User user = (User)byteArrayToObject(bytes);
					rs.close();
					statement.close();
					logger.exiting("UserDAO", "getUser", user);
					return user;
				}
			}
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting user " + username);
			e.printStackTrace();
		}
		logger.exiting("UserDAO", "getUser", null);
		return null;
	}

	@Override
	public List<User> getAllUsers()
	{
		logger.entering("UserDAO", "getAllUsers");
		final String GET_USERS =
				"SELECT " + UserEntry.COLUMN_NAME_USER +
						" FROM " + UserEntry.TABLE_NAME;
		try
		{
			List<User> users = new ArrayList<>();
			PreparedStatement statement = connection.prepareStatement(GET_USERS);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				User user = (User)byteArrayToObject(bytes);
				users.add(user);
			}
			logger.exiting("UserDAO", "getAllUsers", users);
			return users;
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting all users");
			e.printStackTrace();
		}
		logger.exiting("UserDAO", "getAllUsers", null);
		return null;
	}

	@Override
	public boolean updateUser(User user)
	{
		logger.entering("UserDAO", "updateUser", user);
		final String UPDATE_USER =
				"UPDATE " + UserEntry.TABLE_NAME +
						" SET " + UserEntry.COLUMN_NAME_USER + " = ?" +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
			byte[] userAsBytes = objectToByteArray(user);
			statement.setObject(1, userAsBytes);
			statement.setString(2, user.getStringUserName());
			statement.executeUpdate();
			logger.exiting("UserDAO", "updateUser", true);
			return true;
		}
		catch (SQLException | IOException e)
		{
			logger.warning(e + " - updating User - " + user);
		}
		logger.exiting("UserDAO", "updateUser", false);
		return false;
	}

	@Override
	public boolean deleteUser(User user)
	{
		logger.entering("UserDAO", "deleteUser", user);
		final String DELETE_USER =
				"DELETE FROM " + UserEntry.TABLE_NAME +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(DELETE_USER);
			statement.setObject(1, user.getStringUserName());
			statement.executeUpdate();
			logger.exiting("UserDAO", "deleteUser", true);
			return true;
		}
		catch (SQLException e)
		{
			logger.warning(e + " - deleting User - " + user);
		}
		logger.exiting("UserDAO", "deleteUser", false);
		return false;
	}
}
