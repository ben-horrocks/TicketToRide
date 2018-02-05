package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;

import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.common.Command;
import cs340.TicketClient.common.DataModels.Game;
import cs340.TicketClient.common.DataModels.GameID;
import cs340.TicketClient.common.Signal;

/**
 * Created by Ben_D on 2/4/2018.
 */

public class JoinGameTask extends AsyncTask<GameID, Void, Signal>
{
  private Context context;
  public JoinGameTask(Context context)
  {
    this.context = context;
  }

  @Override
  protected Signal doInBackground(GameID... gameIDS)
  {
    ServerProxy.getInstance().JoinGame(gameIDS.clone()[0]);

    return null;
  }
}
