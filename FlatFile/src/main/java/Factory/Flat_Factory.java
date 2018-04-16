package Factory;

import java.io.IOException;
import daos.FlatCommandDAO;
import daos.FlatGameDataDAO;
import daos.FlatPlayerDAO;
import daos.FlatUserDAO;
import daos.ICommandDAO;
import daos.IGameDataDAO;
import daos.IPlayerDAO;
import daos.IUserDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class Flat_Factory implements IFactory {


    boolean clearData = false;

    @Override
    public IUserDAO createUserDAO() {
        FlatUserDAO dao = null;
        try {
            dao = new FlatUserDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
//            logger.warning(e + " - creating user DAO in Flat_Factory ");
//            logger.exiting("Flat_Factory", "createUserDAO", false);
        }
        return dao;
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        FlatPlayerDAO dao = null;
        try {
            dao = new FlatPlayerDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public ICommandDAO createCommandDAO() {
        FlatCommandDAO dao = null;
        try {
            dao = new FlatCommandDAO(clearData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        FlatGameDataDAO dao = null;
        try {
            dao = new FlatGameDataDAO(clearData);
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
