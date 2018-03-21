package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameID;
import common.player_info.User;
import cs340.TicketClient.Communicator.ServerProxy;

public class JoinGameTask extends AsyncTask<Object, Void, Signal>
{
    private WeakReference<Context> contextRef;

    public JoinGameTask(Context context)
    {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected Signal doInBackground(Object... objects)
    {
        User user = (User) objects[0];
        GameID id = (GameID) objects[1];
        return ServerProxy.getInstance().joinGame(user, id);
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
                System.out.println("Join Game Error in AsyncTask: " + signal.getObject());
            }
        }
    }
}
