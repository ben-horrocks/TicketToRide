package common.request;

import common.game_data.GameID;
import common.player_info.Username;

/**
 * Created by jhens on 4/8/2018.
 */

public class ExitRequest {
    Username username;
    GameID id;

    public ExitRequest(Username username, GameID id)
    {
        this.username = username;
        this.id = id;
    }

    public Username getUsername() {
        return username;
    }

    public GameID getId() {
        return id;
    }
}
