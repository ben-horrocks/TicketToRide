package common.request;

import common.game_data.GameID;
import common.player_info.Player;
import common.player_info.Username;

/**
 * Created by jhens on 3/21/2018.
 */

public class DestDrawRequest {

    GameID id;
    Username username;

    public DestDrawRequest(GameID id, Username username)
    {
        this.id = id;
        this.username = username;
    }

    public Username getUsername() {
        return username;
    }

    public GameID getId() {
        return id;
    }
}
