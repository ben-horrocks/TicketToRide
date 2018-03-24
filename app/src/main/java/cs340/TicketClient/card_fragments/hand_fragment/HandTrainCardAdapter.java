package cs340.TicketClient.card_fragments.hand_fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;

import common.cards.TrainCard;
import common.cards.TrainColor;
import cs340.TicketClient.R;

public class HandTrainCardAdapter
        extends RecyclerView.Adapter<HandTrainCardAdapter.HandTrainCardViewHolder>
{
    private TrainCard[] mTrainCards = new TrainCard[0];
    private LayoutInflater mInflater;

    HandTrainCardAdapter(Context context, TrainCard[] trainCards)
    {
        mInflater = LayoutInflater.from(context);
        mTrainCards = trainCards;
    }

    // inflates the cell layout from xml when needed
    @Override
    public HandTrainCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.hand_recyclerview_item, parent, false);
        return new HandTrainCardViewHolder(view);
    }

    // binds the data to the imageView in each cell
    @Override
    public void onBindViewHolder(HandTrainCardViewHolder holder, int position)
    {
        TrainCard trainCard = mTrainCards[position];
        int imageID = getTrainCardImage(trainCard.getType());
		Drawable cardDrawable = mInflater.getContext().getResources().getDrawable(imageID);
		Bitmap bitmap = ((BitmapDrawable) cardDrawable).getBitmap();
		Drawable card = new BitmapDrawable(mInflater.getContext().getResources(),
				Bitmap.createScaledBitmap(bitmap, 600, 400, true));
        holder.myImageView.setImageDrawable(card);
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
    public int getItemCount()
    {
        return mTrainCards.length;
    }

    class HandTrainCardViewHolder extends RecyclerView.ViewHolder
    {
        ImageView myImageView;

        HandTrainCardViewHolder(View itemView)
        {
            super(itemView);
            myImageView = (ImageView) itemView.findViewById(R.id.hand_traincard_image);
        }
    }
}
