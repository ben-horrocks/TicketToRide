package cs340.TicketClient.card_fragments.destination_card_fragment;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.widget.CheckBox;

import common.cards.DestinationCard;
import common.request.SendCardsRequest;
import common.cards.HandDestinationCards;
import common.communication.Signal;
import common.game_data.GameID;
import common.player_info.Username;
import cs340.TicketClient.communicator.ServerProxy;
import cs340.TicketClient.game.GameModel;

import static common.communication.SignalType.OK;

/**
 * Presenter for DestinationCardFragment view
 * @invarient GameModel.getInstance() has appropriate data (clientGameData and HandDestinationCard)
 */
public class DestinationCardFragmentPresenter implements IDestinationCardFragmentPresenter
{

    private GameModel model; // Game model with relevant data
    private DestinationCardFragment fragment; // Fragment this presenter is for

    /**
     * Constructor
     * @pre fragment is not null
     * @post model will be non-null
     * @post this.fragment will be non-null
     * @param fragment DestinationCardFragment that this presenter is being created for
     */
    DestinationCardFragmentPresenter(DestinationCardFragment fragment)
    {
        model = GameModel.getInstance();
        this.fragment = fragment;
    }

    /**
     * Function returns the HandDestinationCard item that is
     * @pre GameModel singleton works as intended
     * @post you will either get back 3 destinations cards or a null object
     * @return a HandCardFragment or null if it has already been used
     */
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

    /**
     * This method takes the cards in the fragment and returns them to the server, indicating which were selected and which were not.
     * @pre there is a connection to the server
     * @post the SendCardsTask is in the process, or done, returning the cards to the server and closing the fragment
     * @post the player's destination cards are updated with the new additions
     */
    public void confirmDestinationCards()
    {
        HandDestinationCards selected = new HandDestinationCards();
        HandDestinationCards returned = new HandDestinationCards();
        int index = 0;
        for(CheckBox box : fragment.checkBoxes)
        {
            DestinationCard card = fragment.dCards.get(index);
            if(box.isChecked() && card != null)
            {
                selected.add(fragment.dCards.get(index));
            } else
            {
                returned.add(fragment.dCards.get(index));
            }
            index++;
        }
        GameID id = model.getGameID();
        Username user = model.getUserName();
        SendCardsRequest request = new SendCardsRequest(id, user, selected, returned);
        SendCardsTask task = new SendCardsTask(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        FragmentManager fm = fragment.getActivity().getSupportFragmentManager();
        fm.beginTransaction().remove(fragment).commit();
    }


    private static class SendCardsTask extends AsyncTask<SendCardsRequest, Integer, Signal>
    {
        DestinationCardFragmentPresenter presenter;

        SendCardsTask(DestinationCardFragmentPresenter presenter) { this.presenter = presenter; }

        /**
         * This method executes the return destinationCards command on a different thread from the UI thread.
         * @pre obj is not null
         * @pre It has 1 request object in it that is not null and non of it's members are null.
         * @post the server has the updated information about the destination cards, and the player is listed as having them.
         * @param obj request with the cards to return, the ones the use selected, the GameID, and the player's username.
         * @return a Signal saying that the cards were returned to the database successfully
         */
        @Override
        protected Signal doInBackground(SendCardsRequest[] obj)
        {
            SendCardsRequest request = obj[0];
            return ServerProxy.getInstance()
                    .returnDestinationCards(request.getId(), request.getUser(),
                                            request.getSelected(), request.getReturned());
        }

        /**
         * This method closes the destinationCardFragment after the cards are returned to the server.
         * @pre the Signal is either an OK or ERROR signal
         * @post the client is given confirmation that either the return of the cards failed, or succeeded.
         * @param signal Signal returned from the server
         */
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
                System.out.printf("error back from return destination cards");
                FragmentManager fm = presenter.fragment.getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        }
    }
}
