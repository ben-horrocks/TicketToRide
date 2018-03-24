package cs340.TicketClient.card_fragments.deck_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.cards.TrainCard;
import cs340.TicketClient.R;
import cs340.TicketClient.game.GameModel;


public class DeckFragment extends Fragment implements View.OnClickListener
{
    Map<ImageView, TrainCard> faceUpCards;
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

        SparseArray<TrainCard> trainTypes = presenter.getFaceUpCards();
        for (int i = 0; i < faceUpCardImages.size(); i++)
		{
			faceUpCardImages.get(i).setImageResource(trainTypes.keyAt(i));
			faceUpCards.put(faceUpCardImages.get(i), trainTypes.get(i));
		}

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
				presenter.DrawFaceUp(GameModel.getInstance().getGameData().getFaceUp().indexOf(trainCard));
			}
		}
		else
		{
			Toast.makeText(getContext(), "Not your turn to claim cards!", Toast.LENGTH_SHORT).show();
		}
	}
}
