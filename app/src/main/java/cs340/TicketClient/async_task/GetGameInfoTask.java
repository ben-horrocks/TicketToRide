package cs340.TicketClient.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameInfo;
import common.player_info.Username;
import cs340.TicketClient.communicator.ServerProxy;
import cs340.TicketClient.lobby.LobbyModel;

/**
 * Created by Kavika F.
 */

public class GetGameInfoTask extends AsyncTask<Object, Void, Signal>
{
	private WeakReference<Context> contextRef;

	public GetGameInfoTask(Context context)
	{
		contextRef = new WeakReference<>(context);
	}


	@Override
	protected Signal doInBackground(Object... objects)
	{
		Username username = (Username) objects[0];
		return ServerProxy.getInstance().getAvailableGameInfo(username);
	}

	@Override
	protected void onPostExecute(Signal signal)
	{
		Context context = contextRef.get();
		if (signal.getSignalType() == SignalType.ERROR)
		{
			if (context != null)
			{
				Toast.makeText(context, (String) signal.getObject(), Toast.LENGTH_SHORT).show();
			}else
			{
				System.out.println("Error in add game AsyncTask: " + signal.getObject());
			}
		}
		else
		{
			LobbyModel.getSingleton().setGames((List<GameInfo>)signal.getObject());
		}
	}
}
