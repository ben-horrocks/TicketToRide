package common.game_data;

import java.util.*;

import common.player_info.Player;
import common.player_info.Username;

/**
 * Created by Ben_D on 3/24/2018.
 */

public class EndGame
{
    Map<Username, Point> players = new HashMap<>();

    public EndGame(List<Player> players)
    {
        for(Player player : players)
        {
            this.players.put(player.getUsername(), player.getPoints());
        }
    }
}
