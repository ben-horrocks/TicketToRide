package Factory;

import daos.ICommandDAO;
import daos.IGameDataDAO;
import daos.IPlayerDAO;
import daos.IUserDAO;

/**
 * Created by Carter on 4/14/18.
 */

public interface IFactory {
    IUserDAO createUserDAO();
    IPlayerDAO createPlayerDAO();
    ICommandDAO createCommandDAO();
    IGameDataDAO createGameDataDAO();
    void setClearData(boolean clearData);
}
