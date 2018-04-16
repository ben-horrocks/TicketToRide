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


    @Override
    public IUserDAO createUserDAO() {
        FlatUserDAO dao = null;
        try {
            dao = new FlatUserDAO();
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
            dao = new FlatPlayerDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public ICommandDAO createCommandDAO() {
        FlatCommandDAO dao = null;
        try {
            dao = new FlatCommandDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        FlatGameDataDAO dao = null;
        try {
            dao = new FlatGameDataDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dao;
    }
}
