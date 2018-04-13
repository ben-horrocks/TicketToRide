package communicators;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;

/**
 * A class to setup a connection to the database.
 */
public class ConnectionSetup
{
	private ConnectionSetup() {}
	private static Logger logger = LogKeeper.getSingleton().getLogger();

	/**
	 * Sets up a connection to the database.
	 * @return Returns the connection made.
	 */
	public static Connection setup()
	{
		logger.entering("ConnectionSetup", "setup");
		try
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			logger.severe(e + ": Class not found - Connection Setup");
			e.printStackTrace();
		}
		String dbName = "src\\main\\java\\database" + File.separator + "TTR_SQLite_DB.db";
		String connectionURL = "jdbc:sqlite:" + dbName;

		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection(connectionURL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: SQL Exception thrown - Connection Setup");
			e.printStackTrace();
		}
		logger.exiting("ConnectionSetup", "setup", connection);
		return connection;
	}
}