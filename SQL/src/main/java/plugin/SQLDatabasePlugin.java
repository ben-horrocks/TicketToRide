package plugin;

import java.util.ArrayList;
import java.util.List;

import Factory.Factory;
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

    private int numCommands;
    private boolean cleanData;

    private Factory factory;
    SQLCommandDAO cDao;
    SQLGameDataDAO gdDao;
    SQLPlayerDAO pDao;

    public SQLDatabasePlugin(int numCommands, boolean cleanData)
    {
        this.numCommands = numCommands;
        this.cleanData = cleanData;

        factory = new Factory();

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

        cDao = (SQLCommandDAO) factory.createCommandDAO();
        gdDao = (SQLGameDataDAO) factory.createGameDataDAO();
        pDao = (SQLPlayerDAO) factory.createPlayerDAO();

        if (cleanSlate)
		{
			boolean cleared;

			cleared = cDao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(cDao);
			if (!cleared) return false;

			cleared = gdDao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(gdDao);
			if (!cleared) return false;

			cleared = pDao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(pDao);
			if (!cleared) return false;

			return cleared;
		}

    	commitAndClose(cDao);
    	commitAndClose(gdDao);
    	commitAndClose(pDao);

    	return true;
    }

    @Override
    public User getUser(Username name) {
    	SQLUserDAO dao = new SQLUserDAO();
    	User user = dao.getUser(name);

    	return user;
    }

    @Override
    public List<User> getAllUsers() {
    	SQLUserDAO dao = new SQLUserDAO();
        List<User> users = dao.getAllUsers();
        return users;
    }

    @Override
    public boolean addUser(User user) {
    	SQLUserDAO dao = new SQLUserDAO();
    	boolean successful = dao.addNewUser(user);

    	return successful;
    }

    @Override
    public boolean deleteUser(Username name) {
        SQLUserDAO dao = new SQLUserDAO();
        boolean successful = dao.deleteUser(name);

        return successful;
    }

    @Override
    public boolean updateUser(User user) {
        SQLUserDAO dao = new SQLUserDAO();
        boolean successful = dao.updateUser(user);

        return successful;
    }

    @Override
    public ServerGameData getGame(GameID id) {
        SQLGameDataDAO dao = new SQLGameDataDAO();
        ServerGameData data = dao.getGameData(id);
        return data;
    }

    @Override
    public List<ServerGameData> getRunningGames() {
    	SQLGameDataDAO dao = new SQLGameDataDAO();
        List<ServerGameData> gameData = dao.getAllGameData();
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
    	SQLGameDataDAO dao = new SQLGameDataDAO();
		List<ServerGameData> gameData = dao.getAllGameData();
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
        if (cDao.getCommandsByGameId(id).size() == numCommands) {

        }

        return successful;
    }

    private boolean commitAndClose(IDAO dao)
	{
		boolean successful;

		/*	-- Debugging statement to check what methods an IDAO has --
		Object[] methods = IDAO.class.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			System.out.println("Method " + i + ": " + methods[i]);
		}
		*/
		successful = dao.commitConnection();
		if (!successful) return false;
		successful = dao.closeConnection();
		return successful;
	}
}
