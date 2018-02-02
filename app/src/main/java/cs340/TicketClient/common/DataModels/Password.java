package cs340.TicketClient.common.DataModels;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Password
{
  private String pass;

  public Password(String pass)
  {
    this.pass = pass;
  }

  public String getPass()
  {
    return pass;
  }

  public void setPass(String pass)
  {
    this.pass = pass;
  }

  public static boolean isValidPass(String pass)
  {
    return true;
  }
}
