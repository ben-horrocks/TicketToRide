package common.DataModels;

import org.junit.Before;
import org.junit.Test;

import common.player_info.Password;
import common.player_info.User;
import common.player_info.Username;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest
{
    private User user;
    private User user1;
    private User user2;
    private User user3;
    private User userTest;

    @Before
    public void setUp()
    {
        Username username = new Username("username");
        Password password = new Password("password");
        user = new User(username, password);

        username = new Username("userName");
        password = new Password("password");
        user1 = new User(username, password);

        username = new Username("chuckyNorris");
        password = new Password("password");
        user2 = new User(username, password);

        username = new Username("lemon Boi");
        password = new Password("hickory Smokes");
        user3 = new User(username, password);

        username = new Username("   Hmm");
        password = new Password(" oh ... ");
        userTest = new User(username, password);
    }

    @Test
    public void getUsername() throws Exception
    {
        Username username = new Username("username");
        assertEquals(username, user.getUsername());
        username = new Username("userName");
        assertEquals(username, user1.getUsername());
        username = new Username("chuckyNorris");
        assertEquals(username, user2.getUsername());
        username = new Username("lemon Boi");
        assertEquals(username, user3.getUsername());
        username = new Username("   Hmm");
        assertEquals(username, userTest.getUsername());
    }

    @Test
    public void getStringUserName() throws Exception
    {
        String username = "username";
        assertEquals(username, user.getStringUserName());
        username = "userName";
        assertEquals(username, user1.getStringUserName());
        username = "chuckyNorris";
        assertEquals(username, user2.getStringUserName());
        username = "lemon Boi";
        assertEquals(username, user3.getStringUserName());
        username = "   Hmm";
        assertEquals(username, userTest.getStringUserName());
    }

    @Test
    public void setName() throws Exception
    {
        assertEquals(new Username("username"), user.getUsername());
        Username username = new Username("taco bell");
        user.setName(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void getPassword() throws Exception
    {
        Password password = new Password("password");
        assertEquals(password, user.getPassword());
        password = new Password("password");
        assertEquals(password, user1.getPassword());
        password = new Password("password");
        assertEquals(password, user2.getPassword());
        password = new Password("hickory Smokes");
        assertEquals(password, user3.getPassword());
        password = new Password(" oh ... ");
        assertEquals(password, userTest.getPassword());
    }

    @Test
    public void getScreenName() throws Exception
    {
    }

    @Test
    public void equals() throws Exception
    {
        Username username = new Username("username");
        Password password = new Password("password");
        User copyUser = new User(username, password);
        assertEquals(copyUser, user);
        assertNotEquals(copyUser, user1);
        assertNotEquals(copyUser, user2);
        assertNotEquals(copyUser, user3);
        assertNotEquals(copyUser, userTest);
        assertNotEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user1, userTest);
        assertNotEquals(user2, user3);
        assertNotEquals(user2, userTest);
        assertNotEquals(user3, userTest);
    }

    @Test
    public void testToString() throws Exception
    {
        String toString = "Username: " + user.getStringUserName();
        assertEquals(toString, user.toString());
        toString = "Username: " + user1.getStringUserName();
        assertEquals(toString, user1.toString());
        toString = "Username: " + user2.getStringUserName();
        assertEquals(toString, user2.toString());
        toString = "Username: " + user3.getStringUserName();
        assertEquals(toString, user3.toString());
        toString = "Username: " + userTest.getStringUserName();
        assertEquals(toString, userTest.toString());
    }

}