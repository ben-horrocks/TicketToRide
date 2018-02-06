package common.DataModels;

import java.io.Serializable;

/**
 * Created by Ben_D on 1/29/2018.
 */

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

  public Username getName()
  {
    return name;
  }

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
}
