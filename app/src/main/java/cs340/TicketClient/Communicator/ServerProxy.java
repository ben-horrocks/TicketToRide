package cs340.TicketClient.Communicator;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

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

    private static final String stringClassName = "java.lang.String";
    private static final String gameIDClassname = "common.DataModels.GameID";
    private static final String playerClassName = "common.DataModels.Player";

    /**
     *
     * @param username username of player trying to login
     * @param password password of player trying to login
     * @return success or fail login Signal
     */
    @Override
    public Signal login(String username, String password) {

        try {
            String[] parameterTypes = {stringClassName, stringClassName};
            Object[] parameters = {username, password};
            CommandParams loginCommand = new CommandParams("login", parameterTypes, parameters);
            Signal returnSignal = (Signal) ClientCommunicator.getSingleton().send(loginCommand);
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
            String[] parameterTypes = {stringClassName, stringClassName, stringClassName};
            Object[] parameters = {username, password, screenName};
            CommandParams registerCommand = new CommandParams("register", parameterTypes, parameters);
            Signal returnSignal = (Signal) ClientCommunicator.getSingleton().send(registerCommand);
            return returnSignal;
        }
        catch(IOException e)
        {
            Log.d("IO Exception", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Signal startGame(GameID id) {
        String[] parameterTypes = {gameIDClassname};
        Object[] parameters = {id};
        CommandParams startGameCommand = new CommandParams("startGame", parameterTypes, parameters);
        try {
            return (Signal) ClientCommunicator.getSingleton().send(startGameCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal getAvailableGameInfo() {
        String[] parameterTypes = {};
        Object[] parameters = {};
        CommandParams getAvailableGameInfoCommand =
                new CommandParams("getAvailableGameInfo", parameterTypes, parameters);
        try{
            return (Signal) ClientCommunicator.getSingleton().send(getAvailableGameInfoCommand);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal addGame(String gameName, Player player)
    {
        String[] paramTypes = {stringClassName, playerClassName};
        Object[] params = {gameName, player};
        CommandParams newcommand = new CommandParams("addGame", paramTypes, params);
        try {
            return (Signal) ClientCommunicator.getSingleton().send(newcommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signal joinGame(Player player, GameID id)
    {
        String[] paramTypes = {playerClassName, gameIDClassname};
        Object[] params = {player, id};
        CommandParams newcommand = new CommandParams("joinGame", paramTypes, params);
        try {
            return (Signal) ClientCommunicator.getSingleton().send(newcommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Signal populate(){
        //empty
        return null;
    }

    @Override
    public Signal returnDestinationCards(ArrayList<Object> pickedCards, ArrayList<Object> returnCards) {
        return null;
    }
}
    
