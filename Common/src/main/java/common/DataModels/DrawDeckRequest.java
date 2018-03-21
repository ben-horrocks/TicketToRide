package common.DataModels;

/**
 * Created by jhens on 3/20/2018.
 */

public class DrawDeckRequest
{
    GameID id;
    Username username;

    public DrawDeckRequest(GameID id, Username username)
    {
        this.id = id;
        this.username = username;
    }

    public GameID getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }
}
