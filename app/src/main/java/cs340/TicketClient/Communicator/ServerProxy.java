package cs340.TicketClient.Communicator;

import android.content.Context;

import cs340.TicketClient.ASyncTask.AddGameTask;
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

    @Override
    public Signal login(String username, String password) {

        //create Command

        return null;
    }

    @Override
    public Signal register(String username, String password, String screenName) {
        return null;
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
