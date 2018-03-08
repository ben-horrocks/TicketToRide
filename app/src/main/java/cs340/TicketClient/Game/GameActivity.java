package cs340.TicketClient.Game;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import common.DataModels.DestinationCard;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.User;
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
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		Bundle extras = getIntent().getExtras();

		FragmentManager fm = this.getSupportFragmentManager();
		Fragment mapViewFragment = fm.findFragmentById(R.id.fragment_map);
		if (mapViewFragment == null)
		{
			mapViewFragment = new MapFragment();
			if (extras != null)
			{
				if (extras.get("packet") instanceof StartGamePacket)
				{
					StartGamePacket packet = (StartGamePacket) extras.get("packet");
					GameModel.getInstance().setGameData(packet.getClientGameData());
					GameModel.getInstance().setInitialDCards((ArrayList< DestinationCard>)packet.getInitialDestinationCards());
				}
				mapViewFragment.setArguments(extras);
			}
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

		switch(item.getItemId()) {
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
}
