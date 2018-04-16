package Factory;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
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

    private static final Logger logger = LogKeeper.getSingleton().getLogger();

    @Override
    public IUserDAO createUserDAO() {
        logger.entering("Flat_Factory", "createUserDAO");
        FlatUserDAO dao = null;
        try {
            dao = new FlatUserDAO();
        } catch (IOException e) {
            logger.warning(e + " - creating user DAO in Flat_Factory ");
            logger.exiting("Flat_Factory", "createUserDAO", false);
        }
        return dao;
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        logger.entering("Flat_Factory", "createPlayerDAO");
        FlatPlayerDAO dao = null;
        try {
            dao = new FlatPlayerDAO();
        } catch (IOException e) {
            logger.warning(e + " - creating player DAO in Flat_Factory ");
            logger.exiting("Flat_Factory", "createPlayerDAO", false);
        }
        return dao;
    }

    @Override
    public ICommandDAO createCommandDAO() {
        logger.entering("Flat_Factory", "createCommandDAO");
        FlatCommandDAO dao = null;
        try {
            dao = new FlatCommandDAO();
        } catch (IOException e) {
            logger.warning(e + " - creating command DAO in Flat_Factory ");
            logger.exiting("Flat_Factory", "createCommandDAO", false);
        }
        return dao;
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        logger.entering("Flat_Factory", "createGameDataDAO");
        FlatGameDataDAO dao = null;
        try {
            dao = new FlatGameDataDAO();
        } catch (IOException e) {
            logger.warning(e + " - creating game data DAO in Flat_Factory ");
            logger.exiting("Flat_Factory", "createGameDataDAO", false);
        }
        return dao;
    }
}
