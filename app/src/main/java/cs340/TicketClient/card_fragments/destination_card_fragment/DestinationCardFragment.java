package cs340.TicketClient.card_fragments.destination_card_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import common.cards.DestinationCard;
import common.cards.HandDestinationCards;
import common.player_info.turn_state.InitialDestinationCardDraw;
import cs340.TicketClient.game.GameModel;
import cs340.TicketClient.R;

public class DestinationCardFragment extends Fragment
{
    TextView[] cardViews;
    CheckBox[] checkBoxes;
    Button confirmCards;
    HandDestinationCards dCards;
    DestinationCardFragmentPresenter presenter;

    public DestinationCardFragment()
    {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination_card, container, false);

        int[] cardIDs = {R.id.card1View, R.id.card2View, R.id.card3View};
        cardViews = new TextView[3];
        for(int i = 0; i < cardViews.length; i++)
        {
            cardViews[i] = view.findViewById(cardIDs[i]);
        }

        int[] checkIDs = {R.id.card1, R.id.card2, R.id.card3};
        checkBoxes = new CheckBox[3];
        for(int i = 0; i < checkBoxes.length; i++)
        {
            checkBoxes[i] = view.findViewById(checkIDs[i]);
            checkBoxes[i].setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        toggleButton();
                    }
                }
            );
        }

        confirmCards = view.findViewById(R.id.confirmButton);
        toggleButton();
        presenter = new DestinationCardFragmentPresenter(this);

        Bundle extras = getArguments();
        if (extras.get("cards") != null)
        {
            dCards = (HandDestinationCards) extras.get("cards");
        }

        //dCards = presenter.getDCards();
        int numCards = 0; //have to count cards because for some reason dCards sometimes has null cards
        for(int i = 0; i < 3; i++)
        {
            DestinationCard card = dCards.get(i);
            if(card != null)
            {
                String start = card.getCity1();
                String end = card.getCity2();
                int points = card.getPointValue();
                String path = start + " -> " + end + "\n Points: " + points;
                cardViews[i].setText(path);
                checkBoxes[i].setClickable(true);
                numCards++;
            }
            else
            {
                cardViews[i].setText("");
                checkBoxes[i].setClickable(false);
            }
        }
        if(numCards == 0)
        {
            Toast.makeText(this.getActivity(), "No Destination Cards Left", Toast.LENGTH_SHORT).show();
        }

        String s = "There are " + GameModel.getInstance().getGameData().getDestinationCardsLeft() +
                   " Destination Cards left";
        TextView destinationCardsLeft = view.findViewById(R.id.DestinationDeckSize);
        destinationCardsLeft.setText(s);



        confirmCards.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                presenter.confirmDestinationCards();
            }
        });
        return view;
    }

    void toggleButton()
    {
        int numSelected = 0;
        for(CheckBox box : checkBoxes)
        {
            if(box.isChecked()) numSelected++;
        }
        if (numSelected >= 2)
        {
            confirmCards.setEnabled(true);
        } else if (numSelected >= 1 && !(GameModel.getInstance().getPlayer().getTurnState() instanceof InitialDestinationCardDraw))
        {
            confirmCards.setEnabled(true);
        }
        else
        {
            confirmCards.setEnabled(false);
        }
    }
}
