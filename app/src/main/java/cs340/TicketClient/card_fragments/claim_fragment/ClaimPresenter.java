package cs340.TicketClient.card_fragments.claim_fragment;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.ArrayList;

import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.communication.Signal;
import common.communication.SignalType;
import common.game_data.GameID;
import common.map.Edge;
import common.player_info.Username;
import common.request.ClaimRequest;
import cs340.TicketClient.communicator.ServerProxy;
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

    public void success(Edge e)
    {
        GameModel.getInstance().getGameData().edgeClaimed(GameModel.getInstance().getSelectedEdge(),
                                                            GameModel.getInstance().getQueuedCards());
        //update map
        FragmentManager fm = fragment.getActivity().getSupportFragmentManager();
        fm.popBackStack();
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
        if (GameModel.getInstance().getPlayer().canClaimEdge(edge)) {
            HandTrainCards cards = new HandTrainCards(GameModel.getInstance().getQueuedCards());
            edge.setOwner(GameModel.getInstance().getPlayer());
            ClaimRequest request = new ClaimRequest(id, user, edge, cards);
            ClaimTask task = new ClaimTask(this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        }
        else
        {
            Toast.makeText(fragment.getActivity(), "Can't Claim Route", Toast.LENGTH_SHORT).show();
        }

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
            ClaimRequest r = claimRequests[0];
            return ServerProxy.getInstance().playerClaimedEdge(r.getId(), r.getUser(),
                                                                r.getEdge(), r.getCards());
        }

        @Override
        protected void onPostExecute(Signal signal) {
            super.onPostExecute(signal);
            if (signal.getSignalType() == SignalType.OK)
            {
                GameModel.getInstance().setQueuedCards(new ArrayList<TrainCard>());
                presenter.success((Edge)signal.getObject());


            }
            else
            {
                //MAKE CANT CLAIM TOAST
            }
        }
    }
}
