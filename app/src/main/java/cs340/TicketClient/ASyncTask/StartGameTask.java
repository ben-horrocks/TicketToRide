package cs340.TicketClient.ASyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameID;
import cs340.TicketClient.Communicator.ServerProxy;

public class StartGameTask extends AsyncTask<GameID, Void, Signal>
{
    private WeakReference<Context> contextRef;

    public StartGameTask(Context context)
    {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected Signal doInBackground(GameID... games)
    {
        return ServerProxy.getInstance().startGame(games[0]);
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
                System.out.println("Error starting game in AsyncTask: " + signal.getObject());
            }
        }
    }
}
