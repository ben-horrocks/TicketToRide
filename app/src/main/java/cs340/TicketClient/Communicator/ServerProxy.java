package cs340.TicketClient.Communicator;

import common.DataModels.Signal;

import cs340.TicketClient.ASyncTask.JoinGameTask;
import common.*;
import common.DataModels.*;

/**
 * Created by Ben_D on 1/29/2018.
 */

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

        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        CommandParams loginCommand = new CommandParams("login", parameterTypes, parameters);
        //send to server
        Signal response = null;
        return response;
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
        String[] parameterTypes = {"String", "String", "String"};
        Object[] parameters = {username, password, screenName};
        CommandParams registerCommand = new CommandParams("register", parameterTypes, parameters);
        //send to server
        Signal response = null;
        return response;
    }

    @Override
    public Signal startGame() {
        String[] parameterTypes = {};
        Object[] parameters = {};
        CommandParams startGameCommand = new CommandParams("startGame", parameterTypes, parameters);
        try {
            return (Signal) ClientCommunicator.getInstance().send(startGameCommand);
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
            return (Signal) ClientCommunicator.getInstance().send(newcommand);
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
            return (Signal) ClientCommunicator.getInstance().send(newcommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
    