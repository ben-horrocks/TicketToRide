package cs340.TicketClient.card_fragments.deck_fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.cards.TrainCard;
import cs340.TicketClient.R;
import cs340.TicketClient.game.GameModel;


public class DeckFragment extends Fragment implements View.OnClickListener
{
    Map<ImageView, TrainCard> faceUpCards = new HashMap<>();
    Button drawDeck;
    DeckFragmentPresenter presenter;
    ImageView tCard1;
	ImageView tCard2;
	ImageView tCard3;
	ImageView tCard4;
	ImageView tCard5;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deck, container, false);
		List<ImageView> faceUpCardImages = new ArrayList<>();
        tCard1 = v.findViewById(R.id.trainCard1);
        faceUpCardImages.add(tCard1);
        tCard2 = v.findViewById(R.id.trainCard2);
        faceUpCardImages.add(tCard2);
        tCard3 = v.findViewById(R.id.trainCard3);
		faceUpCardImages.add(tCard3);
        tCard4 = v.findViewById(R.id.trainCard4);
		faceUpCardImages.add(tCard4);
        tCard5 = v.findViewById(R.id.trainCard5);
		faceUpCardImages.add(tCard5);
        drawDeck = v.findViewById(R.id.drawButton);

        presenter = new DeckFragmentPresenter(this);

        ArrayList<Integer> trainTypes = presenter.getFaceUpCards();
        for (int i = 0; i < faceUpCardImages.size(); i++)
		{
			int imageID = trainTypes.get(i);
			Drawable cardDrawable = getActivity().getResources().getDrawable(imageID);
			Bitmap bitmap = ((BitmapDrawable) cardDrawable).getBitmap();
			Drawable card = new BitmapDrawable(getActivity().getResources(),
					Bitmap.createScaledBitmap(bitmap, 600, 400, true));
			faceUpCardImages.get(i).setImageDrawable(card);
			faceUpCards.put(faceUpCardImages.get(i), presenter.getCardByID(imageID));
		}

		tCard1.setOnClickListener(this);
		tCard2.setOnClickListener(this);
		tCard3.setOnClickListener(this);
		tCard4.setOnClickListener(this);
		tCard5.setOnClickListener(this);
        return v;
    }

    @Override
	public void onClick(View view)
	{
		if (presenter.isMyTurn())
		{
			if (view.getId() == R.id.drawButton)
			{
				presenter.DrawDeck();
			}
			else
			{
				ImageView imageView = (ImageView) view;
				TrainCard trainCard = faceUpCards.get(imageView);
				presenter.drawTrainCard(trainCard);
				if(imageView == tCard1)
					presenter.DrawFaceUp(0);
				if(imageView == tCard2)
					presenter.DrawFaceUp(1);
				if(imageView == tCard3)
					presenter.DrawFaceUp(2);
				if(imageView == tCard4)
					presenter.DrawFaceUp(3);
				if(imageView == tCard5)
					presenter.DrawFaceUp(4);

			}
		}
		else
		{
			Toast.makeText(getContext(), "Not your turn to claim cards!", Toast.LENGTH_SHORT).show();
		}
	}
}
