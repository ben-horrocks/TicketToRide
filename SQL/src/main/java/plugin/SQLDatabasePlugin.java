package plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Factory.SQLFactory;
import common.communication.Command;
import common.game_data.GameID;
import common.game_data.ServerGameData;
import common.player_info.Player;
import common.player_info.User;
import common.player_info.Username;
import daos.*;
import daos.SQLCommandDAO;

/**
 * Created by Carter on 4/16/18.
 */

public class SQLDatabasePlugin implements IDatabasePlugin {

    private static final Object mutex = new Object();

    private int deltaCommands;
    private boolean cleanData;

    private SQLFactory SQLFactory;
    SQLCommandDAO cDao;
    SQLGameDataDAO gdDao;
    SQLPlayerDAO pDao;
    SQLUserDAO uDao;

    public SQLDatabasePlugin(int deltaCommands, boolean cleanData)
    {
        this.deltaCommands = deltaCommands;
        this.cleanData = cleanData;

        SQLFactory = new SQLFactory();

        initializeDatabase(cleanData);
		System.out.println("An SQL File Database was created");
    }

    @Override
    public IDatabasePlugin accessDatabase() {
        synchronized (mutex) {
            return this;
        }
    }

    @Override
    public boolean initializeDatabase(boolean cleanSlate) {

        cDao = (SQLCommandDAO) SQLFactory.createCommandDAO();
        gdDao = (SQLGameDataDAO) SQLFactory.createGameDataDAO();
        pDao = (SQLPlayerDAO) SQLFactory.createPlayerDAO();

        if (cleanSlate)
		{
			boolean cleared;

			cleared = cDao.clearData();
			if (!cleared) return false;

			cleared = gdDao.clearData();
			if (!cleared) return false;

			cleared = pDao.clearData();
			if (!cleared) return false;

            cleared = uDao.clearData();
            if (!cleared) return false;

			return cleared;
		}

        return true;
    }

    @Override
    public User getUser(Username name) {
    	User user = uDao.getUser(name);
    	return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = uDao.getAllUsers();
        return users;
    }

    @Override
    public boolean addUser(User user) {
    	boolean successful = uDao.addNewUser(user);
    	return successful;
    }

    @Override
    public boolean deleteUser(Username name) {
        boolean successful = uDao.deleteUser(name);
        return successful;
    }

    @Override
    public boolean updateUser(User user) {
        boolean successful = uDao.updateUser(user);
        return successful;
    }

    @Override
    public ServerGameData getGame(GameID id) {
        ServerGameData data = gdDao.getGameData(id);
        return data;
    }

    @Override
    public List<ServerGameData> getRunningGames() {
        List<ServerGameData> gameData = gdDao.getAllGameData();
        List<ServerGameData> runningGames = new ArrayList<>();
        for (ServerGameData game : gameData)
		{
			if (game.isGameStarted())
			{
				runningGames.add(game);
			}
		}
		return runningGames;
    }

    @Override
    public List<ServerGameData> getOpenGames() {
		List<ServerGameData> gameData = gdDao.getAllGameData();
		List<ServerGameData> openGames = new ArrayList<>();
		for (ServerGameData game : gameData)
		{
			if (!game.isGameStarted())
			{
				openGames.add(game);
			}
		}
		return openGames;
    }

    @Override
    public List<ServerGameData> getAllGames() {
        List<ServerGameData> games = gdDao.getAllGameData();
        return games;
    }

    @Override
    public List<ServerGameData> getRunningGames(Username user) {
		List<ServerGameData> gameData = gdDao.getAllGameData();
		List<ServerGameData> runningGames = new ArrayList<>();
		for (ServerGameData game : gameData)
		{
			if (game.isGameStarted())
			{
				List<Player> players = game.getPlayers();
				for (Player player : players)
				{
					if (player.getUsername().equals(user))
					{
						runningGames.add(game);
					}
				}
			}
		}
		return runningGames;
    }

    @Override
    public boolean addGame(ServerGameData game) {
    	boolean successful = gdDao.addNewGameData(game);
        return successful;
    }

    @Override
    public boolean deleteGame(GameID id) {
        boolean successful = gdDao.deleteGameData(id);
        return successful;
    }

    @Override
    public boolean updateGame(ServerGameData game) {
        boolean successful = gdDao.updateGameData(game);
        return successful;
    }

    @Override
    public List<Command> getCommands(GameID id) {

        List<Command> commands = cDao.getCommandsByGameId(id);
        return commands;
    }

    @Override
    public boolean addCommand(Command command) {


        boolean successful = cDao.addNewCommand(command);
        GameID id = cDao.getGameIdFromCommand(command);
        if (id == null) {
            return false;
        }
        if (cDao.getCommandsByGameId(id).size() == deltaCommands) {
            gdDao.updateGameData(gdDao.getGameData(id));
            cDao.deleteCommandsByGameId(id);
        }

        return successful;
    }

}
