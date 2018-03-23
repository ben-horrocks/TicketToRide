package CS340.TicketServer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import common.chat.ChatItem;
import common.communication.CommandParams;
import common.game_data.GameInfo;
import common.game_data.StartGamePacket;
import common.communication.IClient;
import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.communication.Signal;
import common.communication.SignalType;
import common.history.HistoryItem;
import common.map.Edge;
import common.player_info.Username;
import communicators.ServerCommunicator;

public class ClientProxy implements IClient
{

    /**
     * Static fields for the Server Facade class
     * these fields make access to the database thread safe
     */
    private static volatile ClientProxy SINGLETON;
    private static final Object mutex = new Object();

    /**
     * The logger for the client proxy.
     */
    private static final Logger logger = LogKeeper.getSingleton().getLogger();

    /**
     * Default constructor for the serverFacade class
     * by constructing the server facade, which accesses the database, in this way
     * it is prohibited to ever create more than one instance of the server facade
     * it is also prohibited for any thread to advance beyond the synchronized keyword
     * without available access to the mutex object
     */
    private ClientProxy()
    {
    }

    static ClientProxy getSINGLETON()
    {
        logger.entering("ClientProxy", "getSINGLETON");
        ClientProxy clientProxy = SINGLETON;
        if (clientProxy == null)
        {
            synchronized (mutex)
            {
                clientProxy = SINGLETON;
                if (clientProxy == null)
                {
                    logger.finer("Creating new ClientProxy");
                    SINGLETON = clientProxy = new ClientProxy();
                }
            }
        }
        logger.exiting("ClientProxy", "getSINGLETON", clientProxy);
        return clientProxy;
    }

    /* The String literals for the class paths for classes. */
    private static final String startGamePacketClassName = StartGamePacket.class.getName();
    private static final String userNameClassName = Username.class.getName();
    private static final String intClassName = int.class.getName();
    private static final String trainCardClassName = TrainCard.class.getName();
    private static final String handDestinationCardsClassName =
            HandDestinationCards.class.getName();
    private static final String chatItemClassName = ChatItem.class.getName();
    private static final String historyItemClassName = HistoryItem.class.getName();
    private static final String edgeClassName = Edge.class.getName();

    @Override
    public Signal updateGameList(List<GameInfo> gameList)
    {
        logger.entering("ClientProxy", "updateGameList", gameList);
        ConcurrentHashMap<Username, ClientThread> threadList =
                (ConcurrentHashMap<Username, ClientThread>) ServerCommunicator.getThreads();
        Signal signal = new Signal(SignalType.UPDATE, gameList);
        for (ClientThread thread : threadList.values())
        {
            thread.push(signal);
        }
        signal = new Signal(SignalType.OK, "Accepted");
        logger.exiting("ClientProxy", "updateGameList", signal);
        return signal;
    }

    @Override
    public Signal startGame(StartGamePacket packet)
    {
        logger.entering("ClientProxy", "startGame", packet);
        //Get the recipient for the packet and find their thread
        Username packetRecipient = packet.getUser();
        String methodName = "startGame";
        String[] paramTypes = {startGamePacketClassName};
        Object[] params = {packet};
        Signal signal = sendCommandToClient(packetRecipient, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "startGame", signal);
        return signal;
    }

