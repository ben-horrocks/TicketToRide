package cs340.TicketClient.CardFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import common.DataModels.DestinationCard;
import common.DataModels.TrainCard;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.R;


public class HandFragment extends Fragment {

    RecyclerView destinationCardsRecyclerView;
    RecyclerView trainCardsRecyclerView;
    HandFragmentPresenter presenter;
    HandTrainCardAdapter trainAdapter;
    HandDestCardAdapter destCardAdapter;


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
        TrainCard trainCards [] = (TrainCard[]) GameModel.getInstance().getGameData().getPlayer().getHand().toArray();
        DestinationCard destinationCard[] = (DestinationCard[]) GameModel.getInstance().getGameData().getPlayer().getDestinationCards().toArray();
        destinationCardsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        trainCardsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        destCardAdapter = new HandDestCardAdapter(getActivity(), destinationCard);
        trainAdapter = new HandTrainCardAdapter(getActivity(), trainCards);
        destinationCardsRecyclerView.setAdapter(destCardAdapter);
        trainCardsRecyclerView.setAdapter(trainAdapter);

        return v;
    }
}
