package cs340.TicketClient.end_game;

import android.os.AsyncTask;
import android.widget.Toast;

import common.communication.Signal;
import common.communication.SignalType;
import common.player_info.User;
import common.player_info.Username;
import cs340.TicketClient.communicator.ServerProxy;

public class EndGamePresenter implements IEndGamePresenter
{
    private EndGameActivity activity;

    public EndGamePresenter(EndGameActivity activity)
    {
        this.activity = activity;
    }

    public void returnToLobby(Username user)
    {
        ReturnToLobbyTask task = new ReturnToLobbyTask();
        Username[] obj = {user};
        task.execute(obj);
        //implement return to lobby here
    }

    public class ReturnToLobbyTask extends AsyncTask<Username, Integer, Signal>
    {
        @Override
        protected Signal doInBackground(Username[] arrayLists)
        {
            return ServerProxy.getInstance().returnToLobby(arrayLists[0]);
        }

        @Override
        protected void onPostExecute(Signal signal)
        {
            super.onPostExecute(signal);
            if(signal.getSignalType() == SignalType.OK)
            {
                activity.goToLobby();
            }
        }
    }

}
