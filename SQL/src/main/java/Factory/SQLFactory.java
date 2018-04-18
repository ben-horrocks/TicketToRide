package Factory;

import daos.*;
import daos.SQLCommandDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class SQLFactory implements IFactory {

    @Override
    public IUserDAO createUserDAO() {
        return new SQLUserDAO();
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        return new SQLPlayerDAO();
    }

    @Override
    public ICommandDAO createCommandDAO() {
       return new SQLCommandDAO();
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        return new SQLGameDataDAO();
    }

    @Override
    public void setClearData(boolean clearData)
    {
        // Do nothing
    }
}
