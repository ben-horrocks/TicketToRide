package cs340.TicketClient.card_fragments.destination_card_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import common.cards.HandDestinationCards;
import common.player_info.turn_state.InitialDestinationCardDraw;
import cs340.TicketClient.game.GameModel;
import cs340.TicketClient.R;

public class DestinationCardFragment extends Fragment
{

    TextView card1View;
    TextView card2View;
    TextView card3View;
    CheckBox card1Check;
    CheckBox card2Check;
    CheckBox card3Check;
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
        card1View = view.findViewById(R.id.card1View);
        card2View = view.findViewById(R.id.card2View);
        card3View = view.findViewById(R.id.card3View);
        card1Check = view.findViewById(R.id.card1);
        card2Check = view.findViewById(R.id.card2);
        card3Check = view.findViewById(R.id.card3);
        confirmCards = view.findViewById(R.id.confirmButton);
        toggleButton();
        presenter = new DestinationCardFragmentPresenter(this);

        Bundle extras = getArguments();
        if (extras.get("cards") != null)
        {
            dCards = (HandDestinationCards) extras.get("cards");
        }

        card1Check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleButton();
            }
        });

        card2Check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleButton();
            }
        });

        card3Check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleButton();
            }
        });

        //dCards = presenter.getDCards();
        if (dCards.size() == 3) {
            String path1 =
                    dCards.get(0).getCity1() + " -> " + dCards.get(0).getCity2() + "\n Points: " +
                            dCards.get(0).getPointValue();
            String path2 =
                    dCards.get(1).getCity1() + " -> " + dCards.get(1).getCity2() + "\n Points: " +
                            dCards.get(1).getPointValue();
            String path3 =
                    dCards.get(2).getCity1() + " -> " + dCards.get(2).getCity2() + "\n Points: " +
                            dCards.get(2).getPointValue();
            card1View.setText(path1);
            card2View.setText(path2);
            card3View.setText(path3);
        }
        else if (dCards.size() == 2)
        {
            String path1 =
                    dCards.get(0).getCity1() + " -> " + dCards.get(0).getCity2() + "\n Points: " +
                            dCards.get(0).getPointValue();
            String path2 =
                    dCards.get(1).getCity1() + " -> " + dCards.get(1).getCity2() + "\n Points: " +
                            dCards.get(1).getPointValue();
            card1View.setText(path1);
            card2View.setText(path2);
            card3View.setText("");
            card3Check.setClickable(false);
        }
        else if (dCards.size() == 1)
        {
            String path1 =
                    dCards.get(0).getCity1() + " -> " + dCards.get(0).getCity2() + "\n Points: " +
                            dCards.get(0).getPointValue();
            card1View.setText(path1);
            card2View.setText("");
            card2Check.setClickable(false);
            card3View.setText("");
            card3Check.setClickable(false);
        }
        else
        {
            card1View.setText("");
            card1Check.setClickable(false);
            card2View.setText("");
            card2Check.setClickable(false);
            card3View.setText("");
            card3Check.setClickable(false);
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
        if (card1Check.isChecked())
        {
            numSelected++;
        }
        if (card2Check.isChecked())
        {
            numSelected++;
        }
        if (card3Check.isChecked())
        {
            numSelected++;
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
