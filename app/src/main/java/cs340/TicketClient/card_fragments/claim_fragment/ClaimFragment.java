//package cs340.TicketClient.card_fragments.claim_fragment;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.*;
//import android.widget.Button;
//
//import common.cards.DestinationCard;
//import common.cards.TrainCard;
//import cs340.TicketClient.R;
//import cs340.TicketClient.game.GameModel;
//import android.support.v4.app.Fragment;
//import android.widget.Toast;
//
//import java.util.List;
//
//public class ClaimFragment extends Fragment {
//
//    RecyclerView trainCardsRecyclerView;
//    ClaimPresenter presenter;
//    ClaimTrainCardAdapter trainAdapter;
//    Button claimRoute;
//    GameModel model;
//
//    public ClaimFragment() { model = GameModel.getInstance(); }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_claim_edge, container, false);
//        claimRoute = v.findViewById(R.id.submit_claim_button);
//        trainCardsRecyclerView = v.findViewById(R.id.ClaimTrainCards);
//        presenter = new ClaimPresenter(this);
//        List<TrainCard> trainCards = presenter.getPlayerTrainCards().getTrainCards();
//        trainCardsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        trainAdapter = new ClaimTrainCardAdapter();
//        trainCardsRecyclerView.setAdapter(trainAdapter);
//        for(TrainCard card : trainCards)
//        {
//            trainAdapter.add(card);
//        }
//		model.getQueuedCards().clear();
//        claimRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (model.canClaimSelectedEdge()) {
//                    presenter.sendClaimRequest();
//                }
//                else
//				{
//					Toast.makeText(getContext(), "Can't Claim Edge", Toast.LENGTH_SHORT).show();
//				}
//            }
//        });
//
//        return v;
//
//    }
//}
//
