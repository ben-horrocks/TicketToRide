package plugin;

import java.util.ArrayList;
import java.util.List;

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

    public SQLDatabasePlugin(int numCommands, boolean cleanData)
    {
        this.numCommands = numCommands;
        this.cleanData = cleanData;
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
    	if (cleanSlate)
		{
			boolean cleared;
			IDAO dao = new SQLCommandDAO();
			cleared = dao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(dao);
			if (!cleared) return false;
			dao = new SQLGameDataDAO();
			cleared = dao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(dao);
			if (!cleared) return false;
			dao = new SQLUserDAO();
			cleared = dao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(dao);
			if (!cleared) return false;
			dao = new SQLPlayerDAO();
			cleared = dao.clearData();
			if (!cleared) return false;
			cleared = commitAndClose(dao);
			return cleared;
		}
		IDAO dao = new SQLCommandDAO();
    	commitAndClose(dao);
    	dao = new SQLGameDataDAO();
    	commitAndClose(dao);
    	dao = new SQLUserDAO();
    	commitAndClose(dao);
    	dao = new SQLPlayerDAO();
    	commitAndClose(dao);
    	return true;
    }

    @Override
    public User getUser(Username name) {
    	SQLUserDAO dao = new SQLUserDAO();
    	User user = dao.getUser(name);
    	commitAndClose(dao);
    	return user;
    }

    @Override
    public List<User> getAllUsers() {
    	SQLUserDAO dao = new SQLUserDAO();
        List<User> users = dao.getAllUsers();
        commitAndClose(dao);
        return users;
    }

    @Override
    public boolean addUser(User user) {
    	SQLUserDAO dao = new SQLUserDAO();
    	boolean successful = dao.addNewUser(user);
		commitAndClose(dao);
    	return successful;
    }

    @Override
    public boolean deleteUser(Username name) {
        SQLUserDAO dao = new SQLUserDAO();
        boolean successful = dao.deleteUser(name);
		commitAndClose(dao);
        return successful;
    }

    @Override
    public boolean updateUser(User user) {
        SQLUserDAO dao = new SQLUserDAO();
        boolean successful = dao.updateUser(user);
		commitAndClose(dao);
        return successful;
    }

    @Override
    public ServerGameData getGame(GameID id) {
        SQLGameDataDAO dao = new SQLGameDataDAO();
        ServerGameData data = dao.getGameData(id);
        commitAndClose(dao);
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
		commitAndClose(dao);
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
		commitAndClose(dao);
		return openGames;
    }

    @Override
    public List<ServerGameData> getAllGames() {
        SQLGameDataDAO dao = new SQLGameDataDAO();
        List<ServerGameData> games = dao.getAllGameData();
        commitAndClose(dao);
        return games;
    }

    @Override
    public List<ServerGameData> getRunningGames(Username user) {
    	SQLGameDataDAO dao = new SQLGameDataDAO();
		List<ServerGameData> gameData = dao.getAllGameData();
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
    	SQLGameDataDAO dao = new SQLGameDataDAO();
    	boolean successful = dao.addNewGameData(game);
    	commitAndClose(dao);
        return successful;
    }

    @Override
    public boolean deleteGame(GameID id) {
        SQLGameDataDAO dao = new SQLGameDataDAO();
        boolean successful = dao.deleteGameData(id);
        commitAndClose(dao);
        return successful;
    }

    @Override
    public boolean updateGame(ServerGameData game) {
        SQLGameDataDAO dao = new SQLGameDataDAO();
        boolean successful = dao.updateGameData(game);
        commitAndClose(dao);
        return successful;
    }

    @Override
    public List<Command> getCommands(GameID id) {
        SQLCommandDAO dao = new SQLCommandDAO();
        List<Command> commands = dao.getCommandsByGameId(id);
        commitAndClose(dao);
        return commands;
    }

    @Override
    public boolean addCommand(Command command) {
        SQLCommandDAO dao = new SQLCommandDAO();
        boolean successful = dao.addNewCommand(command);
        commitAndClose(dao);
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
