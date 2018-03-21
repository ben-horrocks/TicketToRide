package common.DataModels;

/**
 * Created by jhens on 3/20/2018.
 */

public class DrawFaceUpRequest
{
    GameID id;
    Username username;
    int index;

    public DrawFaceUpRequest(GameID id, Username username, int index)
    {
        this.id = id;
        this.username = username;
        this.index = index;
    }

    public GameID getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public int getIndex() {
        return index;
    }
}
