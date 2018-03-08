package cs340.TicketClient.Game;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.Player;
import common.DataModels.GameData.PlayerColor;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.ScreenName;
import common.DataModels.TrainCard;
import common.DataModels.User;
import common.DataModels.Username;
import cs340.TicketClient.GameMenu.ChatFragment;
import cs340.TicketClient.GameMenu.HistoryFragment;
import cs340.TicketClient.R;

public class GameActivity extends AppCompatActivity
{

	private GoogleMap mMap;
	private User user;
	private GamePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		presenter = new GamePresenter(this);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			if (extras.get("packet") instanceof StartGamePacket)
			{
				StartGamePacket packet = (StartGamePacket) extras.get("packet");
				presenter.fillModel(packet);
			}
		}
		FragmentManager fm = this.getSupportFragmentManager();
		Fragment mapViewFragment = fm.findFragmentById(R.id.fragment_map);
		if (mapViewFragment == null)
		{
			mapViewFragment = new MapFragment();
			mapViewFragment.setArguments(extras);
			fm.beginTransaction().add(R.id.fragment_map, mapViewFragment).commit();
		}

		Button handButton = (Button) this.findViewById(R.id.hand_button);
		Button destinationCardButton = (Button) this.findViewById(R.id.draw_destination_button);
		Button drawTrainCardButton = (Button) this.findViewById(R.id.draw_trainCar_button);
		Button claimRouteButton = (Button) this.findViewById(R.id.claim_route_button);

		handButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
			}
		});

		destinationCardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

			}
		});

		drawTrainCardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

			}
		});

		claimRouteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment;

		switch(item.getItemId())
		{
			case R.id.chat_btn:
				fragment = new ChatFragment();
				//fm.beginTransaction().add(R.id.game_menu_fragment, fragment).commit();
				break;
			case R.id.hist_btn:
				fragment = new HistoryFragment();
				//fm.beginTransaction().add(R.id.game_menu_fragment, fragment).commit();
				break;
			case R.id.test_btn:
				presenter.test();

		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Create a larger toast for a longer period of time. Mainly used for testing.
	 * @param message The message for the toast to display.
	 */
	private void makeLargerToast(String message)
	{
		Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
		// Increase toast text size without creating custom toast
		ViewGroup group = (ViewGroup) toast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(25);
		toast.show();
	}

	/**
	 * Display the colors of the players and their userNames. Mainly for testing.
	 * @param userNames The list of userNames to display.
	 * @param colors The list of colors associated with each userName.
	 */
	void displayColors(List<Username> userNames, List<PlayerColor> colors)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < userNames.size(); i++)
		{
			String line = userNames.get(i).getName() + " has color " + colors.get(i).name() + "\n";
			sb.append(line);
		}
		makeLargerToast(sb.toString());
	}

	/**
	 * Display the order in which turns will be taken. Mainly for testing.
	 * @param players The players in a collection sorted by their queue order.
	 */
	void displayPlayerOrder(Player[] players)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Player Turn Order\n");
		for (int i = 0; i < players.length; i++)
		{
			int turn = i + 1;
			String line = turn + ": " + players[i].getUser().getStringUserName() + "\n";
			sb.append(line);
		}
		makeLargerToast(sb.toString());
	}

	/**
	 * Display the current player's hand of train cards. Mainly for testing.
	 * @param playerHand The list of train cards to display.
	 */
	void displayPlayerTrainCards(List<TrainCard> playerHand)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Player Hand\n");
		for (int i = 0; i < playerHand.size(); i++)
		{
			String line = playerHand.get(i).getType().name();
			if (i + 1 < playerHand.size())
			{
				line += ", ";
			}
			sb.append(line);
		}
		makeLargerToast(sb.toString());
	}

	/**
	 * Display the number of cards each opponent has. Mainly for testing.
	 * @param opponents The list of opponents. Will access their hand sizes.
	 */
	void displayOpponentHandSize(List<Opponent> opponents)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Opponent Hand Sizes\n");
		for (Opponent opponent : opponents)
		{
			String line = opponent.getUsername().getName() + ": " + opponent.getNumberHandCards() + "\n";
			sb.append(line);
		}
		makeLargerToast(sb.toString());
	}
}
