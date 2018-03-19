package cs340.TicketClient.CardFragments.destination_card_fragment;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import common.DataModels.GameData.SendCardsRequest;
import common.DataModels.*;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Game.GameModel;

import static common.DataModels.SignalType.OK;

public class DestinationCardFragmentPresenter implements IDestinationCardFragmentPresenter
{

    private GameModel model;
    private DestinationCardFragment fragment;

    DestinationCardFragmentPresenter(DestinationCardFragment fragment)
    {
        model = GameModel.getInstance();
        this.fragment = fragment;
    }


    public HandDestinationCards getDCards()
    {
        HandDestinationCards cards = null;
        if (GameModel.getInstance().getInitialDCards() != null)
        {
            cards = model.getInitialDCards();
            model.clearDCards();
        }
        return cards;
    }

    public void confirmDestinationCards()
    {
        HandDestinationCards selected = new HandDestinationCards();
        HandDestinationCards returned = new HandDestinationCards();
        if (fragment.card1Check.isChecked())
        {
            selected.add(fragment.dCards.get(0));
        } else
        {
            returned.add(fragment.dCards.get(0));
        }
        if (fragment.card2Check.isChecked())
        {
            selected.add(fragment.dCards.get(1));
        } else
        {
            returned.add(fragment.dCards.get(1));
        }
        if (fragment.card3Check.isChecked())
        {
            selected.add(fragment.dCards.get(2));
        } else
        {
            returned.add(fragment.dCards.get(2));
        }
        GameID id = model.getGameID();
        Username user = model.getPlayer().getUser().getUsername();
        SendCardsRequest request = new SendCardsRequest(id, user, selected, returned);
        SendCardsTask task = new SendCardsTask(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        FragmentManager fm = fragment.getActivity().getSupportFragmentManager();
        fm.beginTransaction().remove(fragment).commit();
    }

    private static class SendCardsTask extends AsyncTask<SendCardsRequest, Integer, Signal>
    {
        DestinationCardFragmentPresenter presenter;

        SendCardsTask(DestinationCardFragmentPresenter presenter)
        {
            this.presenter = presenter;
        }

        @Override
        protected Signal doInBackground(SendCardsRequest[] obj)
        {
            SendCardsRequest request = obj[0];
            return ServerProxy.getInstance()
                    .returnDestinationCards(request.getId(), request.getUser(),
                                            request.getSelected(), request.getReturned());
        }

        @Override
        protected void onPostExecute(Signal signal)
        {
            super.onPostExecute(signal);
            if (signal.getSignalType() == OK)
            {
                FragmentManager fm = presenter.fragment.getActivity().getSupportFragmentManager();
                fm.popBackStack();
            } else
            {
                System.out.printf("back info back from return destination cards");
                FragmentManager fm = presenter.fragment.getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        }
    }
}
