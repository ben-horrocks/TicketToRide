package common.request;

import common.cards.HandTrainCards;
import common.game_data.GameID;
import common.map.Edge;
import common.player_info.Username;

/**
 * Created by jhens on 3/24/2018.
 */

public class ClaimRequest {

    private GameID id;
    private Username user;
    private Edge edge;
    private HandTrainCards cards;


    public ClaimRequest(GameID id, Username user, Edge edge, HandTrainCards cards)
    {
        this.id = id;
        this.user = user;
        this.edge = edge;
        this.cards = cards;
    }

    public GameID getId() {
        return id;
    }

    public Username getUser() {
        return user;
    }

    public Edge getEdge() {
        return edge;
    }

    public HandTrainCards getCards() {
        return cards;
    }
}
