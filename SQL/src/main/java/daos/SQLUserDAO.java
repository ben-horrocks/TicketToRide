package daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.player_info.User;
import common.player_info.Username;
import plugin.SQLDatabasePlugin;

public class SQLUserDAO extends AbstractSQL_DAO implements IUserDAO
{

	Connection mConnection;

	//cache is a map from String username to User Objects
	private Map<String, User> cache;

	public SQLUserDAO()
	{
		super();
		cache = new HashMap<>();
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
		openConnection();

		try
		{
			DatabaseMetaData meta = mConnection.getMetaData();
			ResultSet rs = meta.getTables(null, null,
					UserEntry.TABLE_NAME, new String[] {"TABLE"});
			if (rs.next())
			{
				rs.close();
				closeConnection(true);
				return true;
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQLException when checking if User table exists - " + e);
			e.printStackTrace();
			closeConnection(false);
			return false;
		}
		closeConnection(true);
		return true;
	}

	@Override
	boolean createTable()
	{
		openConnection();

		final String CREATE_USERS_TABLE =
				"CREATE TABLE " + UserEntry.TABLE_NAME + " ( '" +
						UserEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL UNIQUE, '" +
						UserEntry.COLUMN_NAME_USER + "' BLOB NOT NULL UNIQUE )";
		try
		{
			Statement statement = mConnection.createStatement();
			statement.executeUpdate(CREATE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			System.err.println("ERROR: creating table in SQLUserDAO");
			e.printStackTrace();
			closeConnection(false);
			return false;
		}

		closeConnection(true);
		return true;
	}

	@Override
	boolean deleteTable()
	{
		openConnection();

		final String DELETE_USERS_TABLE = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
		try
		{
			Statement statement = mConnection.createStatement();
			statement.executeUpdate(DELETE_USERS_TABLE);
		}
		catch (SQLException e)
		{
			System.err.println("ERROR: deleting table in SQLUserDAO");
			e.printStackTrace();
			closeConnection(false);
			return false;
		}

		closeConnection(true);
		return true;
	}

	@Override
	public boolean addNewUser(User user)
	{
		//Add user to cache
		cache.put(user.getStringUserName(), user);

		openConnection();

		final String INSERT_USER =
				"INSERT INTO " + UserEntry.TABLE_NAME +
						" (" + UserEntry.COLUMN_NAME_USERNAME +
						", " + UserEntry.COLUMN_NAME_USER +
						") VALUES (?,?)";
		try
		{
			byte[] userAsBytes = objectToByteArray(user);

			PreparedStatement statement = mConnection.prepareStatement(INSERT_USER);
			statement.setString(1, user.getStringUserName());
			statement.setObject(2, userAsBytes);
			statement.executeUpdate();
			statement.close();
		}
		catch (SQLException | IOException e)
		{
			System.err.println(e + " - adding new user");
			e.printStackTrace();
			closeConnection(false);
			return false;
		}

		closeConnection(true);
		return true;
	}

	@Override
	public User getUser(Username username)
	{
		return cache.get(username.getName());
	}

	@Override
	public List<User> getAllUsers()
	{
		List<User> users = new ArrayList<>();
		users.addAll(cache.values());
		return users;
	}

	@Override
	public boolean updateUser(User user)
	{
		openConnection();

		final String UPDATE_USER =
				"UPDATE " + UserEntry.TABLE_NAME +
						" SET " + UserEntry.COLUMN_NAME_USER + " = ?" +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = mConnection.prepareStatement(UPDATE_USER);
			byte[] userAsBytes = objectToByteArray(user);
			statement.setObject(1, userAsBytes);
			statement.setString(2, user.getStringUserName());
			int resultCount = statement.executeUpdate();
			statement.close();
//			logger.exiting("SQLUserDAO", "updateUser", true);
			cache.put(user.getStringUserName(), user);
			if(resultCount > 0)
			{
				closeConnection(true);
				return true;
			}else {
				closeConnection(false);
				return false;
			}
		}
		catch (SQLException | IOException e)
		{
			e.printStackTrace();
			closeConnection(false);
			return false;
		}

	}

	@Override
	public boolean deleteUser(Username username)
	{
		openConnection();

		final String DELETE_USER =
				"DELETE FROM " + UserEntry.TABLE_NAME +
						" WHERE " + UserEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = mConnection.prepareStatement(DELETE_USER);
			statement.setObject(1, username);
			statement.executeUpdate();
			statement.close();

			cache.remove(username);
			closeConnection(true);
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			closeConnection(false);
			return false;
		}

	}

	public void openConnection() {
		//set up driver
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(java.lang.ClassNotFoundException e) {
			// ERROR! Could not load database driver
		}

		//create connection to Database
		String connectionURL = "jdbc:sqlite:TicketToRide.db";

		try {
			// Open a database connection
			mConnection = DriverManager.getConnection(connectionURL);
			// Start a transaction
			mConnection.setAutoCommit(false);
		}
		catch (SQLException ex) {
			// ERROR
			System.out.println("ERROR: SQL exception while attempting to open database");
			ex.printStackTrace();
		}
	}

	private void closeConnection(boolean commit) {
		try {
			if (commit) {
				mConnection.commit();
			}
			else {
				mConnection.rollback();
			}

			mConnection.close();
			mConnection = null;
		}
		catch (SQLException ex) {
			System.out.println("ERROR: SQL exception while closing database connection");
			ex.printStackTrace();
		}
	}
}
