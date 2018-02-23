package cs340.TicketClient.Game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import cs340.TicketClient.R;

/**
 * Created by Kavika F.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
{
	private MapView mapView;
	private Bundle extras;
	private GoogleMap googleMap;
	private List<MarkerOptions> cityMarkers = new ArrayList<>();

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
		LatLng southWest = new LatLng(24.52, -124.77);
		LatLng northEast = new LatLng( 49.01, -66.94);
		LatLngBounds unitedStates = new LatLngBounds(southWest, northEast);
		googleMap.setLatLngBoundsForCameraTarget(unitedStates);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unitedStates.getCenter(), 3));
		makeCityMarkers();
	}

	private void makeCityMarkers()
	{
		final LatLng LOS_ANGELES = new LatLng(34.052234, -118.243685);
		addToMarkerList(LOS_ANGELES, "Los Angeles");
		final LatLng NEW_YORK_CITY = new LatLng(40.712775, -74.005973);
		addToMarkerList(NEW_YORK_CITY, "New York City");
		final LatLng DULUTH = new LatLng(46.786672, -92.100485);
		addToMarkerList(DULUTH, "Duluth");
		final LatLng HOUSTON = new LatLng(29.760427, -95.369803);
		addToMarkerList(HOUSTON, "Houston");
		final LatLng SAULT_ST_MARIE = new LatLng(46.495300, -84.345317);
		addToMarkerList(SAULT_ST_MARIE, "Sault St. Marie");
		final LatLng NASHVILLE = new LatLng(36.162664, -86.781602);
		addToMarkerList(NASHVILLE, "Nashville");
		final LatLng ATLANTA = new LatLng(33.748995, -84.387982);
		addToMarkerList(ATLANTA, "Atlanta");
		final LatLng PORTLAND = new LatLng(45.523062, -122.676482);
		addToMarkerList(PORTLAND, "Portland");
		final LatLng VANCOUVER = new LatLng(49.282729, -123.120738);
		addToMarkerList(VANCOUVER, "Vancouver");
		final LatLng MONTREAL = new LatLng(45.501689, -73.567256);
		addToMarkerList(MONTREAL, "Montreal");
		final LatLng EL_PASO = new LatLng(31.761878, -106.485022);
		addToMarkerList(EL_PASO, "El Paso");
		final LatLng TORONTO = new LatLng(43.653226, -79.383184);
		addToMarkerList(TORONTO, "Toronto");
		final LatLng MIAMI = new LatLng(25.761680, -80.191790);
		addToMarkerList(MIAMI, "Miami");
		final LatLng PHOENIX = new LatLng(33.448377, -112.074037);
		addToMarkerList(PHOENIX, "Phoenix");
		final LatLng DALLAS = new LatLng(32.776664, -96.796988);
		addToMarkerList(DALLAS, "Dallas");
		final LatLng CALGARY = new LatLng(51.048615, -114.070846);
		addToMarkerList(CALGARY, "Calgary");
		final LatLng SALT_LAKE_CITY = new LatLng(40.760779, -111.891047);
		addToMarkerList(SALT_LAKE_CITY, "Salt Lake City");
		final LatLng WINNIPEG = new LatLng(49.895136, -97.138374);
		addToMarkerList(WINNIPEG, "Winnipeg");
		final LatLng LITTLE_ROCK = new LatLng(34.746481, -92.289595);
		addToMarkerList(LITTLE_ROCK, "Little Rock");
		final LatLng SAN_FRANCISCO = new LatLng(37.774929, -122.419416);
		addToMarkerList(SAN_FRANCISCO, "San Francisco");
		final LatLng KANSAS_CITY = new LatLng(39.099727, -94.578567);
		addToMarkerList(KANSAS_CITY, "Kansas City");
		final LatLng CHICAGO = new LatLng(41.878114, -87.629798);
		addToMarkerList(CHICAGO, "Chicago");
		final LatLng DENVER = new LatLng(39.739236, -104.990251);
		addToMarkerList(DENVER, "Denver");
		final LatLng PITTSBURGH = new LatLng(40.440625, -79.995886);
		addToMarkerList(PITTSBURGH, "Pittsburgh");
		final LatLng SANTA_FE = new LatLng(35.686975, -105.937799);
		addToMarkerList(SANTA_FE, "Santa Fe");
		final LatLng BOSTON = new LatLng(42.360082, -71.058880);
		addToMarkerList(BOSTON, "Boston");
		final LatLng NEW_ORLEANS = new LatLng(29.951066, -90.071532);
		addToMarkerList(NEW_ORLEANS, "New Orleans");
		final LatLng SEATTLE = new LatLng(47.606209, -122.332071);
		addToMarkerList(SEATTLE, "Seattle");
		final LatLng HELENA = new LatLng(46.588371, -112.024505);
		addToMarkerList(HELENA, "Helena");
		final LatLng OKLAHOMA_CITY = new LatLng(35.467560, -97.516428);
		addToMarkerList(OKLAHOMA_CITY, "Oklahoma City");
		final LatLng LAS_VEGAS = new LatLng(36.169941, -115.139830);
		addToMarkerList(LAS_VEGAS, "Las Vegas");
		final LatLng OMAHA = new LatLng(41.252363, -95.997988);
		addToMarkerList(OMAHA, "Omaha");
		final LatLng SAINT_LOUIS = new LatLng(38.627003, -90.199404);
		addToMarkerList(SAINT_LOUIS, "Saint Louis");
		final LatLng WASHINGTON_DC = new LatLng(38.907192, -77.036871);
		addToMarkerList(WASHINGTON_DC, "Washington D.C.");
		final LatLng CHARLESTON = new LatLng(32.776475, -79.931051);
		addToMarkerList(CHARLESTON, "Charleston");
		final LatLng RALEIGH = new LatLng(35.779590, -78.638179);
		addToMarkerList(RALEIGH, "Raleigh");

		/* Able to store an arbitrary object in a marker with Marker.setTag(Object)
		* and get the object with Marker.getTag(). Can use in future if we want */
		for (MarkerOptions marker : cityMarkers)
		{
			googleMap.addMarker(marker);
		}
	}

	/* Helper function to save lines when adding markers to list of all markers */
	private void addToMarkerList(LatLng coordinates, String title)
	{
		MarkerOptions city = new MarkerOptions().position(coordinates).title(title);
		cityMarkers.add(city);
	}
}
