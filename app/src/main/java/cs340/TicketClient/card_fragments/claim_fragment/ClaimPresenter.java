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
import common.player_info.Player;
import common.player_info.Username;
import common.request.ClaimRequest;
import cs340.TicketClient.async_task.TurnEndedTask;
import cs340.TicketClient.communicator.ServerProxy;
import cs340.TicketClient.game.GameModel;

public class ClaimPresenter implements IClaimPresenter {

    private ClaimFragment fragment;
    private GameModel model;

    ClaimPresenter(ClaimFragment fragment)
    {
        this.fragment = fragment;
        model = GameModel.getInstance();
    }

    public void success(Edge e)
    {
        model.getGameData().edgeClaimed(model.getSelectedEdge(), model.getQueuedCards());
        TurnEndedTask task = new TurnEndedTask();
        task.execute(GameModel.getInstance().getGameID(), GameModel.getInstance().getUserName());
        //update map
        FragmentManager fm = fragment.getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public HandTrainCards getPlayerTrainCards() {
        return model.getPlayer().getHand();
    }

    public void sendClaimRequest()
    {
        GameID id = GameModel.getInstance().getGameID();
        Username user = GameModel.getInstance().getUserName();
        Edge edge = GameModel.getInstance().getSelectedEdge();
        HandTrainCards cards = new HandTrainCards(GameModel.getInstance().getQueuedCards());
        if (GameModel.getInstance().getPlayer().canClaimEdgeWithSelected(edge, cards)) {
            GameModel.getInstance().getPlayer().getHand().removeAll(cards.getTrainCards());
            GameModel.getInstance().getPlayer().getPieces().useTrainPieces(cards.size());
            GameModel.getInstance().getPlayer().addPoints(edge.computePointValue());
            edge.setOwner(GameModel.getInstance().getPlayer());
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
                model.setQueuedCards(new ArrayList<TrainCard>());
                presenter.success((Edge)signal.getObject());
				//TurnEndedTask task = new TurnEndedTask();
				//task.execute(model.getGameID(), model.getUserName());
            }
            else
            {
                //MAKE CANT CLAIM TOAST
            }
        }
    }
}
