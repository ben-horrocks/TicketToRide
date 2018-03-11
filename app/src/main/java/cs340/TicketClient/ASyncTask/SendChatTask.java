package cs340.TicketClient.ASyncTask;

/**
 * Created by Vibro on 3/10/2018.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import common.DataModels.ChatItem;
import common.DataModels.GameID;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import cs340.TicketClient.Communicator.ServerProxy;

public class SendChatTask extends AsyncTask<Object, Void, Signal>
{
    private Context context;

    public SendChatTask(Context context)
    {
        this.context = context;
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
        if (signal.getSignalType() == SignalType.ERROR)
        {
            Toast.makeText(context, (String)signal.getObject(), Toast.LENGTH_LONG).show();
        } else
        {
            //LobbyPresenter.getInstance().gameStarted();
        }
    }
}
