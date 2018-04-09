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
import common.communication.Command;
import common.game_data.GameID;

/**
 * Created by Carter on 4/9/18.
 */

public class SQLCommandDAO extends AbstractDAO implements ICommandDAO {

    private static final Logger logger = LogKeeper.getSingleton().getLogger();
    private Connection connection;

    public SQLCommandDAO(Connection connection)
    {
        this.connection = connection;
    }

    private static class CommandEntry
    {
        static final String TABLE_NAME = "Commands";
        static final String COLUMN_NAME_GAME_ID = "GameID";
        static final String COLUMN_NAME_COMMAND = "Command";
    }


    @Override
    boolean createTable() {
        logger.entering("SQLCommandDAO", "createTable");
        final String CREATE_COMMAND_TABLE =
                "CREATE TABLE " + CommandEntry.TABLE_NAME + " ( " +
                        CommandEntry.COLUMN_NAME_GAME_ID + " TEXT NOT NULL, " +
                        CommandEntry.COLUMN_NAME_COMMAND + "BLOB NOT NULL )";
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_COMMAND_TABLE);
        } catch (SQLException e)
        {
            logger.warning(e + " - creating table " + CommandEntry.TABLE_NAME);
            logger.exiting("SQLCommandDAO", "createTable", false);
            return false;
        }
        return true;
    }

    @Override
    boolean deleteTable() {
        logger.entering("SQLCommandDAO", "deleteTable");
        final String DELETE_COMMAND_TABLE = "DROP TABLE " + CommandEntry.TABLE_NAME;
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_COMMAND_TABLE);
        } catch (SQLException e)
        {
            logger.warning(e + " - deleting table " + CommandEntry.TABLE_NAME);
            logger.exiting("SQLCommandDAO", "deleteTable", false);
            return false;
        }
        logger.exiting("SQLCommandDAO", "deleteTable", true);
        return true;
    }

    @Override
    public boolean addNewCommand(Command command) {
        logger.entering("SQLPlayerDAO", "addNewCommand", command);
        final String INSERT_PLAYER =
                "INSERT INTO Commands (" + CommandEntry.COLUMN_NAME_GAME_ID +
                        ", " + CommandEntry.COLUMN_NAME_COMMAND +
                        ") VALUES (?,?)";

        //get game id from provided command
        GameID id = getGameIdFromCommand(command);

        try
        {
            byte[] commandAsBytes = objectToByteArray(command);
            PreparedStatement statement = connection.prepareStatement(INSERT_PLAYER);
            statement.setString(1, id.getId());
            statement.setString(2, command.getMethodName());
            statement.setObject(3, commandAsBytes);
            statement.executeUpdate();
        } catch (SQLException | IOException e)
        {
            logger.warning(e + " - adding new command " + command);
            logger.exiting("SQLCommandDAO", "addNewCommand", false);
            return false;
        }
        logger.exiting("SQLCommandDAO", "addNewCommand", true);
        return true;
    }

    @Override
    public List<Command> getCommandsByGameId(GameID id) {
        logger.entering("SQLCommandDAO", "getCommandByGameId", id);
        final String GET_COMMANDS =
                "SELECT " + CommandEntry.COLUMN_NAME_COMMAND +
                        " FROM " + CommandEntry.TABLE_NAME +
                        " WHERE " + CommandEntry.COLUMN_NAME_GAME_ID + " = ?";
        try
        {
            PreparedStatement statement = connection.prepareStatement(GET_COMMANDS);
            statement.setString(1, id.getId());

            ArrayList<Command> commandList = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                byte[] bytes = rs.getBytes(1);
                Command foundCommand = (Command) byteArrayToObject(bytes);
                commandList.add(foundCommand);

            }
            if (commandList.isEmpty())
            {
                logger.fine("Nothing found with game id: " + id.getId());
            } else
            {
                rs.close();
                statement.close();
                logger.exiting("SQLCommandDAO", "getCommand", id);
                return commandList;
            }
        } catch (SQLException | IOException | ClassNotFoundException e)
        {
            logger.warning(e + " - getting commands for id: " + id);
            e.printStackTrace();
        }
        logger.exiting("SQLCommandDAO", "getCommand", null);
        return null;
    }

    @Override
    public List<Command> getAllCommands(Command command) {
        return null;
    }

    @Override
    public boolean updateCommand(Command command) {
        return false;
    }

    @Override
    public boolean deleteCommandsByGameId(GameID id) {
        logger.entering("SQLCommandDAO", "deleteCommandsByGameId", id);
        final String DELETE_COMMANDS =
                "DELETE FROM " + CommandEntry.TABLE_NAME +
                        " WHERE " + CommandEntry.COLUMN_NAME_GAME_ID + " = ?";
        try
        {
            PreparedStatement statement = connection.prepareStatement(DELETE_COMMANDS);
            statement.setString(1, id.getId());
            statement.execute();
            statement.close();
            logger.exiting("SQLCommandDAO", "getCommand", id);
            return true;

        } catch (SQLException e)
        {
            logger.warning(e + " - getting commands for id: " + id);
            e.printStackTrace();
        }
        logger.exiting("SQLCommandDAO", "getCommand", null);
        return false;
    }

    private GameID getGameIdFromCommand(Command command) {
        String[] typeNames = command.getParameterTypeNames();
        GameID id = null;
        for (int i = 0; i < typeNames.length; i++) {
            if (typeNames[i].equals(GameID.class.getName())) {
                id = ((GameID) command.getParameters()[i]);
            }
        }
    }
}
