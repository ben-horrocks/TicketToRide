package common.DataModels.GameData;

import java.io.Serializable;
import java.util.ArrayList;

import common.DataModels.DestinationCard;
import common.DataModels.GameID;
import common.DataModels.Username;

public class SendCardsRequest implements Serializable {

    private GameID id;
    private Username user;
    private ArrayList<DestinationCard> selected;
    private ArrayList<DestinationCard> returned;

    public SendCardsRequest(GameID id, Username user, ArrayList<DestinationCard> selected, ArrayList<DestinationCard> returned)
    {
        this.id = id;
        this.user = user;
        this.selected = selected;
        this.returned = returned;
    }

    public ArrayList<DestinationCard> getReturned() { return returned; }

    public ArrayList<DestinationCard> getSelected() { return selected; }

    public GameID getId() { return id; }

    public Username getUser() { return user; }
}
