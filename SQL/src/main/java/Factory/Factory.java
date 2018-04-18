package Factory;

import daos.*;
import daos.CommandDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class Factory implements IFactory {

    @Override
    public IUserDAO createUserDAO() {
        return new UserDAO();
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        return new PlayerDAO();
    }

    @Override
    public ICommandDAO createCommandDAO() {
       return new CommandDAO();
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        return new GameDataDAO();
    }

    @Override
    public void setClearData(boolean clearData)
    {
        // Do nothing
    }
}
