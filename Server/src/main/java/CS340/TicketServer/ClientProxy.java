package CS340.TicketServer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import common.CommandParams;
import common.DataModels.GameData.ServerGameData;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.User;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.Username;
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
    public Signal updateGameList(List<GameInfo> gameList) {
		ConcurrentHashMap<Username, ClientThread> threadList = (ConcurrentHashMap<Username, ClientThread>) ServerCommunicator.getThreads();
        Signal signal = new Signal(SignalType.UPDATE, gameList);
        for (ClientThread thread : threadList.values()) {
            thread.push(signal);
        }
        return new Signal(SignalType.OK, "Accepted");
    }

    @Override
    public Signal startGame(GameID id/*should be ServerGameData*/) {
        //TODO with list of players in game && ServerGameData, create packet for each player
        //get threads for all players & the serverGameData to be started with associated players
        ConcurrentHashMap<Username, ClientThread> threadList = (ConcurrentHashMap<Username, ClientThread>) ServerCommunicator.getThreads();
        ServerGameData serverGameData = Database.SINGLETON.getRunningGameByID(id);
        
        //create start signal to push to all players in serverGameData
        Signal signal = new Signal(SignalType.START_GAME, serverGameData);

        for (User p : serverGameData.getUsers()) {
            if(threadList.get(p.getUsername()) != null)
                threadList.get(p.getUsername()).push(signal);
        }
        return new Signal(SignalType.OK, "Accepted");
    }

    @Override
    public Signal playerDrewDestinationCards(Username name, int amount) {
        //TODO implement sending commands to client.
        return null;
    }
}
