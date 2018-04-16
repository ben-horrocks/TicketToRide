package Factory;

import daos.ICommandDAO;
import daos.IGameDataDAO;
import daos.IPlayerDAO;
import daos.IUserDAO;

/**
 * Created by Carter on 4/14/18.
 */

public interface IFactory {
    public IUserDAO createUserDAO();
    public IPlayerDAO createPlayerDAO();
    public ICommandDAO createCommandDAO();
    public IGameDataDAO createGameDataDAO();
    public void setClearData(boolean clearData);
}
