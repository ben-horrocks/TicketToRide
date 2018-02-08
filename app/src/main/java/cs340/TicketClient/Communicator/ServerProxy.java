package cs340.TicketClient.Communicator;

import android.util.Log;

import java.io.IOException;

import common.CommandParams;
import common.DataModels.Signal;
import common.DataModels.Game;
import common.DataModels.GameID;
import common.IServer;
import cs340.TicketClient.ASyncTask.JoinGameTask;

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

    }

    @Override
    public void addGame(Game newgame)
    {
        String[] paramTypes = {"Game"};
        Object[] params = {newgame};
        Command newcommand = new Command("AddGame", paramTypes, params);
        //Call the Client Communicator here

    }

    @Override
    public Signal JoinGame(GameID id)
    {
        String[] paramTypes = {"GameID"};
        Object[] params = {id};
        Command newcommand = new Command("JoinGame", paramTypes, params);
        //execute the command over the client server here

        JoinGameTask task = new JoinGameTask();
        task.execute(id);
    }
}
    