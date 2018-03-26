package cs340.TicketClient.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameID;
import common.player_info.Username;
import cs340.TicketClient.communicator.ServerProxy;

/**
 * Created by Kavika F.
 */

public class TurnEndedTask extends AsyncTask<Object, Void, Signal>
{
	public TurnEndedTask() {}

	@Override
	protected Signal doInBackground(Object... objects)
	{
		GameID gameID = (GameID)objects[0];
		Username username = (Username)objects[1];
		return ServerProxy.getInstance().turnEnded(gameID, username);
	}

	@Override
	protected void onPostExecute(Signal signal)
	{
		if (signal.getSignalType() == SignalType.ERROR)
		{
			System.out.println("Error in TurnEnded AsyncTask: " + signal.getObject());
		}
	}
}
