package CS340.TicketServer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import common.CommandParams;
import common.DataModels.GameInfo;
import common.DataModels.GameData.StartGamePacket;
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
    public Signal startGame(StartGamePacket packet) {
        //Get the recipient for the packet and find their thread
        Username packetRecipient = packet.getUser();
        String[] paramTypes = {"common.DataModels.GameData.StartGamePacket"};
        Object[] params = {packet};
        CommandParams newcommand = new CommandParams("startGame", paramTypes, params);
        try {
            ConcurrentHashMap<Username, ClientThread> threadList = (ConcurrentHashMap<Username, ClientThread>) ServerCommunicator.getThreads();
            threadList.get(packetRecipient).push(newcommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Signal(SignalType.OK, "Accepted");
    }

    @Override
    public Signal playerDrewDestinationCards(Username name, int amount) {
        //TODO implement sending commands to client.
        return null;
    }
}
