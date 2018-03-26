
package cs340.TicketClient.async_task;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import common.communication.Signal;
import common.game_data.GameID;
import common.player_info.Username;
import cs340.TicketClient.communicator.ServerProxy;

/**
 * Created by Kavika F.
 */

public class ResumeGameTask extends AsyncTask<Object, Void, Signal>
{
	private WeakReference<Context> contextRef;

	public ResumeGameTask(Context context) { contextRef = new WeakReference<>(context); }

	@Override
	protected Signal doInBackground(Object... objects)
	{
		GameID gameID = (GameID) objects[0];
		Username username = (Username) objects[1];
		return ServerProxy.getInstance().resumeGame(gameID, username);
	}
}
