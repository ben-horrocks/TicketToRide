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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Password)) return false;

    Password password = (Password) o;

    return getPass() != null ? getPass().equals(password.getPass()) : password.getPass() == null;
  }

  @Override
  public int hashCode() {
    return getPass() != null ? getPass().hashCode() : 0;
  }
}
