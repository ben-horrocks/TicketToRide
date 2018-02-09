package cs340.TicketClient.Communicator;

import android.util.Log;

import java.io.IOException;

import common.CommandParams;
import common.DataModels.Signal;

import cs340.TicketClient.ASyncTask.JoinGameTask;
import common.*;
import common.DataModels.*;

public class ServerProxy implements IServer
{
    private static ServerProxy singleton;

    public static ServerProxy getInstance()
    {
        if (singleton == null)
            singleton = new ServerProxy();
        return singleton;
    }

    /**
     *
     * @param username username of player trying to login
     * @param password password of player trying to login
     * @return success or fail login Signal
     */
    @Override
    public Signal login(String username, String password) {

        try {
            String[] parameterTypes = {"String", "String"};
            Object[] parameters = {username, password};
            CommandParams loginCommand = new CommandParams("login", parameterTypes, parameters);
            Signal returnSignal = (Signal) ClientCommunicator.SINGLETON.send(loginCommand);
            return returnSignal;
        }
        catch(IOException e)
        {
            Log.d("IO Execption",e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param username username of player trying to register (must be unique)
     * @param password password of player trying to register
     * @param screenName screenname of player trying to register
     * @return success or fail register Signal
     */
    @Override
    public Signal register(String username, String password, String screenName) {
        try {
            String[] parameterTypes = {"String", "String", "String"};
            Object[] parameters = {username, password, screenName};
            CommandParams registerCommand = new CommandParams("register", parameterTypes, parameters);
            Signal returnSignal = (Signal) ClientCommunicator.SINGLETON.send(registerCommand);
            return returnSignal;
        }
        catch(IOException e)
        {
            Log.d("IO Exception", e.getMessage());
            return null;
        }
    }

    @Override
    public Signal startGame() {
        String[] parameterTypes = {};
        Object[] parameters = {};
        CommandParams startGameCommand = new CommandParams("startGame", parameterTypes, parameters);
        try {
            return (Signal) ClientCommunicator.SINGLETON.send(startGameCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal addGame(Game newgame)
    {
        String[] paramTypes = {"Game"};
        Object[] params = {newgame};
        CommandParams newcommand = new CommandParams("AddGame", paramTypes, params);
        try {
            return (Signal) ClientCommunicator.SINGLETON.send(newcommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal JoinGame(GameID id)
    {
        String[] paramTypes = {"GameID"};
        Object[] params = {id};
        CommandParams newcommand = new CommandParams("JoinGame", paramTypes, params);
        try {
            return (Signal) ClientCommunicator.SINGLETON.send(newcommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
    
