package cs340.TicketClient.communicator;

import android.util.Log;

import java.io.IOException;

import common.chat.ChatItem;
import common.communication.CommandParams;
import common.communication.IServer;
import common.cards.HandDestinationCards;
import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameID;
import common.map.Edge;
import common.player_info.User;
import common.player_info.Username;

public class ServerProxy implements IServer
{
    private static ServerProxy singleton;

    public static ServerProxy getInstance()
    {
        if (singleton == null)
        {
            singleton = new ServerProxy();
        }
        return singleton;
    }

    private static final String stringClassName = String.class.getName();
    private static final String gameIDClassname = GameID.class.getName();
    private static final String playerClassName = User.class.getName();
    private static final String usernameClassName = Username.class.getName();
    private static final String handDestinationCardsClassName =
            HandDestinationCards.class.getName();
    private static final String chatItemClassName = ChatItem.class.getName();
    private static final String edgeClassName = Edge.class.getName();

    /**
     * @param username username of player trying to login
     * @param password password of player trying to login
     * @return success or fail login Signal
     */
    @Override
    public Signal login(String username, String password)
    {

        try
        {
            String[] parameterTypes = {stringClassName, stringClassName};
            Object[] parameters = {username, password};
            CommandParams loginCommand = new CommandParams("login", parameterTypes, parameters);
            Signal returnSignal = (Signal) ClientCommunicator.getSingleton().send(loginCommand);
            return returnSignal;
        } catch (IOException e)
        {
            Log.d("IO Execption", e.getMessage());
            return null;
        }
    }

    /**
     * @param username   username of player trying to register (must be unique)
     * @param password   password of player trying to register
     * @return success or fail register Signal
     */
    @Override
    public Signal register(String username, String password)
    {
        try
        {
            String[] parameterTypes = {stringClassName, stringClassName};
            Object[] parameters = {username, password};
            CommandParams registerCommand =
                    new CommandParams("register", parameterTypes, parameters);
            Signal returnSignal = (Signal) ClientCommunicator.getSingleton().send(registerCommand);
            return returnSignal;
        } catch (IOException e)
        {
            Log.d("IO Exception", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Signal startGame(GameID id)
    {
        String[] parameterTypes = {gameIDClassname};
        Object[] parameters = {id};
        CommandParams startGameCommand = new CommandParams("startGame", parameterTypes, parameters);
        try
        {
            return (Signal) ClientCommunicator.getSingleton().send(startGameCommand);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal getAvailableGameInfo()
    {
        String[] parameterTypes = {};
        Object[] parameters = {};
        CommandParams getAvailableGameInfoCommand =
                new CommandParams("getAvailableGameInfo", parameterTypes, parameters);
        try
        {
            return (Signal) ClientCommunicator.getSingleton().send(getAvailableGameInfoCommand);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal addGame(String gameName, User user)
    {
        String[] paramTypes = {stringClassName, playerClassName};
        Object[] params = {gameName, user};
        CommandParams newcommand = new CommandParams("addGame", paramTypes, params);
        try
        {
            return (Signal) ClientCommunicator.getSingleton().send(newcommand);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal joinGame(User user, GameID id)
    {
        String[] paramTypes = {playerClassName, gameIDClassname};
        Object[] params = {user, id};
        CommandParams newcommand = new CommandParams("joinGame", paramTypes, params);
        try
        {
            return (Signal) ClientCommunicator.getSingleton().send(newcommand);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Signal populate()
    {
        //empty
        return null;
    }

    @Override
    public Signal returnDestinationCards(GameID id, Username name, HandDestinationCards pickedCards,
                                         HandDestinationCards returnCards)
    {
        String[] paramTypes = {gameIDClassname, usernameClassName, handDestinationCardsClassName,
                               handDestinationCardsClassName};
        Object[] params = {id, name, pickedCards, returnCards};
        CommandParams commandParams =
                new CommandParams("returnDestinationCards", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal sendChat(GameID id, ChatItem item)
    {
        String[] paramTypes = {gameIDClassname, chatItemClassName};
        Object[] params = {id, item};
        CommandParams commandParams = new CommandParams("sendChat", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            e.printStackTrace();
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal drawDeck(GameID id, Username user)
    {
        String[] paramTypes = {gameIDClassname, usernameClassName};
        Object[] params = {id, user};
        CommandParams commandParams = new CommandParams("drawDeck", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal drawDestinationCards(GameID id, Username user)
    {
        String[] paramTypes = {gameIDClassname, usernameClassName};
        Object[] params = {id, user};
        CommandParams commandParams = new CommandParams("drawDestinationCards", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal drawFaceUp(GameID id, Username user, int cardIndex)
    {
        String[] paramTypes = {gameIDClassname, usernameClassName, "int"};
        Object[] params = {id, user, cardIndex};
        CommandParams commandParams = new CommandParams("drawFaceUp", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal claimEdge(GameID id, Username user, Edge edge)
    {
        String[] paramTypes = {gameIDClassname, usernameClassName, edgeClassName};
        Object[] params = {id, user, edge};
        CommandParams commandParams = new CommandParams("drawFaceUp", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal lastTurn(GameID id) {
        String[] paramTypes = {gameIDClassname};
        Object[] params = {id};
        CommandParams commandParams = new CommandParams("lastTurn", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal turnEnded(GameID id, Username name)
    {
        String[] paramTypes = {gameIDClassname, usernameClassName};
        Object[] params = {id, name};
        CommandParams commandParams = new CommandParams("turnEnded", paramTypes, params);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal returnToLobby(Username user)
    {
        String[] parameterTypes = {};
        Object[] parameters = {};
        CommandParams commandParams = new CommandParams("returnToLobby", parameterTypes, parameters);
        try
        {
            Signal s = (Signal) ClientCommunicator.getSingleton().send(commandParams);
            return s;
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

}
    
