package cs340.TicketClient.async_task;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import common.communication.Signal;

/**
 * Created by Kavika F.
 */

public class IsTurnOverTask extends AsyncTask<Object, Void, Signal>
{
	private WeakReference<Context> contextRef;

	public IsTurnOverTask(Context context)
	{
		contextRef = new WeakReference<>(context);
	}

	@Override
	protected Signal doInBackground(Object... objects)
	{
		return null;
	}
}
