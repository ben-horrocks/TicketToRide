package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.*;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Lobby.LobbyActivity;
import cs340.TicketClient.Lobby.LobbyPresenter;

public class AddGameTask extends AsyncTask<Game, Void, Signal>
{
  private Context context;
  public AddGameTask(Context context)
  {
    this.context = context;
  }

  @Override
  protected Signal doInBackground(Game... games)
  {
    return ServerProxy.getInstance().addGame(games.clone()[0]);
  }

  @Override
  protected void onPostExecute(Signal signal)
  {
    if (signal.getSignalType() == SignalType.ERROR)
    {
      Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
    } else
    {
      //send a successful add game to the LobbyPresenter
    }
  }
}
