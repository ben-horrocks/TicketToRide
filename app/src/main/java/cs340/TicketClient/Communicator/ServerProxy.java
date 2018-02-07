package cs340.TicketClient.Communicator;

import common.Command;
import android.content.Context;
import common.DataModels.Signal;
import common.DataModels.Game;
import common.DataModels.GameID;
import common.IServer;
import common.ISignal;
import common.Signal;
import cs340.TicketClient.ASyncTask.JoinGameTask;
import cs340.TicketClient.common.*;
import cs340.TicketClient.common.DataModels.Game;
import cs340.TicketClient.common.DataModels.GameID;

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
    public ISignal login(String username, String password) {

        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command loginCommand = new Command("login", parameterTypes, parameters, Command.CommandType.SERVER);
        //send to server
        ISignal response = null;
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
    public ISignal register(String username, String password, String screenName) {
        String[] parameterTypes = {"String", "String", "String"};
        Object[] parameters = {username, password, screenName};
        Command registerCommand = new Command("register", parameterTypes, parameters, Command.CommandType.SERVER);
        //send to server
        ISignal response = null;
        return response;
    }

    @Override
    public void startGame() {

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
    