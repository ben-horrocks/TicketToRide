package common.game_data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import common.player_info.User;
import common.player_info.Username;

public class GameInfo implements Serializable
{
    private GameID id;
    private String name;
    private String creatorName;
    private Set<Username> users;
    private boolean started;

    public GameInfo(ServerGameData g)
    {
        this.id = g.getId();
        this.name = g.getName();
        this.creatorName = g.getCreatorName();
        this.users = new HashSet<>();
        for(User u: g.getUsers())
        {
            users.add(u.getUsername());
        }
        this.started = g.isGameStarted();
    }

    public GameInfo(GameID id, String name, String creatorName, Set<User> users)
    {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.users = new HashSet<>();
        for(User u: users)
        {
            this.users.add(u.getUsername());
        }
        this.started = false;
    }

    public GameID getID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getCreatorName()
    {
        return this.creatorName;
    }

    public Set<Username> getUsers()
    {
        return this.users;
    }

    public boolean hasUser(Username user)
    {
        return this.users.contains(user);
    }

    public int getPlayerCount()
    {
        return this.users.size();
    }

    public boolean isFull()
    {
        return users.size() < 5;
    }

    public boolean canStart(User p)
    {
        boolean correctPlayer = p.getStringUserName().equals(this.getName());
        boolean enoughPlayers = users.size() > 1;
        boolean tooManyPlayers = users.size() > 5;
        return correctPlayer && enoughPlayers && !tooManyPlayers;
    }

    public boolean isStarted() {
        return started;
    }
}
