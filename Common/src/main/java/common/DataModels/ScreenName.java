package common.DataModels;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ScreenName implements Serializable
{
  private String name;

  public ScreenName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public static boolean isValidScreenName(String name)
  {
    if (name.contains(" ")) {
      return false;
    }
    return true;
  }
}
