package cs340.TicketClient.Game;

import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.DestinationCard;
import common.DataModels.GameData.SendCardsRequest;
import common.DataModels.GameID;
import common.DataModels.Signal;
import common.DataModels.Username;
import cs340.TicketClient.Communicator.ServerProxy;

/**
 * Created by jhens on 3/1/2018.
 */

class DestinationCardFragmentPresenter {

    private GameModel model;
    private DestinationCardFragment fragment;

    DestinationCardFragmentPresenter(DestinationCardFragment fragment)
    {
        this.model = null;
        this.fragment = fragment;
    }


    ArrayList<DestinationCard> getDCards()
    {
        return null;
    }

    void confirmDestinationCards()
    {
        ArrayList<DestinationCard> selected = new ArrayList<>();
        ArrayList<DestinationCard> returned = new ArrayList<>();
        if(fragment.card1Check.isChecked())
            selected.add(fragment.dCards.get(0));
        else
            returned.add(fragment.dCards.get(0));
        if(fragment.card2Check.isChecked())
            selected.add(fragment.dCards.get(1));
        else
            returned.add(fragment.dCards.get(1));
        if(fragment.card3Check.isChecked())
            selected.add(fragment.dCards.get(2));
        else
            returned.add(fragment.dCards.get(2));
        GameID id = null;
        Username user = null;
        SendCardsRequest request= new SendCardsRequest(id, user, selected, returned);
        SendCardsTask task = new SendCardsTask();
        task.execute(request);
        return;

    }

    class SendCardsTask extends AsyncTask<SendCardsRequest,Integer ,Signal>
    {
        @Override
        protected Signal doInBackground(SendCardsRequest[] obj) {
            SendCardsRequest request = obj[0];
            return ServerProxy.getInstance().returnDestinationCards(request.getId(), request.getUser(), request.getSelected(), request.getReturned());
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
        }
    }
}
