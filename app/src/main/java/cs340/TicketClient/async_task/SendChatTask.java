package cs340.TicketClient.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import common.chat.ChatItem;
import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameID;
import cs340.TicketClient.communicator.ServerProxy;

public class SendChatTask extends AsyncTask<Object, Void, Signal>
{
    private WeakReference<Context> contextRef;

    public SendChatTask(Context context)
    {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected Signal doInBackground(Object... params)
    {
        GameID game = (GameID) params[0];
        ChatItem message = (ChatItem) params[1];
        return ServerProxy.getInstance().sendChat(game, message);
    }

    @Override
    protected void onPostExecute(Signal signal)
    {
        Context context = contextRef.get();
        if (signal.getSignalType() == SignalType.ERROR)
        {
            if (context != null)
            {
                Toast.makeText(context, (String) signal.getObject(), Toast.LENGTH_LONG).show();
            } else
            {
                System.out.println("Send Chat Error in AsyncTask: " + signal.getObject());
            }
        }
        // else do nothing
    }
}
