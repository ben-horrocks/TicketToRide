package CS340.TicketServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.DataFormatException;

import common.DataModels.Game;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Player;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.IClient;
import communicators.ServerCommunicator;

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
		ConcurrentHashMap<Player, CommandThread> threadList = (ConcurrentHashMap<Player, CommandThread>) ServerCommunicator.getThreads();
        Signal signal = new Signal(SignalType.UPDATE, gameList);
        for (CommandThread thread : threadList.values()) {
            thread.push(signal);
        }
    }

    @Override
    public void startGame(GameID id) {
        //get threads for all players & the game to be started with associated players
        ConcurrentHashMap<Player, CommandThread> threadList = (ConcurrentHashMap<Player, CommandThread>) ServerCommunicator.getThreads();
        Game game = Database.SINGLETON.getOpenGameByID(id);
        
        //create start signal to push to all players in game
        Signal signal = new Signal(SignalType.OK, game);

        for (Player p : game.getPlayers()) {
            threadList.get(p).push(signal);
        }

    }

}
