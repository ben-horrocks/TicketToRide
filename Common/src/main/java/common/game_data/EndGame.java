package common.game_data;

import java.io.Serializable;
import java.util.*;

import common.player_info.Player;
import common.player_info.Username;

public class EndGame implements Serializable
{
    List<EndGamePlayer> players = new ArrayList<>();

    public EndGame(List<Player> players)
    {
        for(Player player : players)
        {

            this.players.add(new EndGamePlayer(player.getUsername(), player.getPoints()));
        }
    }

    public List<EndGamePlayer> getPlayers()
    {
        return players;
    }

    public class EndGamePlayer
    {
        Username username;
        Point point;

        public EndGamePlayer(Username name, Point point)
        {
            this.username = name;
            this.point = point;
        }

        public String getUsername()
        {
            return username.getName();
        }

        public Point getPoint()
        {
            return point;
        }

        public void setUsername(Username username)
        {
            this.username = username;
        }

        public void setPoint(Point point)
        {
            this.point = point;
        }
    }
}
