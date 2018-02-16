package common.DataModels;

import java.io.Serializable;

public class Username implements Serializable
{
  private String name;
  public Username(String name)
  {
    this.name = name;
  }

  public static boolean isValidUserName(String name)
  {
    return name.contains(" ");
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
