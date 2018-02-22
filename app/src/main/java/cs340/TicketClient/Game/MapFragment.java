package cs340.TicketClient.Game;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cs340.TicketClient.R;

/**
 * Created by Kavika F.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
{
	private MapView mapView;
	private Bundle extras;
	private GoogleMap googleMap;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_game, container, false);
		mapView = (MapView) rootView.findViewById(R.id.map_view);
		mapView.onCreate(savedInstanceState);

		extras = getArguments();

		mapView.onResume();
		MapsInitializer.initialize(getActivity().getApplicationContext());
		mapView.getMapAsync(this);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		// TODO: implement hamburger menu
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: implement drawer things
		return true;
	}

	@Override
	public void onMapReady(GoogleMap mMap)
	{
		googleMap = mMap;
		googleMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("Hello World"));
	}
}
