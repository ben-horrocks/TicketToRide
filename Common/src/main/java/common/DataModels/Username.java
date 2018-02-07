package common.DataModels;

import java.util.HashSet;
import java.util.Set;

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

  public String getName() {return name;}
  public static boolean isValidUserName(String name)
  {
    return true;
  }
}
