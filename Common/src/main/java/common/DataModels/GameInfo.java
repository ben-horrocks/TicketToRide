package common.DataModels;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ryan on 2/6/2018.
 */

public class GameInfo implements Serializable
{
    private GameID id;
    private String name;
    private String creatorName;
    private Set<Player> players = new HashSet<>();

    public GameInfo(Game g){
        this.id = g.getId();
        this.name = g.getName();
        this.creatorName = g.getCreatorName();
        this.players = g.getPlayers();
    }

    public GameInfo(GameID id, String name, String creatorName, Set<Player> players) {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.players = players;
    }

    public GameID getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public int getPlayerCount(){ return this.players.size(); }

    public boolean isFull() {
        return players.size() < 5;
    }
}
