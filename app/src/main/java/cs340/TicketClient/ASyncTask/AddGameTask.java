package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;

import cs340.TicketClient.Communicator.ClientCommunicator;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.common.Command;
import cs340.TicketClient.common.DataModels.Game;
import cs340.TicketClient.common.Signal;

/**
 * Created by Ben_D on 2/4/2018.
 */

public class AddGameTask extends AsyncTask<Game, Void, Signal>
{
  public AddGameTask()
  {

  }
  @Override
  protected Signal doInBackground(Game... games)
  {
    ServerProxy.getInstance().addGame(games.clone()[0]);

    //execute the command over the client server here
    return null;
  }

  @Override
  protected void onPostExecute(Signal signal)
  {
    //Signal Handling here
  }
}
