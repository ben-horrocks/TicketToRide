package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import common.game_data.GameID;
import common.player_info.Password;
import common.player_info.Player;
import common.player_info.PlayerColor;
import common.player_info.User;
import common.player_info.Username;

import static org.junit.Assert.*;

/**
 * Created by Carter on 4/11/18.
 */
public class SQLPlayerDAOTest
{
	private PlayerDAO dao;

    @Before
    public void setUp()
	{
		this.dao = new PlayerDAO();
    }

    @After
    public void tearDown()
	{
		dao.closeConnection();
    }

    @Test
    public void createTable()
	{
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
    public void deleteTable()
	{
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
    public void addNewPlayer()
	{
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		Player player = new Player(user, PlayerColor.BLACK);
		GameID gameID = new GameID();
		boolean success = dao.addNewPlayer(gameID, player);
		assertTrue(success);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		Player player2 = new Player(user, PlayerColor.GREEN);
		success = dao.addNewPlayer(gameID, player2);
		assertTrue(success);

		Player sqlPlayer = dao.getPlayer(gameID, player2.getUsername());
		assertTrue(sqlPlayer.equals(player2));

		username = new Username(test);
		password = new Password(pw);
		user = new User(username, password);
		player = new Player(user, PlayerColor.BLACK);
		sqlPlayer = dao.getPlayer(gameID, player.getUsername());
		assertTrue(sqlPlayer.equals(player));
    }

    @Test
    public void getPlayer()
	{
		addNewPlayer();
    }

    @Test
    public void getAllPlayers()
	{
		List<Player> players = new ArrayList<>();
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		Player player = new Player(user, PlayerColor.BLACK);
		GameID gameID = new GameID();
		boolean success = dao.addNewPlayer(gameID, player);
		assertTrue(success);
		players.add(player);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		Player player2 = new Player(user, PlayerColor.GREEN);
		success = dao.addNewPlayer(gameID, player2);
		assertTrue(success);
		players.add(player2);

		List<Player> sqlPlayers = dao.getAllPlayers();
		for (Player p : sqlPlayers)
		{
			assertTrue(players.contains(p));
		}
    }

    @Test
    public void updatePlayer()
	{
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		Player player = new Player(user, PlayerColor.BLACK);
		GameID gameID = new GameID();
		boolean success = dao.addNewPlayer(gameID, player);
		assertTrue(success);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		Player player2 = new Player(user, PlayerColor.GREEN);
		success = dao.addNewPlayer(gameID, player2);
		assertTrue(success);

		Player sqlPlayer = dao.getPlayer(gameID, player2.getUsername());
		assertTrue(sqlPlayer.equals(player2));

		username = new Username("new username'");
		user = new User(username, password);
		player = new Player(user, PlayerColor.BLUE);
		success = dao.updatePlayer(gameID, player);
		assertFalse(success);

		player2.addPoints(45);
		success = dao.updatePlayer(gameID, player2);
		assertTrue(success);
    }

    @Test
    public void deletePlayer()
	{
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		Player player = new Player(user, PlayerColor.BLACK);
		GameID gameID = new GameID();
		boolean success = dao.addNewPlayer(gameID, player);
		assertTrue(success);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		Player player2 = new Player(user, PlayerColor.GREEN);
		success = dao.addNewPlayer(gameID, player2);
		assertTrue(success);

		dao.deletePlayer(gameID, player);
		player = dao.getPlayer(gameID, player.getUsername());
		assertTrue(player == null);
    }

}