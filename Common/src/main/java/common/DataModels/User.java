package common.DataModels;

import java.io.Serializable;

public class User implements Serializable
{
	private Username username;
	private Password pass;
	private ScreenName screenName;
	private AuthToken token;

	public User(Username username, Password pass, ScreenName screenName)
	{
		this.username = username;
		this.pass = pass;
		this.screenName = screenName;
		this.token = new AuthToken();
	}

	public Username getUsername() { return username; }

	public String getStringUserName() {return username.getName(); }

	public void setName(Username username) { this.username = username; }

	public Password getPass() { return pass; }

	public ScreenName getScreenName() { return screenName; }

	public AuthToken getToken() { return token; }

	public void setToken(AuthToken token) { this.token = token; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!username.equals(user.username)) return false;
		if (!pass.equals(user.pass)) return false;
		return screenName.equals(user.screenName);
		// if authToken is used in equals method, then no 2 users
		// with all the same data can be equal.
	}

	@Override
	public int hashCode()
	{
		int result = username.getName().length();
		result = 31 * result + username.hashCode();
		result = 31 * result + pass.hashCode();
		result = 31 * result + screenName.hashCode();
		result = 31 * result + token.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return "Username: " + username.toString();
	}
}
