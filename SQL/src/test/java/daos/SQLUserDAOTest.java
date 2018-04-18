package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.player_info.Password;
import common.player_info.User;
import common.player_info.Username;

import static org.junit.Assert.*;

/**
 * Created by Kavika F.
 */
public class SQLUserDAOTest
{
	private UserDAO dao;

	@Before
	public void setUp()
	{
		this.dao = new UserDAO();
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
	public void addNewUser()
	{
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		boolean success = dao.addNewUser(user);
		assertTrue(success);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		success = dao.addNewUser(user);
		assertTrue(success);

		username = new Username(test);
		password = new Password(pw);
		User dbUser = dao.getUser(username);
		user = new User(username, password);
		assertTrue(dbUser.equals(user));

		username = new Username(anotherTest);
		password = new Password(panda);
		dbUser = dao.getUser(username);
		user = new User(username, password);
		assertTrue(dbUser.equals(user));
	}

	@Test
	public void getUser()
	{
		addNewUser();
	}

	@Test
	public void getAllUsers()
	{
		List<User> users = new ArrayList<>();
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		users.add(user);
		boolean success = dao.addNewUser(user);
		assertTrue(success);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		users.add(user);
		success = dao.addNewUser(user);
		assertTrue(success);

		List<User> sqlUsers = dao.getAllUsers();
		for (User u : sqlUsers)
		{
			assertTrue(users.contains(u));
		}
	}

	@Test
	public void updateUser()
	{
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		dao.addNewUser(user);

		username = new Username(anotherTest);
		password = new Password(panda);
		user = new User(username, password);
		dao.addNewUser(user);

		username = new Username("new name'");
		user = new User(username, password);
		boolean successful = dao.updateUser(user);
		assertFalse(successful);

		username = new Username(test);
		password = new Password("rofl");
		user = new User(username, password);
		successful = dao.updateUser(user);
		assertTrue(successful);
	}

	@Test
	public void deleteUser()
	{
		String test = "Test";
		String pw = "Password";
		String anotherTest = "AnotherTest";
		String panda = "Pa4nda";
		Username username = new Username(test);
		Password password = new Password(pw);
		User user = new User(username, password);
		dao.addNewUser(user);

		username = new Username(anotherTest);
		password = new Password(panda);
		User user2 = new User(username, password);
		dao.addNewUser(user2);

		dao.deleteUser(user.getUsername());
		user = dao.getUser(user.getUsername());
		assertTrue(user == null);
	}
}