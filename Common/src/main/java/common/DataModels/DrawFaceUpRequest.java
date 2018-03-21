package common.DataModels;

import common.game_data.GameID;
import common.player_info.Username;

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
