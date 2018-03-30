package cs340.TicketClient.card_fragments.hand_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import common.cards.DestinationCard;
import common.cards.TrainCard;
import cs340.TicketClient.R;


public class HandFragment extends Fragment
{

    RecyclerView destinationCardsRecyclerView;
    RecyclerView trainCardsRecyclerView;
    HandFragmentPresenter presenter;
    HandTrainCardAdapter trainAdapter;
    HandDestCardAdapter destCardAdapter;


    public HandFragment()
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
        View v = inflater.inflate(R.layout.fragment_hand, container, false);
        destinationCardsRecyclerView = v.findViewById(R.id.destinationCards);
        trainCardsRecyclerView = v.findViewById(R.id.trainCards);
        presenter = new HandFragmentPresenter(this);
        DestinationCard destinationCard[] = presenter.getPlayerDestinationCards().toArray();
        TrainCard trainCards[] = (TrainCard[]) presenter.getPlayerTrainCards().toArray();
        destinationCardsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        trainCardsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        destCardAdapter = new HandDestCardAdapter(getActivity(), destinationCard);
        trainAdapter = new HandTrainCardAdapter(getActivity(), trainCards);
        destinationCardsRecyclerView.setAdapter(destCardAdapter);
        trainCardsRecyclerView.setAdapter(trainAdapter);

        return v;
    }
}
