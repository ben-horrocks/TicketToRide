package cs340.TicketClient.common.DataModels;

import java.util.UUID;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class GameID
{
  private String id;

  public GameID()
  {
    this.id = generateNewId();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  private String generateNewId()
  {
    StringBuilder ID = new StringBuilder();
    ID.setLength(0);
    ID.append(UUID.randomUUID().toString());
    ID.deleteCharAt(23);
    ID.deleteCharAt(18);
    ID.deleteCharAt(13);
    ID.deleteCharAt(8);
    return ID.toString();
  }
}
