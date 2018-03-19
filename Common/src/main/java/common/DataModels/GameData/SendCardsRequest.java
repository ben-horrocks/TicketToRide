package common.DataModels.GameData;

import java.io.Serializable;

import common.DataModels.*;

public class SendCardsRequest implements Serializable
{

    private GameID id;
    private Username user;
    private HandDestinationCards selected;
    private HandDestinationCards returned;

    public SendCardsRequest(GameID id, Username user, HandDestinationCards selected,
                            HandDestinationCards returned)
    {
        this.id = id;
        this.user = user;
        this.selected = selected;
        this.returned = returned;
    }

    public HandDestinationCards getReturned()
    {
        return returned;
    }

    public HandDestinationCards getSelected()
    {
        return selected;
    }

    public GameID getId()
    {
        return id;
    }

    public Username getUser()
    {
        return user;
    }
}
