package common.DataModels;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ScreenName
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
    return true;
  }
}
