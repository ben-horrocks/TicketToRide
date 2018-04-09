package common.request;

import common.game_data.GameID;
import common.player_info.User;

/**
 * Created by jhens on 4/8/2018.
 */

public class ResumeRequest {
    User user;
    GameID id;

    public ResumeRequest(User user, GameID id)
    {
        this.user = user;
        this.id = id;
    }

    public GameID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
