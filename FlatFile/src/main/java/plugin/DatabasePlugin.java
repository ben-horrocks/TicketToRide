package plugin;

import java.util.List;

import common.communication.Command;
import common.game_data.GameID;
import common.game_data.ServerGameData;
import common.player_info.User;
import common.player_info.Username;
//import daos.*;
//import Factory.*;

public class DatabasePlugin implements IDatabasePlugin
{

    public DatabasePlugin()
    {
        System.out.println("I got here without parameters!");
    }

    public DatabasePlugin(int numCommands, boolean cleanData)
    {
        System.out.println("A Flat File Database was created");
    }

    @Override
    public IDatabasePlugin accessDatabase()
    {
        return null;
    }

    @Override
    public boolean initializeDatabase(boolean cleanSlate)
    {
        return false;
    }

    @Override
    public User getUser(Username name)
    {
        return null;
    }

    @Override
    public List<User> getAllUsers()
    {
        return null;
    }

    @Override
    public boolean addUser(User user)
    {
        return false;
    }

    @Override
    public boolean deleteUser(Username name)
    {
        return false;
    }

    @Override
    public boolean updateUser(User user)
    {
        return false;
    }

    @Override
    public ServerGameData getGame(GameID id)
    {
        return null;
    }

    @Override
    public List<ServerGameData> getRunningGames()
    {
        return null;
    }

    @Override
    public List<ServerGameData> getOpenGames()
    {
        return null;
    }

    @Override
    public List<ServerGameData> getAllGames()
    {
        return null;
    }

    @Override
    public List<ServerGameData> getRunningGames(Username user)
    {
        return null;
    }

    @Override
    public boolean addGame(ServerGameData game)
    {
        return false;
    }

    @Override
    public boolean deleteGame(GameID id)
    {
        return false;
    }

    @Override
    public boolean updateGame(ServerGameData game)
    {
        return false;
    }

    @Override
    public List<Command> getCommands(GameID id)
    {
        return null;
    }

    @Override
    public boolean addCommand(GameID id)
    {
        return false;
    }
}
