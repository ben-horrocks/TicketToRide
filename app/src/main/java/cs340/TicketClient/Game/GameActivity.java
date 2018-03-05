package cs340.TicketClient.Game;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

import common.DataModels.GameData.Player;
import common.DataModels.User;
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
}
