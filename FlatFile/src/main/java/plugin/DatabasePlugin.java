package plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.communication.Command;
import common.game_data.GameID;
import common.game_data.ServerGameData;
import common.player_info.User;
import common.player_info.Username;
import daos.*;
//import daos.*;
//import Factory.*;

public class DatabasePlugin implements IDatabasePlugin
{
    private int deltaCommands;
    private ICommandDAO commandDAO;
    private IGameDataDAO gameDataDAO;
    private IUserDAO userDAO;

    private static final Object mutex = new Object();

    public DatabasePlugin(int numCommands, boolean cleanData)
    {
        deltaCommands = numCommands;
        initializeDatabase(cleanData);
        System.out.println("A Flat File Database was created");
    }

    @Override
    public IDatabasePlugin accessDatabase()
    {
        synchronized (mutex)
        {
            return this;
        }
    }

    @Override
    public boolean initializeDatabase(boolean cleanSlate)
    {
        try
        {
            commandDAO = new CommandDAO(cleanSlate);
            gameDataDAO = new GameDataDAO(cleanSlate);
            userDAO = new UserDAO(cleanSlate);
        } catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User getUser(Username name)
    {
        return userDAO.getUser(name);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean addUser(User user)
    {
        return userDAO.addNewUser(user);
    }

    @Override
    public boolean deleteUser(Username name)
    {
        return userDAO.deleteUser(name);
    }

    @Override
    public boolean updateUser(User user)
    {
        return userDAO.updateUser(user);
    }

    @Override
    public ServerGameData getGame(GameID id)
    {
        return gameDataDAO.getGameData(id);
    }

    @Override
    public List<ServerGameData> getRunningGames()
    {
        List<ServerGameData> games = gameDataDAO.getAllGameData();
        List<ServerGameData> returnGames = new ArrayList<>();
        for(ServerGameData game : games)
        {
            if(game.isGameStarted())
            {
                returnGames.add(game);
            }
        }
        return returnGames;
    }

    @Override
    public List<ServerGameData> getOpenGames()
    {
        List<ServerGameData> games = gameDataDAO.getAllGameData();
        List<ServerGameData> returnGames = new ArrayList<>();
        for(ServerGameData game : games)
        {
            if(!game.isGameStarted())
            {
                returnGames.add(game);
            }
        }
        return returnGames;
    }

    @Override
    public List<ServerGameData> getAllGames()
    {
        return gameDataDAO.getAllGameData();
    }

    @Override
    public List<ServerGameData> getRunningGames(Username user)
    {
        List<ServerGameData> games = gameDataDAO.getAllGameData();
        List<ServerGameData> returnGames = new ArrayList<>();
        for(ServerGameData game : games)
        {
            if(game.isGameStarted() && game.getPlayer(user.getName()) != null)
            {
                returnGames.add(game);
            }
        }
        return returnGames;
    }

    @Override
    public boolean addGame(ServerGameData game)
    {
        return gameDataDAO.addNewGameData(game);
    }

    @Override
    public boolean deleteGame(GameID id)
    {
        return gameDataDAO.deleteGameData(id);
    }

    @Override
    public boolean updateGame(ServerGameData game)
    {
        return gameDataDAO.updateGameData(game);
    }

    @Override
    public List<Command> getCommands(GameID id)
    {
        return commandDAO.getCommandsByGameId(id);
    }

    @Override
    public boolean addCommand(Command command)
    {
        return commandDAO.addNewCommand(command);
    }
}
