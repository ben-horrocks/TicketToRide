package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.*;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Lobby.LobbyActivity;
import cs340.TicketClient.Lobby.LobbyPresenter;

public class AddGameTask extends AsyncTask<Object, Void, Signal>
{
  private Context context;
  public AddGameTask(Context context)
  {
    this.context = context;
  }

  @Override
  protected Signal doInBackground(Object... objects)
  {
    String gamename = (String) objects[0];
    Player player = (Player) objects[1];
    return ServerProxy.getInstance().addGame(gamename, player);
  }

  @Override
  protected void onPostExecute(Signal signal)
  {
    if (signal.getSignalType() == SignalType.ERROR)
    {
      Toast.makeText(context, (String) signal.getObject(), Toast.LENGTH_SHORT).show();
    } else
    {
      //send a successful add game to the LobbyPresenter, change add new game to start game
    }
  }
}
