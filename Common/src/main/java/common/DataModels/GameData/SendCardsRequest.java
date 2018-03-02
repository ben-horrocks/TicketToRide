package common.DataModels.GameData;

import java.util.ArrayList;

import common.DataModels.DestinationCard;
import common.DataModels.GameID;
import common.DataModels.Username;

/**
 * Created by jhens on 3/1/2018.
 */

public class SendCardsRequest {

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

    public ArrayList<DestinationCard> getReturned() {
        return returned;
    }

    public ArrayList<DestinationCard> getSelected() {
        return selected;
    }

    public GameID getId() {
        return id;
    }

    public Username getUser() {
        return user;
    }
}
