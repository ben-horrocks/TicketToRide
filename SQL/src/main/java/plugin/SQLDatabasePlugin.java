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
    SQLUserDAO uDao;

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

            cleared = uDao.clearData();
            if (!cleared) return false;
            cleared = commitAndClose(uDao);
            if (!cleared) return false;

			return cleared;
		}

    	commitAndClose(cDao);
    	commitAndClose(gdDao);
    	commitAndClose(pDao);
        commitAndClose(uDao);

        return true;
    }

    @Override
    public User getUser(Username name) {
    	User user = uDao.getUser(name);
    	commitAndClose(uDao);
    	return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = uDao.getAllUsers();
        commitAndClose(uDao);
        return users;
    }

    @Override
    public boolean addUser(User user) {
    	boolean successful = uDao.addNewUser(user);
		commitAndClose(uDao);
    	return successful;
    }

    @Override
    public boolean deleteUser(Username name) {
        boolean successful = uDao.deleteUser(name);
		commitAndClose(uDao);
        return successful;
    }

    @Override
    public boolean updateUser(User user) {
        boolean successful = uDao.updateUser(user);
		commitAndClose(uDao);
        return successful;
    }

    @Override
    public ServerGameData getGame(GameID id) {
        ServerGameData data = gdDao.getGameData(id);
        commitAndClose(gdDao);
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
		commitAndClose(gdDao);
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
		commitAndClose(gdDao);
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
		commitAndClose(dao);
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
