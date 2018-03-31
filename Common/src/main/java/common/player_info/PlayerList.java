package common.player_info;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jhens on 3/30/2018.
 */

public class PlayerList implements Serializable{


    ArrayList<Player> players;

    public PlayerList(ArrayList<Player> players)
    {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
