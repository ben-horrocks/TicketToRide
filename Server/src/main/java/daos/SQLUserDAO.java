package daos;

import java.io.IOException;
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

public class SQLUserDAO extends AbstractDAO implements IUserDAO
{
	private static final Logger logger = LogKeeper.getSingleton().getLogger();

	public SQLUserDAO(Connection connection)
	{
		super(connection);
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
		logger.entering("SQLUserDAO", "createTable");
		final String CREATE_USERS_TABLE =
				"CREATE TABLE " + UserEntry.TABLE_NAME + " ( '" +
						UserEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL UNIQUE, '" +
						UserEntry.COLUMN_NAME_USER + "' BLOB NOT NULL UNIQUE, " +
						"PRIMARY KEY(' " + UserEntry.COLUMN_NAME_USERNAME + "') )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			logger.warning(e + " - creating table " + UserEntry.TABLE_NAME);
			logger.exiting("SQLUserDAO", "createTable", false);
			return false;
		}
		logger.exiting("SQLUserDAO", "createTable", true);
		return true;
	}

	@Override
	boolean deleteTable()
	{
		logger.entering("SQLUserDAO", "deleteTable");
		final String DELETE_USERS_TABLE = "DROP TABLE " + UserEntry.TABLE_NAME;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(DELETE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			logger.warning(e + " - deleting table " + UserEntry.TABLE_NAME);
			logger.exiting("SQLUserDAO", "deleteTable", false);
			return false;
		}
		logger.exiting("SQLUserDAO", "deleteTable", true);
		return true;
	}

	@Override
	public boolean addNewUser(User user)
	{
		logger.entering("SQLUserDAO", "addNewUser", user);
		final String INSERT_USER =
				"INSERT INTO " + UserEntry.TABLE_NAME +
						" (" + UserEntry.COLUMN_NAME_USERNAME +
						", " + UserEntry.COLUMN_NAME_USER +
						") VALUES (?,?)";
		try
		{
			byte[] userAsBytes = objectToByteArray(user);

			PreparedStatement statement = connection.prepareStatement(INSERT_USER);
			statement.setString(1, user.getStringUserName());
			statement.setObject(2, userAsBytes);
			statement.executeUpdate();
		}
		catch (SQLException | IOException e)
		{
			logger.warning(e + " - adding new user " + user.getStringUserName());
			logger.exiting("SQLUserDAO", "addNewUser", false);
			return false;
		}
		logger.exiting("SQLUserDAO", "addNewUser", true);
		return true;
	}

	@Override
	public User getUser(Username username)
	{
		logger.entering("SQLUserDAO", "getUser", username);
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
					logger.exiting("SQLUserDAO", "getUser", user);
					return user;
				}
			}
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting user " + username);
			e.printStackTrace();
		}
		logger.exiting("SQLUserDAO", "getUser", null);
		return null;
	}

	@Override
	public List<User> getAllUsers()
	{
		logger.entering("SQLUserDAO", "getAllUsers");
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
			logger.exiting("SQLUserDAO", "getAllUsers", users);
			return users;
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting all users");
			e.printStackTrace();
		}
		logger.exiting("SQLUserDAO", "getAllUsers", null);
		return null;
	}

	@Override
	public boolean updateUser(User user)
	{
		logger.entering("SQLUserDAO", "updateUser", user);
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
			logger.exiting("SQLUserDAO", "updateUser", true);
			return true;
		}
		catch (SQLException | IOException e)
		{
			logger.warning(e + " - updating User - " + user);
			e.printStackTrace();
		}
		logger.exiting("SQLUserDAO", "updateUser", false);
		return false;
	}

	@Override
	public boolean deleteUser(User user)
	{
		logger.entering("SQLUserDAO", "deleteUser", user);
		final String DELETE_USER =
				"DELETE FROM " + UserEntry.TABLE_NAME +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(DELETE_USER);
			statement.setObject(1, user.getStringUserName());
			statement.executeUpdate();
			logger.exiting("SQLUserDAO", "deleteUser", true);
			return true;
		}
		catch (SQLException e)
		{
			logger.warning(e + " - deleting User - " + user);
			e.printStackTrace();
		}
		logger.exiting("SQLUserDAO", "deleteUser", false);
		return false;
	}
}
