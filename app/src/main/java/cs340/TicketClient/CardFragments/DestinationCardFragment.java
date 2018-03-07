package cs340.TicketClient.CardFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import common.DataModels.DestinationCard;
import cs340.TicketClient.R;

public class DestinationCardFragment extends Fragment {

    TextView card1View;
    TextView card2View;
    TextView card3View;
    CheckBox card1Check;
    CheckBox card2Check;
    CheckBox card3Check;
    Button confirmCards;
    ArrayList<DestinationCard> dCards;
    DestinationCardFragmentPresenter presenter;

    public DestinationCardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_destination_card, container, false);
        card1View = view.findViewById(R.id.card1View);
        card2View = view.findViewById(R.id.card2View);
        card3View = view.findViewById(R.id.card3View);
        card1Check = view.findViewById(R.id.card1);
        card2Check = view.findViewById(R.id.card2);
        card3Check = view.findViewById(R.id.card3);
        confirmCards = view.findViewById(R.id.confirmButton);
        toggleButton();
        presenter = new DestinationCardFragmentPresenter(this);

        dCards = presenter.getDCards();
        String path1 = dCards.get(0).getCity1() + " -> " + dCards.get(0).getCity2()+ "\n Points: " + dCards.get(0).getPointValue();
        String path2 = dCards.get(1).getCity1()+ " -> " + dCards.get(1).getCity2()+ "\n Points: " + dCards.get(1).getPointValue();
        String path3 = dCards.get(2).getCity1()+ " -> " + dCards.get(2).getCity2()+ "\n Points: " + dCards.get(2).getPointValue();

        card1View.setText(path1);
        card2View.setText(path2);
        card3View.setText(path3);

        card1Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton();
            }
        });

        card2Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton();
            }
        });

        card3Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton();
            }
        });

        confirmCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.confirmDestinationCards();
                //TODO close fragment
            }
        });
        return view;
    }

    void toggleButton()
    {
        int numSelected = 0;
        if (card1Check.isChecked()) numSelected++;
        if (card2Check.isChecked()) numSelected++;
        if(card3Check.isChecked()) numSelected++;
        if(numSelected >=2 ) confirmCards.setEnabled(true);
        else confirmCards.setEnabled(false);
    }

}
