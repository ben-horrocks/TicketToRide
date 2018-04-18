package Factory;

import daos.*;
import daos.CommandDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class Factory implements IFactory {

    private boolean clearData = false;

    @Override
    public IUserDAO createUserDAO() {
        UserDAO dao = new UserDAO();

        //Check if needs to clear database
        if (clearData) {
            dao.clearData();
        }

        return dao;
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        PlayerDAO dao = new PlayerDAO();

        //check if needs to clear data
        if (clearData) {
            dao.clearData();
        }

        return dao;
    }

    @Override
    public ICommandDAO createCommandDAO() {
        CommandDAO dao = new CommandDAO();

        //check if needs to clear data
        if (clearData) {
            dao.clearData();
        }

        return dao;
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        GameDataDAO dao = new GameDataDAO();

        //check if needs to clear data
        if (clearData) {
            dao.clearData();
        }

        return dao;
    }

    @Override
    public void setClearData(boolean clearData)
    {
        this.clearData = clearData;
    }
}
