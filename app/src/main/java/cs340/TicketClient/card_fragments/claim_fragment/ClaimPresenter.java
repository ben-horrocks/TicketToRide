package cs340.TicketClient.card_fragments.claim_fragment;

import android.os.AsyncTask;

import common.cards.HandTrainCards;
import common.communication.Signal;
import common.game_data.GameID;
import common.map.Edge;
import common.player_info.Username;
import common.request.ClaimRequest;
import cs340.TicketClient.game.GameModel;

/**
 * Created by jhens on 3/24/2018.
 */

public class ClaimPresenter implements IClaimPresenter {

    private ClaimFragment fragment;
    private GameModel model;

    ClaimPresenter(ClaimFragment fragment)
    {
        this.fragment = fragment;
        model = null;
    }

    @Override
    public HandTrainCards getPlayerTrainCards() {
        return GameModel.getInstance().getPlayer().getHand();
    }

    public void sendClaimRequest()
    {
        GameID id = GameModel.getInstance().getGameID();
        Username user = GameModel.getInstance().getUserName();
        Edge edge = GameModel.getInstance().getSelectedEdge();
        HandTrainCards cards = new HandTrainCards(GameModel.getInstance().getQueuedCards());
        ClaimRequest request = new ClaimRequest(id, user, edge, cards);
        ClaimTask task = new ClaimTask(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
    }


    class ClaimTask extends AsyncTask<ClaimRequest, Integer, Signal>
    {
        ClaimPresenter presenter;

        ClaimTask(ClaimPresenter presenter)
        {
            this.presenter = presenter;
        }
        
        @Override
        protected Signal doInBackground(ClaimRequest... claimRequests) {
            return null;
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
        }
    }
}
