package plugin;

import java.util.List;

import Factory.Factory;
import common.communication.Command;
import common.game_data.GameID;
import common.game_data.ServerGameData;
import common.player_info.User;
import common.player_info.Username;
import daos.CommandDAO;
import daos.GameDataDAO;
import daos.PlayerDAO;
import daos.UserDAO;

/**
 * Created by Carter on 4/16/18.
 */

public class SQLDatabasePlugin implements IDatabasePlugin {

    private static final Object mutex = new Object();

    private CommandDAO mCommandDAO;
    private GameDataDAO mGameDataDAO;
    private UserDAO mUserDAO;
    private PlayerDAO mPlayerDAO;

    private SQLDatabasePlugin() {
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
        return false;
    }

    @Override
    public User getUser(Username name) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(Username name) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public ServerGameData getGame(GameID id) {
        return null;
    }

    @Override
    public List<ServerGameData> getRunningGames() {
        return null;
    }

    @Override
    public List<ServerGameData> getOpenGames() {
        return null;
    }

    @Override
    public List<ServerGameData> getAllGames() {
        return null;
    }

    @Override
    public List<ServerGameData> getRunningGames(Username user) {
        return null;
    }

    @Override
    public boolean addGame(ServerGameData game) {
        return false;
    }

    @Override
    public boolean deleteGame(GameID id) {
        return false;
    }

    @Override
    public boolean updateGame(ServerGameData game) {
        return false;
    }

    @Override
    public List<Command> getCommands(GameID id) {
        return null;
    }

    @Override
    public boolean addCommand(GameID id) {
        return false;
    }
}
