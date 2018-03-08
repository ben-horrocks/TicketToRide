package cs340.TicketClient.GameMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cs340.TicketClient.R;

public class PlayerFragment extends Fragment
{
	private PlayerPresenter presenter;
	private TextView mPlayerName;
	private TextView mPlayerColor;
	private TextView mPlayerScore;
	private TextView mTurnQueue;

	@Override
	public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
		View v = inflater.inflate(R.layout.fragment_player_info, container, false);

		presenter = PlayerPresenter.getSINGLETON(this);

		v.setBackgroundColor(Color.WHITE);
		mPlayerName = (TextView) v.findViewById(R.id.drawer_player_name);
		mPlayerName.setText(presenter.getPlayerName());

		mPlayerColor = (TextView) v.findViewById(R.id.drawer_player_color);
		mPlayerColor.setText(presenter.getPlayerColor());

		mPlayerScore = (TextView) v.findViewById(R.id.drawer_player_points);
		mPlayerScore.setText(presenter.getPlayerPoints());

		mTurnQueue = (TextView) v.findViewById(R.id.drawer_player_turnQueue);
		mTurnQueue.setText(presenter.getTurnQueue());

		return v;
	}
}
