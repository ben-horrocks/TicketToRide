package cs340.TicketClient.common.DataModels;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class Username
{
  private static Set<String> usedname = new HashSet<>();
  private String name;
  public Username(String name)
  {
    this.name = name;
  }

  public static boolean isValidUserName(String name)
  {
    if(usedname.contains(name))
    {
      return false;
    } else
    {
      return true;
    }
  }
}
