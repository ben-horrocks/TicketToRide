package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import communicators.ConnectionSetup;

/**
 * Created by Carter on 4/11/18.
 */
public class SQLCommandDAOTest {

	private static final String stringClassName = String.class.getName();
	private static final String gameIDClassname = GameID.class.getName();
	private static final String playerClassName = User.class.getName();
	private static final String usernameClassName = Username.class.getName();
	private static final String handDestinationCardsClassName = HandDestinationCards.class.getName();
	private static final String handTrainCardClassName = HandTrainCards.class.getName();
	private static final String chatItemClassName = ChatItem.class.getName();
	private static final String edgeClassName = Edge.class.getName();
    private CommandDAO dao;

    @Before
    public void setUp() {
        dao = new CommandDAO();
    }

    @After
    public void tearDown() {
        dao.closeConnection();
    }

    @Test
    public void createTable() {
		if (dao.tableExists())
		{
			dao.deleteTable();
		}
		boolean success = dao.createTable();
		assertTrue(success);
		success = dao.tableExists();
		assertTrue(success);
    }

    @Test
    public void deleteTable() {
		if (!dao.tableExists())
		{
			dao.createTable();
		}
		boolean success = dao.deleteTable();
		assertTrue(success);
		success = dao.tableExists();
		assertFalse(success);
    }

    @Test
    public void addNewCommand() {
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
//        Command serverCommand =
//                new Command(commandParams, ServerFacade.class.getName());

        //Add command to DAO
//        commandDao.addNewCommand(serverCommand);

        //Check to see if command was added
		GameID id = new GameID();
        List<Command> dbCommands = dao.getCommandsByGameId(id);

    }

    @Test
    public void getCommandsByGameId() throws Exception {

    }

    @Test
    public void deleteCommandsByGameId() throws Exception {

    }

}