package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import communicators.ConnectionSetup;

import static org.junit.Assert.*;

/**
 * Created by Kavika F.
 */
public class SQLUserDAOTest
{
	private Connection connection;

	@Before
	public void setUp() throws Exception
	{
		this.connection = ConnectionSetup.setup();
	}

	@After
	public void tearDown() throws Exception
	{
		connection.close();
	}

	@Test
	public void createTable()
	{
	}

	@Test
	public void deleteTable()
	{
	}

	@Test
	public void addNewUser()
	{
	}

	@Test
	public void getUser()
	{
	}

	@Test
	public void getAllUsers()
	{
	}

	@Test
	public void updateUser()
	{
	}

	@Test
	public void deleteUser()
	{
	}
}