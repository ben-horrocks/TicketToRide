package common.DataModels;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsernameTest
{
	private Username username;
	private Username name1;
	private Username name2;
	private Username badName;
	private Username goodName;

	@Before
	public void setUp()
	{
		username = new Username("userName");
		name1 = new Username("MyName");
		name2 = new Username("AlsoMyName");
		badName = new Username("I'm a bad boii ");
		goodName = new Username("BarkBark");
	}

	@Test
	public void isValidUserName() throws Exception
	{
		assertEquals(true, Username.isValidUserName("userName"));
		assertEquals(true, Username.isValidUserName("MyName"));
		assertEquals(true, Username.isValidUserName("AlsoMyName"));
		assertEquals(false, Username.isValidUserName("I'm a bad boii "));
		assertEquals(true, Username.isValidUserName("BarkBark"));
	}

	@Test
	public void getName() throws Exception
	{
		assertEquals("userName", username.getName());
		assertEquals("MyName", name1.getName());
		assertEquals("AlsoMyName", name2.getName());
		assertEquals("I'm a bad boii ", badName.getName());
		assertEquals("BarkBark", goodName.getName());
	}

	@Test
	public void setName() throws Exception
	{
		username.setName("pickle");
		assertEquals("pickle", username.getName());
		assertEquals("MyName", name1.getName());
		name1.setName("alsoPickle");
		assertEquals("alsoPickle", name1.getName());
		assertEquals("AlsoMyName", name2.getName());
		name2.setName("wait...");
		name2.setName("No wait, this name!");
		name2.setName("naw, just this one");
		name2.setName("ThisOneForSure");
		assertEquals("ThisOneForSure", name2.getName());
	}

	@Test
	public void equals() throws Exception
	{
		assertNotEquals(username, name1);
		assertNotEquals(username, name2);
		assertNotEquals(username, badName);
		assertNotEquals(username, goodName);
		assertNotEquals(name1, name2);
		assertNotEquals(name1, badName);
		assertNotEquals(name1, goodName);
		assertNotEquals(name2, badName);
		assertNotEquals(name2, goodName);
		assertNotEquals(badName, goodName);
	}

	@Test
	public void testToString() throws Exception
	{
		assertEquals("userName", username.toString());
		assertEquals("MyName", name1.toString());
		assertEquals("AlsoMyName", name2.toString());
		assertEquals("I'm a bad boii ", badName.toString());
		assertEquals("BarkBark", goodName.toString());
	}

}