    @Override
    public Signal opponentDrewDestinationCards(Username name, int amount)
    {
        // int in logger may break things
        logger.entering("ClientProxy", "opponentDrewDestinationCards", new Object[]{name, amount});
        String methodName = "opponentDrewDestinationCards";
        String[] paramTypes = {userNameClassName, intClassName};
        Object[] params = {name, amount};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "opponentDrewDestinationCards", signal);
        return signal;
    }

    @Override
    public Signal opponentDrewFaceUpCard(Username name, int index, TrainCard replacement)
    {
        // int in logger may break things
        logger.entering("ClientProxy", "opponentDrewFaceUpCard",
                        new Object[]{name, index, replacement});
        String methodName = "opponentDrewFaceUpCard";
        String[] paramTypes = {userNameClassName, intClassName, trainCardClassName};
        Object[] params = {name, index, replacement};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "opponentDrewFaceUpCard", signal);
        return signal;
    }

    @Override
    public Signal opponentDrewDeckCard(Username name)
    {
        logger.entering("ClientProxy", "opponentDrewDeckCard", name);
        String methodName = "opponentDrewDeckCard";
        String[] paramTypes = {userNameClassName};
        Object[] params = {name};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "opponentDrewDeckCard", signal);
        return signal;
    }

    @Override
    public Signal playerDrewDestinationCards(Username name, HandDestinationCards cards)
    {
        logger.entering("ClientProxy", "playerDrewDestinationCards", new Object[]{name, cards});
        String methodName = "playerDrewDestinationCards";
        String[] paramTypes = {userNameClassName, handDestinationCardsClassName};
        Object[] params = {name, cards};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "playerDrewDestinationCards", signal);
        return signal;
    }

    @Override
    public Signal addChatItem(Username name, ChatItem item)
    {
        logger.entering("ClientProxy", "addChatItem", new Object[]{name, item});
        String methodName = "addChatItem";
        String[] paramTypes = {userNameClassName, chatItemClassName};
        Object[] params = {name, item};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "addChatItem", signal);
        return signal;
    }

    @Override
    public Signal addHistoryItem(Username name, HistoryItem item)
    {
        logger.entering("ClientProxy", "addHistoryItem", new Object[]{name, item});
        String methodName = "addHistoryItem";
        String[] paramTypes = {userNameClassName, historyItemClassName};
        Object[] params = {name, item};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "addHistoryItem", signal);
        return signal;
    }

    @Override
    public Signal playerClaimedEdge(Username name, Edge edge)
    {
        logger.entering("ClientProxy", "playerClaimedEdge", new Object[]{name, edge});
        String methodName = "playerClaimedEdge";
        String[] paramTypes = {userNameClassName, edgeClassName};
        Object[] params = {name, edge};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "playerClaimedEdge", signal);
        return signal;
    }

    @Override
    public Signal lastTurn(Username name)
    {
        logger.entering("ClientProxy", "lastTurn", new Object[]{name});
        String methodName = "lastTurn";
        String[] paramTypes = {userNameClassName};
        Object[] params = {name};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "lastTurn", signal);
        return signal;
    }

    @Override
    public Signal gameEnded(Username name)
    {
        logger.entering("ClientProxy", "gameEnded", new Object[]{name});
        String methodName = "gameEnded";
        String[] paramTypes = {userNameClassName};
        Object[] params = {name};
        Signal signal = sendCommandToClient(name, methodName, paramTypes, params);
        logger.exiting("ClientProxy", "gameEnded", signal);
        return signal;
    }

    private Signal sendCommandToClient(Username client, String methodName, String[] paramTypes,
                                       Object[] params)
    {
        logger.entering("ClientProxy", "sendCommandToClient",
                        new Object[]{client, methodName, paramTypes, params});
        logger.fine("Sending \"" + methodName + "\" command to: " + client.getName());
        CommandParams command = new CommandParams(methodName, paramTypes, params);
        try
        {
            ConcurrentHashMap<Username, ClientThread> threadList =
                    (ConcurrentHashMap<Username, ClientThread>) ServerCommunicator.getThreads();
            threadList.get(client).push(command);
            Signal signal = new Signal(SignalType.OK, "Dummy Signal");
            logger.exiting("ClientProxy", "sendCommandToClient", signal);
            return signal;
        } catch (NullPointerException e)
        {
            logger.warning("Client: " + client.getName() + " is not connected!");
        } catch (Exception e)
        {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
        logger.warning("An error occurred when sending a \"" + methodName + "\" command to: " +
                       client.getName());
        Signal signal = new Signal(SignalType.ERROR,
                                   "An error occurred when sending a \"" + methodName +
                                   "\" command to: " + client.getName());
        logger.exiting("ClientProxy", "sendCommandToClient", signal);
        return signal;
    }
}
