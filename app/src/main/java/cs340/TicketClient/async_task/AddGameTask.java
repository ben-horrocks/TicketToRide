package cs340.TicketClient.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import common.communication.Signal;
import common.communication.SignalType;
import common.player_info.User;
import cs340.TicketClient.communicator.ServerProxy;

public class AddGameTask extends AsyncTask<Object, Void, Signal>
{
    private WeakReference<Context> contextRef;

    public AddGameTask(Context context)
    {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected Signal doInBackground(Object... objects)
    {
        String gamename = (String) objects[0];
        User user = (User) objects[1];
        return ServerProxy.getInstance().addGame(gamename, user);
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
            } else
            {
                System.out.println("Error in add game AsyncTask: " + signal.getObject());
            }
        }
    }
}