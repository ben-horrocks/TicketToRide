package common.DataModels;

/**
 * Created by jhens on 3/8/2018.
 */

public class DestDrawRequest {
    GameID id;
    Username user;

    public DestDrawRequest(GameID id, Username user)
    {
        this.id = id;
        this.user = user;
    }

    public GameID getId() {
        return id;
    }

    public Username getUser() {
        return user;
    }
}
