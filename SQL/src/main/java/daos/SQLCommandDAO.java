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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.chat.ChatItem;
import common.communication.Command;
import common.communication.CommandParams;
import common.game_data.GameID;
import common.map.Edge;
import common.player_info.Password;
import common.player_info.User;
import common.player_info.Username;

/**
 * Created by Carter on 4/9/18.
 */

public class SQLCommandDAO extends AbstractSQL_DAO implements ICommandDAO
{

    Connection mConnection;

    //cache is a Map of GameIDs to Sets of Commands for that particular game
    private Map<GameID, List<Command>> cache;

    public SQLCommandDAO()
    {
        super();
        cache = new HashMap<>();
    }

    public static class CommandEntry
    {
        static final String TABLE_NAME = "Commands";
        static final String COLUMN_NAME_GAME_ID = "GameID";
        static final String COLUMN_NAME_COMMAND = "Command";
    }

    @Override
	boolean tableExists()
	{
	    openConnection();

		try
		{
			DatabaseMetaData meta = mConnection.getMetaData();
			ResultSet rs = meta.getTables(null, null,
					CommandEntry.TABLE_NAME, new String[] {"TABLE"});
			if (rs.next())
			{
				rs.close();
				closeConnection(true);
				return true;
			}
		}
		catch (SQLException e)
		{
			// Shouldn't really have errors.
			System.out.println("SQLException when checking if Command table exists - " + e);
			closeConnection(false);
			return false;
		}

		closeConnection(true);
		return true;
	}

    @Override
    boolean createTable() {

        openConnection();

        final String CREATE_COMMAND_TABLE =
                "CREATE TABLE " + CommandEntry.TABLE_NAME + " ( " +
                        CommandEntry.COLUMN_NAME_GAME_ID + " TEXT NOT NULL, " +
                        CommandEntry.COLUMN_NAME_COMMAND + " BLOB NOT NULL )";
        try
        {
            Statement statement = mConnection.createStatement();
            statement.executeUpdate(CREATE_COMMAND_TABLE);
            statement.close();
        } catch (SQLException e)
        {
			System.err.println(e + " - creating table in SQLCommandDAO");
			e.printStackTrace();
			closeConnection(false);
            return false;
        }

        //Add new mapping in cache for new commands
        cache = new HashMap<>();

        closeConnection(true);
        return true;
    }

    @Override
    boolean deleteTable() {

        openConnection();

        final String DELETE_COMMAND_TABLE = "DROP TABLE IF EXISTS " + CommandEntry.TABLE_NAME;
        try
        {
            Statement statement = mConnection.createStatement();
            statement.executeUpdate(DELETE_COMMAND_TABLE);
            statement.close();
        } catch (SQLException e)
        {
			System.err.println(e + " - deleting table in SQLUserDAO");
			e.printStackTrace();
			closeConnection(false);
            return false;
        }

        //Add new mapping in cache for new commands
        cache = new HashMap<>();

        closeConnection(true);
        return true;
    }

    @Override
    public boolean addNewCommand(Command command) {

        //update cache
        GameID id = getGameIdFromCommand(command);
        cache.get(id).add(command);

        openConnection();

        final String INSERT_PLAYER =
                "INSERT INTO Commands (" + CommandEntry.COLUMN_NAME_GAME_ID +
                        ", " + CommandEntry.COLUMN_NAME_COMMAND +
                        ") VALUES (?,?)";

        if (id != null) {
			try
			{
				byte[] commandAsBytes = objectToByteArray(command);
				PreparedStatement statement = mConnection.prepareStatement(INSERT_PLAYER);
				statement.setString(1, id.getId());
				statement.setObject(2, commandAsBytes);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException | IOException e)
			{
				System.err.println(e + " - adding new command");
				e.printStackTrace();
				closeConnection(false);
				return false;
			}
        }

        closeConnection(true);
        return true;
    }

    @Override
    public List<Command> getCommandsByGameId(GameID id) {

        List<Command> list = cache.get(id);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;

    }

    @Override
    public boolean deleteCommandsByGameId(GameID id) {

        //update cache
        cache.remove(id);

        openConnection();

        final String DELETE_COMMANDS =
                "DELETE FROM " + CommandEntry.TABLE_NAME +
                        " WHERE " + CommandEntry.COLUMN_NAME_GAME_ID + " = ?";
        try
        {
            PreparedStatement statement = mConnection.prepareStatement(DELETE_COMMANDS);
            statement.setString(1, id.getId());
            statement.execute();
            statement.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
            closeConnection(false);
        }

        closeConnection(false);
        return true;
    }

    public GameID getGameIdFromCommand(Command command) {
        String[] typeNames = command.getParameterTypeNames();
        GameID id = null;
        for (int i = 0; i < typeNames.length; i++) {
            if (typeNames[i].equals(GameID.class.getName())) {
                id = ((GameID) command.getParameters()[i]);
            }
        }
        return id;
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

        //create conection to Database
        String dbName = "Server/TicketToRide.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;

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
