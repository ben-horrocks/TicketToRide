package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.*;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Game.GameActivity;
import cs340.TicketClient.Lobby.LobbyActivity;
import cs340.TicketClient.Lobby.LobbyPresenter;

public class StartGameTask extends AsyncTask<GameID, Void, Signal>
{
  private Context context;
  public StartGameTask(Context context)
  {
    this.context = context;
  }

  @Override
  protected Signal doInBackground(GameID... games)
  {
    return ServerProxy.getInstance().startGame(games[0]);
  }

  @Override
  protected void onPostExecute(Signal signal)
  {
    if (signal.getSignalType() == SignalType.ERROR)
    {
      Toast.makeText(context, (String)signal.getObject(), Toast.LENGTH_SHORT).show();
    } else
    {
      GameID id = (GameID) signal.getObject();
      LobbyPresenter.getInstance().startGame(id);
    }
  }
}
