package common.DataModels;

import java.io.Serializable;

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
    return !name.contains(" ");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ScreenName)) return false;

    ScreenName that = (ScreenName) o;

    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    return getName() != null ? getName().hashCode() : 0;
  }
}
