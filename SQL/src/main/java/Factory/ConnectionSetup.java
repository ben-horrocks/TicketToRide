package Factory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class to setup a connection to the database.
 */
public class ConnectionSetup
{
	private ConnectionSetup() {}

	/**
	 * Sets up a connection to the database.
	 * @return Returns the connection made.
	 */
	public static Connection setup()
	{
		try
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e + ": Class not found - Connection Setup");
			e.printStackTrace();
		}
		String dbName = "src\\main\\java\\database" + File.separator + "SQLite_DB.db";
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
		return connection;
	}
}