package common.DataModels;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import common.DataModels.GameData.ServerGameData;

public class GameInfo implements Serializable
{
    private GameID id;
    private String name;
    private String creatorName;
    private Set<User> users = new HashSet<>();

    public GameInfo(ServerGameData g){
        this.id = g.getId();
        this.name = g.getName();
        this.creatorName = g.getCreatorName();
        this.users = g.getUsers();
    }

    public GameInfo(GameID id, String name, String creatorName, Set<User> users) {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.users = users;
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

    public Set<User> getUsers() {return this.users;}

    public int getPlayerCount(){ return this.users.size(); }

    public boolean isFull() {
        return users.size() < 5;
    }

    public boolean canStart(User p){
        boolean correctPlayer = p.getStringUserName().equals(this.getName());
        boolean enoughPlayers = users.size() > 1;
        boolean tooManyPlayers = users.size() > 5;
        return correctPlayer && enoughPlayers && !tooManyPlayers;
    }
}
