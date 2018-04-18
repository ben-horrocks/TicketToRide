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
import daos.CommandDAO;
import daos.GameDataDAO;
import daos.PlayerDAO;
import daos.UserDAO;

/**
 * Created by Carter on 4/16/18.
 */

public class DatabasePlugin implements IDatabasePlugin {

    private static final Object mutex = new Object();

    private CommandDAO mCommandDAO;
    private GameDataDAO mGameDataDAO;
    private UserDAO mUserDAO;
    private PlayerDAO mPlayerDAO;

    public DatabasePlugin(int numCommands, boolean cleanData)
    {
        System.out.println("An SQL File Database was created");
    }

    private DatabasePlugin() {
        Factory factory = new Factory();
        mCommandDAO = (CommandDAO) factory.createCommandDAO();
        mGameDataDAO = (GameDataDAO) factory.createGameDataDAO();
        mUserDAO = (UserDAO) factory.createUserDAO();
        mPlayerDAO = (PlayerDAO) factory.createPlayerDAO();

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
			cleared = mCommandDAO.clearData();
			if (!cleared) return false;
			cleared = mGameDataDAO.clearData();
			if (!cleared) return false;
			cleared = mUserDAO.clearData();
			if (!cleared) return false;
			cleared = mPlayerDAO.clearData();
			return cleared;
		}
    	return true;
    }

    @Override
    public User getUser(Username name) {
    	return mUserDAO.getUser(name);
    }

    @Override
    public List<User> getAllUsers() {
        return mUserDAO.getAllUsers();
    }

    @Override
    public boolean addUser(User user) {
        return mUserDAO.addNewUser(user);
    }

    @Override
    public boolean deleteUser(Username name) {
        return mUserDAO.deleteUser(name);
    }

    @Override
    public boolean updateUser(User user) {
        return mUserDAO.updateUser(user);
    }

    @Override
    public ServerGameData getGame(GameID id) {
        return mGameDataDAO.getGameData(id);
    }

    @Override
    public List<ServerGameData> getRunningGames() {
        List<ServerGameData> gameData = mGameDataDAO.getAllGameData();
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
		List<ServerGameData> gameData = mGameDataDAO.getAllGameData();
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
        return mGameDataDAO.getAllGameData();
    }

    @Override
    public List<ServerGameData> getRunningGames(Username user) {
		List<ServerGameData> gameData = mGameDataDAO.getAllGameData();
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
        return mGameDataDAO.addNewGameData(game);
    }

    @Override
    public boolean deleteGame(GameID id) {
        return mGameDataDAO.deleteGameData(id);
    }

    @Override
    public boolean updateGame(ServerGameData game) {
        return mGameDataDAO.updateGameData(game);
    }

    @Override
    public List<Command> getCommands(GameID id) {
        return mCommandDAO.getCommandsByGameId(id);
    }

    @Override
    public boolean addCommand(Command command) {
        return mCommandDAO.addNewCommand(command);
    }
}
