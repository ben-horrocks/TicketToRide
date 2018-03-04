package cs340.TicketClient.CardFragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import common.DataModels.TrainCard;
import common.DataModels.TrainColor;
import cs340.TicketClient.R;

public class TrainCardHandAdapter extends RecyclerView.Adapter<TrainCardHandAdapter.TrainCardHandViewHolder>
{
	private TrainCard[] mTrainCards = new TrainCard[0];
	private LayoutInflater mInflater;

	TrainCardHandAdapter(Context context, TrainCard[] trainCards)
	{
		this.mInflater = LayoutInflater.from(context);
		mTrainCards = trainCards;
	}

	// inflates the cell layout from xml when needed
	@Override
	public TrainCardHandViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = mInflater.inflate(R.layout.hand_recyclerview_item, parent, false);
		return new TrainCardHandViewHolder(view);
	}

	// binds the data to the imageView in each cell
	@Override
	public void onBindViewHolder(TrainCardHandViewHolder holder, int position)
	{
		TrainCard trainCard = mTrainCards[position];
		holder.myImageView.setImageResource(getTrainCardImage(trainCard.getType()));
	}

	private int getTrainCardImage(TrainColor color)
	{
		switch (color)
		{
			case BLACK: return R.drawable.traincard_black;
			case BLUE: return R.drawable.traincard_blue;
			case GREEN: return R.drawable.traincard_green;
			case ORANGE: return R.drawable.traincard_orange;
			case PINK: return R.drawable.traincard_pink;
			case RED: return R.drawable.traincard_red;
			case WHITE: return R.drawable.traincard_white;
			case YELLOW: return R.drawable.traincard_yellow;
			case LOCOMOTIVE: return R.drawable.traincard_locomotive;
			default: System.out.println("No TrainCardImage for that Color");
				return 0;
		}
	}

	@Override
	public int getItemCount()
	{
		return mTrainCards.length;
	}

	class TrainCardHandViewHolder extends RecyclerView.ViewHolder
	{
		ImageView myImageView;

		TrainCardHandViewHolder(View itemView)
		{
			super(itemView);
			myImageView = (ImageView) itemView.findViewById(R.id.hand_traincard_image);
		}
	}
}