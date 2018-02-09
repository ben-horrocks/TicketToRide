package common.DataModels;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Password implements Serializable
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
    if (pass.contains(" ")) {
      return false;
    }
    return true;
  }
}
