package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import CS340.TicketServer.ServerFacade;
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
import communicators.ConnectionSetup;

import static org.junit.Assert.*;

/**
 * Created by Carter on 4/11/18.
 */
public class SQLCommandDAOTest {

    //Database connection and DAO
    Connection mConnection;
    SQLCommandDAO commandDao;

    //Info for creating CommandParams
    private static final String stringClassName = String.class.getName();
    private static final String gameIDClassname = GameID.class.getName();
    private static final String playerClassName = User.class.getName();
    private static final String usernameClassName = Username.class.getName();
    private static final String handDestinationCardsClassName =
            HandDestinationCards.class.getName();
    private static final String handTrainCardClassName =
            HandTrainCards.class.getName();
    private static final String chatItemClassName = ChatItem.class.getName();
    private static final String edgeClassName = Edge.class.getName();

    @Before
    public void setUp() throws Exception {
        this.mConnection = ConnectionSetup.setup();
        commandDao = new SQLCommandDAO(mConnection);
    }

    @After
    public void tearDown() throws Exception {
        mConnection.close();
    }

    @Test
    public void createTable() throws Exception {

       boolean tableCreated = commandDao.createTable();
       assert(tableCreated);

        try {
            DatabaseMetaData metaData = mConnection.getMetaData();
            ResultSet rs = metaData.getTables(null, null,
                    SQLCommandDAO.CommandEntry.COLUMN_NAME_COMMAND, new String[] {"TABLE"});

            if (rs.next())
            {
                System.out.println("	TABLE_NAME: " + rs.getString("TABLE_NAME"));
            }
            else
            {
                assert(false);
            }
        }
		catch (SQLException e)
        {
            // Shouldn't really have errors.
            System.out.println("SQLException when checking if table exists - " + e);
            assert(false);
        }

    }

    @Test
    public void deleteTable() throws Exception {
        boolean success = commandDao.deleteTable();
        assert(success);
        try
        {
            DatabaseMetaData meta = mConnection.getMetaData();
            ResultSet rs = meta.getTables(null, null,
                    SQLCommandDAO.CommandEntry.TABLE_NAME, new String[] {"TABLE"});
            if (rs.next())
            {
                assert(false);
            }
            else
            {
                System.out.println("No such table... Good!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException when checking if table exists - " + e);
            assert(false);
        }
    }

    @Test
    public void addNewCommand() throws Exception {
        //Make User
        String test = "Test";
        String pw = "Password";
        String anotherTest = "AnotherTest";
        String panda = "Pa4nda";
        Username username = new Username(test);
        Password password = new Password(pw);
        User user = new User(username, password);

        //Make Command
        String[] parameterTypes = {usernameClassName};
        Object[] parameters = {user};
        CommandParams commandParams =
                new CommandParams("getAvailableGameInfo", parameterTypes, parameters);
        Command serverCommand =
                new Command(commandParams, ServerFacade.class.getName());

        //Add command to DAO
        commandDao.addNewCommand(serverCommand);

        //Check to see if command was added
		GameID id = new GameID();
        List<Command> dbCommands = commandDao.getCommandsByGameId(id);

    }

    @Test
    public void getCommandsByGameId() throws Exception {

    }

    @Test
    public void deleteCommandsByGameId() throws Exception {

    }

}