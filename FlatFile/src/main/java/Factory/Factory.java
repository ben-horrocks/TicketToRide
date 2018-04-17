package Factory;

import java.io.IOException;

import daos.*;
import daos.UserDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class Factory implements IFactory {


    boolean clearData = false;

    @Override
    public IUserDAO createUserDAO() {
        UserDAO dao = null;
        try {
            dao = new UserDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
//            logger.warning(e + " - creating user DAO in Factory ");
//            logger.exiting("Factory", "createUserDAO", false);
        }
        return dao;
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        PlayerDAO dao = null;
        try {
            dao = new PlayerDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public ICommandDAO createCommandDAO() {
        CommandDAO dao = null;
        try {
            dao = new CommandDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        GameDataDAO dao = null;
        try {
            dao = new GameDataDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public void setClearData(boolean clearData)
    {
        this.clearData = clearData;
    }
}
