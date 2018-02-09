package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.*;
import cs340.TicketClient.Communicator.ServerProxy;
import common.CommandParams;

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

    return ServerProxy.getInstance().JoinGame(gameIDS.clone()[0]);
  }

  @Override
  protected void onPostExecute(Signal signal)
  {
    if(signal.getSignalType() == SignalType.ERROR)
    {
      Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
    } else
    {

    }
  }
}
