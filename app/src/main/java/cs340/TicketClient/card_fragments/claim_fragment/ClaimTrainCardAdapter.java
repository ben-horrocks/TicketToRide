package cs340.TicketClient.card_fragments.claim_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import common.cards.HandTrainCards;
import cs340.TicketClient.card_fragments.hand_fragment.HandFragment;

/**
 * Created by jhens on 3/24/2018.
 */

public class ClaimTrainCardAdapter extends RecyclerView.Adapter<ClaimTrainCardAdapter.ClaimTrainCardViewHolder> {

    @Override
    public void onBindViewHolder(ClaimTrainCardViewHolder holder, int position) {

    }

    @Override
    public ClaimTrainCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ClaimTrainCardViewHolder extends RecyclerView.ViewHolder
    {
        ImageView view;
        public ClaimTrainCardViewHolder(View view)
        {
            super(view);
            this.view = null;
        }
    }
}