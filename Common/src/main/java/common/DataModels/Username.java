package common.DataModels;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Username
{
  private String name;
  public Username(String name)
  {
    this.name = name;
  }

  public static boolean isValidUserName(String name)
  {
    Pattern pattern = Pattern.compile("\\s");
    Matcher matcher = pattern.matcher(name);
    return matcher.find();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
