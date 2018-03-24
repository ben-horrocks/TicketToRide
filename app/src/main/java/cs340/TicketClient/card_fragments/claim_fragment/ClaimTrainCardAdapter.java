package cs340.TicketClient.card_fragments.claim_fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import cs340.TicketClient.R;
import cs340.TicketClient.card_fragments.hand_fragment.HandFragment;
import cs340.TicketClient.game.GameModel;

/**
 * Created by jhens on 3/24/2018.
 */

public class ClaimTrainCardAdapter extends RecyclerView.Adapter<ClaimTrainCardAdapter.ClaimTrainCardViewHolder> {

    private TrainCard[] mTrainCards = new TrainCard[0];
    private LayoutInflater mInflater;

    ClaimTrainCardAdapter(Context context, TrainCard[] cards)
    {
        mInflater = LayoutInflater.from(context);
        mTrainCards = cards;
    }

    @Override
    public void onBindViewHolder(ClaimTrainCardViewHolder holder, int position)
    {
        TrainCard trainCard = mTrainCards[position];
        int imageID = getTrainCardImage(trainCard.getType());
        Drawable cardDrawable = mInflater.getContext().getResources().getDrawable(imageID);
        Bitmap bitmap = ((BitmapDrawable) cardDrawable).getBitmap();
        Drawable card = new BitmapDrawable(mInflater.getContext().getResources(),
                Bitmap.createScaledBitmap(bitmap, 600, 400, true));
        holder.myImageView.setImageDrawable(card);
        holder.checkBox.setText(position);
    }

    @Override
    public ClaimTrainCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.claim_recyclerview_item, parent, false);
        return new ClaimTrainCardViewHolder(view);
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
            case LOCOMOTIVE:
                return R.drawable.traincard_locomotive;
            default:
                System.out.println("No TrainCardImage for that Color");
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ClaimTrainCardViewHolder extends RecyclerView.ViewHolder
    {
        ImageView myImageView;
        CheckBox checkBox;

        public ClaimTrainCardViewHolder(View view)
        {
            super(view);
            myImageView = itemView.findViewById(R.id.hand_claimcard_image);
            checkBox = itemView.findViewById(R.id.cardCheck);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int position = Integer.getInteger(checkBox.getText().toString());
                    TrainCard card = GameModel.getInstance().getPlayer().getHand().get(position);
                    if (checkBox.isChecked())
                    {
                        GameModel.getInstance().getQueuedCards().remove(card);
                        checkBox.setChecked(false);
                    } else
                    {
                        GameModel.getInstance().getQueuedCards().add(card);
                        checkBox.setChecked(true);
                    }

                }
            });
        }


    }
}