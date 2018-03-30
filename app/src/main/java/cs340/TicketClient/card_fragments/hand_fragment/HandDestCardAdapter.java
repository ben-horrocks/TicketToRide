package cs340.TicketClient.card_fragments.hand_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import common.cards.DestinationCard;
import cs340.TicketClient.R;

/**
 * Created by Kavika F.
 */

public class HandDestCardAdapter extends RecyclerView.Adapter<HandDestCardAdapter.HandDCViewHolder>
{
    private DestinationCard[] mDestinationCards = new DestinationCard[0];
    private LayoutInflater mInflater;

    HandDestCardAdapter(Context context, DestinationCard[] destinationCards)
    {
        mInflater = LayoutInflater.from(context);
        mDestinationCards = destinationCards;
    }

    @Override
    public HandDCViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.destination_recyclerview_item, parent, false);
        return new HandDCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HandDCViewHolder holder, int position)
    {
        DestinationCard destinationCard = mDestinationCards[position];
        String s = destinationCard.toString();
        holder.mTextView.setText(destinationCard.toString());
    }

    @Override
    public int getItemCount()
    {
        return mDestinationCards.length;
    }

    class HandDCViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;

        HandDCViewHolder(View itemView)
        {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.hand_destinationCard_item);
        }
    }
}
