package cs340.TicketClient.GameMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cs340.TicketClient.R;

public class PlayerFragment extends Fragment
{
	private PlayerPresenter presenter;
	private TextView mPlayerName;
	private TextView mPlayerColor;
	private TextView mPlayerScore;
	private TextView mPlayerTrainCards;
	private TextView mPlayerDestinationCards;
	private TextView mTurnQueue;
	private TextView mOpponentInfo;
	private Button mExitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
		View v = inflater.inflate(R.layout.fragment_player_info, container, false);

		presenter = PlayerPresenter.getSINGLETON(this);

		v.setBackgroundColor(Color.WHITE);
		mPlayerName = (TextView) v.findViewById(R.id.drawer_player_name);
		String text = "Name: " + presenter.getPlayerName();
		mPlayerName.setText(text);

		mPlayerColor = (TextView) v.findViewById(R.id.drawer_player_color);
		text = "Color: " + presenter.getPlayerColor();
		mPlayerColor.setText(text);

		mPlayerScore = (TextView) v.findViewById(R.id.drawer_player_points);
		text = "Score: " + presenter.getPlayerPoints();
		mPlayerScore.setText(text);

		mPlayerTrainCards = (TextView) v.findViewById(R.id.drawer_player_train_cards);
		text = presenter.getTrainCards();
		mPlayerTrainCards.setText(text);

		mPlayerDestinationCards = (TextView) v.findViewById(R.id.drawer_player_destination_cards);
		text = presenter.getDestinationCards();
		mPlayerDestinationCards.setText(text);

		mTurnQueue = (TextView) v.findViewById(R.id.drawer_player_turnQueue);
		text = "Turn Queue: \n" + presenter.getTurnQueue();
		mTurnQueue.setText(text);

		mOpponentInfo = (TextView) v.findViewById(R.id.drawer_player_opponent_info);
		text = "Opponent Information: \n" + presenter.getOpponentInfo();
		mOpponentInfo.setText(text);

		mExitButton = (Button) v.findViewById(R.id.drawer_player_exit_button);
		mExitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fm.popBackStack();
			}
		});



		return v;
	}
}
