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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Username username = (Username) o;

		return name.equals(username.name);
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}
