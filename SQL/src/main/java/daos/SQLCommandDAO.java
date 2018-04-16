package daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.chat.ChatItem;
import common.communication.Command;
import common.communication.CommandParams;
import common.game_data.GameID;
import common.game_data.GameInfo;
import common.map.Edge;
import common.player_info.Password;
import common.player_info.User;
import common.player_info.Username;

/**
 * Created by Carter on 4/9/18.
 */

public class SQLCommandDAO extends AbstractSQL_DAO implements ICommandDAO {

    public SQLCommandDAO(Connection connection) { super(connection); }

    public static class CommandEntry
    {
        static final String TABLE_NAME = "Commands";
        static final String COLUMN_NAME_GAME_ID = "GameID";
        static final String COLUMN_NAME_COMMAND = "Command";
    }


    @Override
    boolean createTable() {
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
            e.printStackTrace();
            //logger.warning(e + " - creating table " + CommandEntry.TABLE_NAME);
            //logger.exiting("SQLCommandDAO", "createTable", false);
            return false;
        }
        return true;
    }

    @Override
    boolean deleteTable() {
        final String DELETE_COMMAND_TABLE = "DROP TABLE " + CommandEntry.TABLE_NAME;
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_COMMAND_TABLE);
        } catch (SQLException e)
        {
            e.printStackTrace();
            //logger.warning(e + " - deleting table " + CommandEntry.TABLE_NAME);
            //logger.exiting("SQLCommandDAO", "deleteTable", false);
            return false;
        }
        //logger.exiting("SQLCommandDAO", "deleteTable", true);
        return true;
    }

    @Override
    public boolean addNewCommand(Command command) {
        //logger.entering("SQLPlayerDAO", "addNewCommand", command);
        final String INSERT_PLAYER =
                "INSERT INTO Commands (" + CommandEntry.COLUMN_NAME_GAME_ID +
                        ", " + CommandEntry.COLUMN_NAME_COMMAND +
                        ") VALUES (?,?)";

        //get game id from provided command
        GameID id = getGameIdFromCommand(command);
        if (id == null) {
            //logger.warning("did not find id for command: " + command);
            //logger.exiting("SQLCommandDAO", "addNewCommand", false);
            return false;
        }

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
            e.printStackTrace();
            //logger.warning(e + " - adding new command " + command);
            //logger.exiting("SQLCommandDAO", "addNewCommand", false);
            return false;
        }
        //logger.exiting("SQLCommandDAO", "addNewCommand", true);
        return true;
    }

    @Override
    public List<Command> getCommandsByGameId(GameID id) {
        //logger.entering("SQLCommandDAO", "getCommandByGameId", id);
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
                //logger.fine("Nothing found with game id: " + id.getId());
            } else
            {
                rs.close();
                statement.close();
                //logger.exiting("SQLCommandDAO", "getCommand", id);
                return commandList;
            }
        } catch (SQLException | IOException | ClassNotFoundException e)
        {
            //logger.warning(e + " - getting commands for id: " + id);
            e.printStackTrace();
        }
        //logger.exiting("SQLCommandDAO", "getCommand", null);
        return null;
    }

    @Override
    public boolean deleteCommandsByGameId(GameID id) {
        //logger.entering("SQLCommandDAO", "deleteCommandsByGameId", id);
        final String DELETE_COMMANDS =
                "DELETE FROM " + CommandEntry.TABLE_NAME +
                        " WHERE " + CommandEntry.COLUMN_NAME_GAME_ID + " = ?";
        try
        {
            PreparedStatement statement = connection.prepareStatement(DELETE_COMMANDS);
            statement.setString(1, id.getId());
            statement.execute();
            statement.close();
            //logger.exiting("SQLCommandDAO", "getCommand", id);
            return true;

        } catch (SQLException e)
        {
            //logger.warning(e + " - getting commands for id: " + id);
            e.printStackTrace();
        }
        //logger.exiting("SQLCommandDAO", "getCommand", null);
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
        return id;
    }

    public static void main (String[] args) {
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
        Connection connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        }
        catch (SQLException ex) {
            // ERROR
            System.out.println("ERROR: SQL exception while attempting to open database");
            ex.printStackTrace();
        }

        //Set up all necessary stuff for running tests
        String stringClassName = String.class.getName();
        String gameIDClassname = GameID.class.getName();
        String playerClassName = User.class.getName();
        String usernameClassName = Username.class.getName();
        String handDestinationCardsClassName = HandDestinationCards.class.getName();
        String handTrainCardClassName = HandTrainCards.class.getName();
        String chatItemClassName = ChatItem.class.getName();
        String edgeClassName = Edge.class.getName();

        //Make user
        String test = "Test";
        String pw = "Password";
        String anotherTest = "AnotherTest";
        String panda = "Pa4nda";
        Username username = new Username(test);
        Password password = new Password(pw);
        User user = new User(username, password);

        //Make new game
        String gameName = "newGame";
        GameID id = new GameID();

        //Make command
        String[] parameterTypes = {usernameClassName, gameIDClassname};
        Object[] parameters = {user, id};
        CommandParams commandParams =
                new CommandParams("getAvailableGameInfo", parameterTypes, parameters);
//        Command serverCommand =
//                new Command(commandParams, ServerFacade.class.getName());

        //RUN TESTS
        SQLCommandDAO dao = new SQLCommandDAO(connection);

        dao.createTable();
//        dao.addNewCommand(serverCommand);
        dao.getCommandsByGameId(id);

    }
}
