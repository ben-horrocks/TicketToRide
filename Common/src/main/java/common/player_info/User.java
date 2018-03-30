package common.player_info;

import java.io.Serializable;

/**
 * A user in the game. A user has a username, password, screenName, and authorization token.
 */
public class User implements Serializable
{
    private Username username;
    private Password password;
    private AuthToken token;

    /**
     * @param username   The username for this user.
     * @param pass       The password for this user.
     * @pre All parameters are non-null.
     * @post Will create a user with provided Username, Password, ScreenName and a new authorization token.
     */
    public User(Username username, Password pass)
    {
        this.username = username;
        this.password = pass;
        this.token = new AuthToken();
    }

    public Username getUsername()
    {
        return username;
    }

    public String getStringUserName()
    {
        return username.getName();
    }

    public void setName(Username username)
    {
        this.username = username;
    }

    public Password getPassword()
    {
        return password;
    }

    public AuthToken getToken()
    {
        return token;
    }

    public void setToken(AuthToken token)
    {
        this.token = token;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        User user = (User) o;

        return username.equals(user.username) && password.equals(user.password);
        // if authToken is used in equals method, then no 2 users
        // with all the same data can be equal.
    }

    @Override
    public int hashCode()
    {
        int result = username.getName().length();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + token.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Username: " + username.toString();
    }
}
