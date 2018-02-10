package CS340.TicketServer;

import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

import common.DataModels.Game;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.IClient;

public class ClientProxy implements IClient {

    /**
     * Static fields for the Server Facade class
     * these fields make access to the database thread safe
     */
    private static volatile ClientProxy SINGLETON;
    private static final Object mutex = new Object();

    /**
     * Default constructor for the serverFacade class
     * by constructing the server facade, which accesses the database, in this way
     * it is prohibited to ever create more than one instance of the server facade
     * it is also prohibited for any thread to advance beyond the synchronized keyword
     * without available access to the mutex object
     */
    private ClientProxy() {}

    public static ClientProxy getSINGLETON() {
        ClientProxy clientProxy = SINGLETON;
        if (clientProxy == null) {
            synchronized (mutex) {
                clientProxy = SINGLETON;
                if (clientProxy == null) {
                    SINGLETON = clientProxy = new ClientProxy();
                }
            }
        }
        return clientProxy;
    }

    @Override
    public void updateGameList(List<GameInfo> gameList) {
        Signal signal = new Signal(SignalType.OK, Database.SINGLETON.getAllGames());
    }

    @Override
    public void startGame(GameID id) {


    }

}
