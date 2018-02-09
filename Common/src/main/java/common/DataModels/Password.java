package common.DataModels;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Pattern pattern = Pattern.compile("\\s");
    Matcher matcher = pattern.matcher(pass);
    return matcher.find();
  }
}
