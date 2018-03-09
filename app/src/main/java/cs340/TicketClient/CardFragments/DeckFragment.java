package cs340.TicketClient.CardFragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import cs340.TicketClient.R;


public class DeckFragment extends Fragment {

    ImageView tCard1;
    ImageView tCard2;
    ImageView tCard3;
    ImageView tCard4;
    ImageView tCard5;
    Button drawDeck;
    DeckFragmentPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_deck, container, false);
        tCard1 = v.findViewById(R.id.trainCard1);
        tCard2 = v.findViewById(R.id.trainCard2);
        tCard3 = v.findViewById(R.id.trainCard3);
        tCard4 = v.findViewById(R.id.trainCard4);
        tCard5 = v.findViewById(R.id.trainCard5);
        drawDeck = v.findViewById(R.id.drawButton);
        presenter = new DeckFragmentPresenter(this);

        ArrayList<Integer> trainTypes = presenter.getFaceUpCards();
        tCard1.setImageResource(trainTypes.get(0));
        tCard2.setImageResource(trainTypes.get(1));
        tCard3.setImageResource(trainTypes.get(2));
        tCard4.setImageResource(trainTypes.get(3));
        tCard5.setImageResource(trainTypes.get(4));
        return v;
    }
}
