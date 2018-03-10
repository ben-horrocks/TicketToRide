package CS340.TicketServer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import common.CommandParams;
import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.GameInfo;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.TrainCard;
import common.DataModels.User;
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

    /* The String literals for the class paths for classes. */
    private static final String startGamePacketClassName = StartGamePacket.class.getName();
	private static final String userNameClassName = Username.class.getName();
	private static final String intClassName = int.class.getName();
	private static final String trainCardClassName = TrainCard.class.getName();
	private static final String handDestinationCardsClassName = HandDestinationCards.class.getName();
	private static final String chatItemClassName = ChatItem.class.getName();
	private static final String historyItemClassName = HistoryItem.class.getName();

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
        String methodName = "startGame";
        String[] paramTypes = {startGamePacketClassName};
        Object[] params = {packet};
        return sendCommandToClient(packetRecipient, methodName, paramTypes, params);
    }

    @Override
    public Signal opponentDrewDestinationCards(Username name, int amount) {
        String methodName = "opponentDrewDestinationCards";
        String[] paramTypes = {userNameClassName, intClassName};
        Object[] params = {name, amount};
        return sendCommandToClient(name, methodName, paramTypes, params);
    }

    @Override
    public Signal opponentDrewFaceUpCard(Username name, int index, TrainCard replacement) {
        String methodName = "opponentDrewFaceUpCard";
        String[] paramTypes = {userNameClassName, intClassName, trainCardClassName};
        Object[] params = {name, index, replacement};
        return sendCommandToClient(name, methodName, paramTypes, params);
    }

    @Override
    public Signal opponentDrewDeckCard(Username name) {
        String methodName = "opponentDrewDeckCard";
        String[] paramTypes = {userNameClassName};
        Object[] params = {name};
        return sendCommandToClient(name, methodName, paramTypes, params);
    }

    @Override
    public Signal playerDrewDestinationCards(Username name, HandDestinationCards cards) {
        String methodName = "playerDrewDestinationCards";
        String[] paramTypes = {userNameClassName, handDestinationCardsClassName};
        Object[] params = {name, cards};
        return sendCommandToClient(name, methodName, paramTypes, params);
    }

    @Override
    public Signal addChatItem(Username name, ChatItem item) {
        String methodName = "addChatItem";
        String[] paramTypes = {userNameClassName, chatItemClassName};
        Object[] params = {name, item};
        return sendCommandToClient(name, methodName, paramTypes, params);
    }

    @Override
    public Signal addHistoryItem(Username name, HistoryItem item) {
        String methodName = "addHistoryItem";
        String[] paramTypes = {historyItemClassName};
        Object[] params = {item};
        return sendCommandToClient(name, methodName, paramTypes, params);
    }

    private Signal sendCommandToClient(Username client, String methodName, String[] paramTypes, Object[] params){
        System.out.println("Sending \"" + methodName + "\" command to: " + client.getName());
        CommandParams command = new CommandParams(methodName, paramTypes, params);
        try {
            ConcurrentHashMap<Username, ClientThread> threadList = (ConcurrentHashMap<Username, ClientThread>) ServerCommunicator.getThreads();
            threadList.get(client).push(command);
            Signal signal = new Signal(SignalType.OK, "Dummy Signal");
            return signal;
        } catch (Exception e) {
            e.printStackTrace();
            return new Signal(SignalType.ERROR, "An error occurred when sending a \""
                    + methodName + "\" command to: " + client.getName());
        }
    }
}
