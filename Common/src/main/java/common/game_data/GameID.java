package common.game_data;

import java.io.Serializable;
import java.util.UUID;

public class GameID implements Serializable
{
    private String id;

    public GameID()
    {
        this.id = generateNewId();
    }

    public GameID(String id) {this.id = id;}

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof GameID))
        {
            return false;
        }

        GameID gameID = (GameID) o;

        return getId() != null ? getId().equals(gameID.getId()) : gameID.getId() == null;
    }

    @Override
    public int hashCode()
    {
        return getId() != null ? getId().hashCode() : 0;
    }
}
