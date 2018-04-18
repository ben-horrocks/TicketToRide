package daos;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.player_info.User;
import common.player_info.Username;

public class UserDAO extends AbstractSQL_DAO implements IUserDAO
{
////	private static final Logger logger = LogKeeper.getSingleton().getLogger();

	public UserDAO()
	{
		super();
	}

	static class UserEntry
	{
		static final String TABLE_NAME = "Users";
		static final String COLUMN_NAME_USERNAME = "username";
		static final String COLUMN_NAME_USER = "user";
	}

	@Override
	boolean tableExists()
	{
		try
		{
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null,
					UserEntry.TABLE_NAME, new String[] {"TABLE"});
			if (rs.next())
			{
				rs.close();
				return true;
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQLException when checking if User table exists - " + e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	boolean createTable()
	{
//		logger.entering("UserDAO", "createTable");
		final String CREATE_USERS_TABLE =
				"CREATE TABLE " + UserEntry.TABLE_NAME + " ( '" +
						UserEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL UNIQUE, '" +
						UserEntry.COLUMN_NAME_USER + "' BLOB NOT NULL UNIQUE )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			System.err.println("ERROR: creating table in UserDAO");
			e.printStackTrace();
			return false;
		}
//		logger.exiting("UserDAO", "createTable", true);
		return true;
	}

	@Override
	boolean deleteTable()
	{
//		logger.entering("UserDAO", "deleteTable");
		final String DELETE_USERS_TABLE = "DROP TABLE " + UserEntry.TABLE_NAME;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(DELETE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			System.err.println("ERROR: deleting table in UserDAO");
			e.printStackTrace();
			return false;
		}
//		logger.exiting("UserDAO", "deleteTable", true);
		return true;
	}

	@Override
	public boolean addNewUser(User user)
	{
//		logger.entering("UserDAO", "addNewUser", user);
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
			statement.close();
		}
		catch (SQLException | IOException e)
		{
			System.err.println(e + " - adding new user");
			e.printStackTrace();
			return false;
		}
//		logger.exiting("UserDAO", "addNewUser", true);
		return true;
	}

	@Override
	public User getUser(Username username)
	{
//		logger.entering("UserDAO", "getUser", username);
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
				if (bytes != null)
				{
					return (User)byteArrayToObject(bytes);
//					logger.exiting("UserDAO", "getUser", user);
				}
			}
			rs.close();
			statement.close();
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
//			logger.warning(e + " - getting user " + username);
			e.printStackTrace();
		}
//		logger.exiting("UserDAO", "getUser", null);
		return null;
	}

	@Override
	public List<User> getAllUsers()
	{
//		logger.entering("UserDAO", "getAllUsers");
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
				if (bytes.length == 0) continue;
				User user = (User)byteArrayToObject(bytes);
				users.add(user);
			}
//			logger.exiting("UserDAO", "getAllUsers", users);
			rs.close();
			statement.close();
			return users;
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
//			logger.warning(e + " - getting all users");
			e.printStackTrace();
		}
//		logger.exiting("UserDAO", "getAllUsers", null);
		return null;
	}

	@Override
	public boolean updateUser(User user)
	{
//		logger.entering("UserDAO", "updateUser", user);
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
			int resultCount = statement.executeUpdate();
			statement.close();
//			logger.exiting("UserDAO", "updateUser", true);
			return resultCount > 0;
		}
		catch (SQLException | IOException e)
		{
//			logger.warning(e + " - updating User - " + user);
			e.printStackTrace();
		}
//		logger.exiting("UserDAO", "updateUser", false);
		return false;
	}

	@Override
	public boolean deleteUser(Username username)
	{
//		logger.entering("UserDAO", "deleteUser", user);
		final String DELETE_USER =
				"DELETE FROM " + UserEntry.TABLE_NAME +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(DELETE_USER);
			statement.setObject(1, username);
			statement.executeUpdate();
			statement.close();
//			logger.exiting("UserDAO", "deleteUser", true);
			return true;
		}
		catch (SQLException e)
		{
//			logger.warning(e + " - deleting User - " + user);
			e.printStackTrace();
		}
//		logger.exiting("UserDAO", "deleteUser", false);
		return false;
	}
}
