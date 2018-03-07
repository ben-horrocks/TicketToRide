package cs340.TicketClient.Game;

import android.content.Intent;
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

import common.DataModels.GameData.Player;
import common.DataModels.User;
import cs340.TicketClient.MenuFragments.ChatFragment;
import cs340.TicketClient.MenuFragments.HistoryFragment;
import cs340.TicketClient.R;

public class GameActivity extends AppCompatActivity
{

	private GoogleMap mMap;
	private User user;

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
				if (extras.get("user") instanceof User)
				{
					this.user = (User) extras.get("user");
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

		switch(item.getItemId()) {
			case R.id.chat_btn:
				Intent i = new Intent(this, ChatFragment.class);
				this.startActivity(i);
				break;
			case R.id.hist_btn:
				Intent j = new Intent(this, HistoryFragment.class);
				this.startActivity(j);
				break;
			case R.id.test_btn:
				//TODO: run tests here

		}

		return super.onOptionsItemSelected(item);
	}
}
