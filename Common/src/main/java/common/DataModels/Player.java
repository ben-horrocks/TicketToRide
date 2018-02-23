package common.DataModels;

import java.io.Serializable;

public class Player implements Serializable
{
  private int points;
  private Username name;
  private Password pass;
  private ScreenName screenName;
  private AuthToken token;

  public Player(Username name, Password pass, ScreenName screenName)
  {
    this.points = 0;
    this.name = name;
    this.pass = pass;
    this.screenName = screenName;
    this.token = new AuthToken();
  }

  public int getPoints()
  {
    return points;
  }

  public void setPoints(int points)
  {
    this.points = points;
  }

  public void incrementPoints(int newpoints)
  {
    this.points += points;
  }

  public Username getUsername()
  {
    return name;
  }

  public String getName() {return name.getName(); }

  public void setName(Username name)
  {
    this.name = name;
  }

  public Password getPass()
  {
    return pass;
  }

  public void setPass(Password pass)
  {
    this.pass = pass;
  }

  public ScreenName getScreenName()
  {
    return screenName;
  }

  public void setScreenName(ScreenName screenName)
  {
    this.screenName = screenName;
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Player player = (Player) o;

		if (points != player.points) return false;
		if (!name.equals(player.name)) return false;
		if (!pass.equals(player.pass)) return false;
		if (!screenName.equals(player.screenName)) return false;
		return token.equals(player.token);
	}

	@Override
	public int hashCode()
	{
		int result = points;
		result = 31 * result + name.hashCode();
		result = 31 * result + pass.hashCode();
		result = 31 * result + screenName.hashCode();
		result = 31 * result + token.hashCode();
		return result;
	}
}
