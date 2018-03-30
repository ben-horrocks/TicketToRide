package cs340.TicketClient.card_fragments.claim_fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import cs340.TicketClient.R;
import cs340.TicketClient.card_fragments.hand_fragment.HandFragment;
import cs340.TicketClient.game.GameModel;

public class ClaimTrainCardAdapter extends RecyclerView.Adapter<ClaimTrainCardAdapter.ClaimTrainCardViewHolder> {

    private List<TrainCard> mTrainCards = new ArrayList<>();

    ClaimTrainCardAdapter()
    {
    }

    @Override
    public void onBindViewHolder(ClaimTrainCardViewHolder holder, int position)
    {
        TrainCard trainCard = mTrainCards.get(position);
        int imageID = getTrainCardImage(trainCard.getType());
        holder.myImageView.setImageResource(imageID);
        holder.id = position;

    }

    @Override
    public ClaimTrainCardAdapter.ClaimTrainCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.claim_recyclerview_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ClaimTrainCardViewHolder vh = new ClaimTrainCardViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public int getItemCount()
    {
        return mTrainCards.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private int getTrainCardImage(TrainColor color)
    {
        switch (color)
        {
            case BLACK:
                return R.drawable.traincard_black;
            case BLUE:
                return R.drawable.traincard_blue;
            case GREEN:
                return R.drawable.traincard_green;
            case ORANGE:
                return R.drawable.traincard_orange;
            case PINK:
                return R.drawable.traincard_pink;
            case RED:
                return R.drawable.traincard_red;
            case WHITE:
                return R.drawable.traincard_white;
            case YELLOW:
                return R.drawable.traincard_yellow;
            case GRAY:
                return R.drawable.traincard_locomotive;

            default:
                System.out.println("No TrainCardImage for that Color");
                return 0;
        }
    }

    public void add(TrainCard card)
    {
        mTrainCards.add(card);
        notifyDataSetChanged();
    }

    class ClaimTrainCardViewHolder extends RecyclerView.ViewHolder
    {
        ImageView myImageView;
        ImageView mIsSelected;
        boolean checked = false;
        int id;
        public ClaimTrainCardViewHolder(View view)
        {
            super(view);
            myImageView = itemView.findViewById(R.id.hand_claimcard_image);
            mIsSelected = itemView.findViewById(R.id.cardCheck);
            mIsSelected.setImageResource(R.drawable.unchecked);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ArrayList<TrainCard> cards = GameModel.getInstance().getQueuedCards();
                    TrainCard card = GameModel.getInstance().getPlayer().getHand().get(id);
                    if (!checked)
                    {

                        cards.add(card);
                        mIsSelected.setImageResource(R.drawable.checked);
                        checked = true;
                    } else
                    {
                        cards.remove(card);
                        mIsSelected.setImageResource(R.drawable.unchecked);
                        checked = false;
                    }
                    notifyDataSetChanged();

                }
            });
        }


    }
}