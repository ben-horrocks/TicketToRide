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
		try
		{
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null,
					CommandEntry.TABLE_NAME, new String[] {"TABLE"});
			if (rs.next())
			{
				rs.close();
				return true;
			}
		}
		catch (SQLException e)
		{
			// Shouldn't really have errors.
			System.out.println("SQLException when checking if Command table exists - " + e);
		}
		return false;
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
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_COMMAND_TABLE);
            statement.close();
        } catch (SQLException e)
        {
			System.err.println(e + " - creating table in SQLCommandDAO");
			e.printStackTrace();
			closeConnection();
            return false;
        }

        //Add new mapping in cache for new commands
        cache = new HashMap<>();

        commitConnection();
        closeConnection();
        return true;
    }

    @Override
    boolean deleteTable() {

        openConnection();

        final String DELETE_COMMAND_TABLE = "DROP TABLE " + CommandEntry.TABLE_NAME;
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_COMMAND_TABLE);
            statement.close();
        } catch (SQLException e)
        {
			System.err.println(e + " - deleting table in SQLUserDAO");
			e.printStackTrace();
			closeConnection();
            return false;
        }
        //logger.exiting("SQLCommandDAO", "deleteTable", true);

        //Add new mapping in cache for new commands
        cache = new HashMap<>();

        commitConnection();
        closeConnection();
        return true;
    }

    @Override
    public boolean addNewCommand(Command command) {

        openConnection();

        //Check if the cache value is reached
        GameID id = getGameIdFromCommand(command);

        //update cache
        cache.get(id).add(command);

        final String INSERT_PLAYER =
                "INSERT INTO Commands (" + CommandEntry.COLUMN_NAME_GAME_ID +
                        ", " + CommandEntry.COLUMN_NAME_COMMAND +
                        ") VALUES (?,?)";

        if (id != null) {
			try
			{
				byte[] commandAsBytes = objectToByteArray(command);
				PreparedStatement statement = connection.prepareStatement(INSERT_PLAYER);
				statement.setString(1, id.getId());
				statement.setObject(2, commandAsBytes);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException | IOException e)
			{
				System.err.println(e + " - adding new command");
				e.printStackTrace();
				closeConnection();
				return false;
			}
        }
        commitConnection();
        closeConnection();
        return true;
    }

    @Override
    public List<Command> getCommandsByGameId(GameID id) {

        List<Command> list = cache.get(id);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;

//        //logger.entering("SQLCommandDAO", "getCommandByGameId", id);
//        final String GET_COMMANDS =
//                "SELECT " + CommandEntry.COLUMN_NAME_COMMAND +
//                        " FROM " + CommandEntry.TABLE_NAME +
//                        " WHERE " + CommandEntry.COLUMN_NAME_GAME_ID + " = ?";
//        try
//        {
//            PreparedStatement statement = connection.prepareStatement(GET_COMMANDS);
//            statement.setString(1, id.getId());
//            ArrayList<Command> commandList = new ArrayList<>();
//            ResultSet rs = statement.executeQuery();
//            while (rs.next())
//            {
//                byte[] bytes = rs.getBytes(1);
//                Command foundCommand = (Command) byteArrayToObject(bytes);
//                commandList.add(foundCommand);
//
//            }
//			rs.close();
//			statement.close();
//			//logger.exiting("SQLCommandDAO", "getCommand", id);
//			return commandList;
//        } catch (SQLException | IOException | ClassNotFoundException e)
//        {
//            //logger.warning(e + " - getting commands for id: " + id);
//            e.printStackTrace();
//        }
//        //logger.exiting("SQLCommandDAO", "getCommand", null);
//        return null;
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
            PreparedStatement statement = connection.prepareStatement(DELETE_COMMANDS);
            statement.setString(1, id.getId());
            statement.execute();
            statement.close();
            commitConnection();
            closeConnection();
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        closeConnection();
        return false;

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
}
