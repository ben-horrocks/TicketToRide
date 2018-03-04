package cs340.TicketClient.CardFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import common.DataModels.TrainCard;
import cs340.TicketClient.R;


public class HandFragment extends Fragment {

    RecyclerView destinationCardsRecyclerView;
    RecyclerView trainCardsRecyclerView;
    HandFragmentPresenter presenter;
    TrainCardHandAdapter trainCardHandAdapter;


    public HandFragment() {
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
        View v = inflater.inflate(R.layout.fragment_hand, container, false);
        destinationCardsRecyclerView = v.findViewById(R.id.destinationCards);
        trainCardsRecyclerView = v.findViewById(R.id.trainCards);
        presenter = new HandFragmentPresenter(this);
        //TODO construct recyclerView
		if (getArguments() != null)
		{
			//TrainCard[] trainCards =
			//trainCardHandAdapter = new TrainCardHandAdapter(this, )
		}


        return v;
    }

    //TODO create recyclerView adapters and binders
}
