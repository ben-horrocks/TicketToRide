package common.DataModels;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Username implements Serializable
{
  private String name;
  public Username(String name)
  {
    this.name = name;
  }

  public static boolean isValidUserName(String name)
  {
    if (name.contains(" ")) {
      return false;
    }
    return true;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
