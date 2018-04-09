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
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(user);
			byte[] userAsBytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(userAsBytes);

			PreparedStatement statement = connection.prepareStatement(INSERT_USER);
			statement.setString(1, user.getStringUserName());
			statement.setBinaryStream(2, bais, userAsBytes.length);
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
				byte[] buf = rs.getBytes(1);
				if (buf == null)
				{
					logger.fine("Nothing found with username: " + username);
				}
				else
				{
					ByteArrayInputStream bais = new ByteArrayInputStream(buf);
					ObjectInputStream inputStream = new ObjectInputStream(bais);
					User user = (User)inputStream.readObject();
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
