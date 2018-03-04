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
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.DataModels.EdgeGraph;
import common.DataModels.GameData.CityName;
import cs340.TicketClient.R;
import common.DataModels.City;
import common.DataModels.Edge;
import common.DataModels.TrainColor;

/**
 * Created by Kavika F.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
{
	private MapView mapView;
	private Bundle extras;
	private GoogleMap googleMap;
	private Map<String, City> cities = new HashMap<>();
	private EdgeGraph edges = new EdgeGraph();
	private Set<Polyline> lines = new HashSet<>();

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
		googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		LatLng southWest = new LatLng(24.52, -124.77);
		LatLng northEast = new LatLng( 49.01, -66.94);
		LatLngBounds unitedStates = new LatLngBounds(southWest, northEast);
		googleMap.setLatLngBoundsForCameraTarget(unitedStates);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unitedStates.getCenter(), 3));
		makeCityMarkers();
		drawMarkers();
		createEdges();
		drawEdges();
	}

	private void makeCityMarkers()
	{
		final LatLng LOS_ANGELES = new LatLng(34.052234, -118.243685);
		addCityToList(LOS_ANGELES, CityName.LOS_ANGELES);
		final LatLng NEW_YORK_CITY = new LatLng(40.712775, -74.005973);
		addCityToList(NEW_YORK_CITY, CityName.NEW_YORK_CITY);
		final LatLng DULUTH = new LatLng(46.786672, -92.100485);
		addCityToList(DULUTH, CityName.DULUTH);
		final LatLng HOUSTON = new LatLng(29.760427, -95.369803);
		addCityToList(HOUSTON, CityName.HOUSTON);
		final LatLng SAULT_ST_MARIE = new LatLng(46.495300, -84.345317);
		addCityToList(SAULT_ST_MARIE, CityName.SAULT_ST_MARIE);
		final LatLng NASHVILLE = new LatLng(36.162664, -86.781602);
		addCityToList(NASHVILLE, CityName.NASHVILLE);
		final LatLng ATLANTA = new LatLng(33.748995, -84.387982);
		addCityToList(ATLANTA, CityName.ATLANTA);
		final LatLng PORTLAND = new LatLng(45.523062, -122.676482);
		addCityToList(PORTLAND, CityName.PORTLAND);
		final LatLng VANCOUVER = new LatLng(49.282729, -123.120738);
		addCityToList(VANCOUVER, CityName.VANCOUVER);
		final LatLng MONTREAL = new LatLng(45.501689, -73.567256);
		addCityToList(MONTREAL, CityName.MONTREAL);
		final LatLng EL_PASO = new LatLng(31.761878, -106.485022);
		addCityToList(EL_PASO, CityName.EL_PASO);
		final LatLng TORONTO = new LatLng(43.653226, -79.383184);
		addCityToList(TORONTO, CityName.TORONTO);
		final LatLng MIAMI = new LatLng(25.761680, -80.191790);
		addCityToList(MIAMI, CityName.MIAMI);
		final LatLng PHOENIX = new LatLng(33.448377, -112.074037);
		addCityToList(PHOENIX, CityName.PHOENIX);
		final LatLng DALLAS = new LatLng(32.776664, -96.796988);
		addCityToList(DALLAS, CityName.DALLAS);
		final LatLng CALGARY = new LatLng(51.048615, -114.070846);
		addCityToList(CALGARY, CityName.CALGARY);
		final LatLng SALT_LAKE_CITY = new LatLng(40.760779, -111.891047);
		addCityToList(SALT_LAKE_CITY, CityName.SALT_LAKE_CITY);
		final LatLng WINNIPEG = new LatLng(49.895136, -97.138374);
		addCityToList(WINNIPEG, CityName.WINNIPEG);
		final LatLng LITTLE_ROCK = new LatLng(34.746481, -92.289595);
		addCityToList(LITTLE_ROCK, CityName.LITTLE_ROCK);
		final LatLng SAN_FRANCISCO = new LatLng(37.774929, -122.419416);
		addCityToList(SAN_FRANCISCO, CityName.SAN_FRANCISCO);
		final LatLng KANSAS_CITY = new LatLng(39.099727, -94.578567);
		addCityToList(KANSAS_CITY, CityName.KANSAS_CITY);
		final LatLng CHICAGO = new LatLng(41.878114, -87.629798);
		addCityToList(CHICAGO, CityName.KANSAS_CITY);
		final LatLng DENVER = new LatLng(39.739236, -104.990251);
		addCityToList(DENVER, CityName.DENVER);
		final LatLng PITTSBURGH = new LatLng(40.440625, -79.995886);
		addCityToList(PITTSBURGH, CityName.PITTSBURGH);
		final LatLng SANTA_FE = new LatLng(35.686975, -105.937799);
		addCityToList(SANTA_FE, CityName.SANTA_FE);
		final LatLng BOSTON = new LatLng(42.360082, -71.058880);
		addCityToList(BOSTON, CityName.BOSTON);
		final LatLng NEW_ORLEANS = new LatLng(29.951066, -90.071532);
		addCityToList(NEW_ORLEANS, CityName.NEW_ORLEANS);
		final LatLng SEATTLE = new LatLng(47.606209, -122.332071);
		addCityToList(SEATTLE, CityName.SEATTLE);
		final LatLng HELENA = new LatLng(46.588371, -112.024505);
		addCityToList(HELENA, CityName.HELENA);
		final LatLng OKLAHOMA_CITY = new LatLng(35.467560, -97.516428);
		addCityToList(OKLAHOMA_CITY, CityName.OKLAHOMA_CITY);
		final LatLng LAS_VEGAS = new LatLng(36.169941, -115.139830);
		addCityToList(LAS_VEGAS, CityName.LAS_VEGAS);
		final LatLng OMAHA = new LatLng(41.252363, -95.997988);
		addCityToList(OMAHA, CityName.OMAHA);
		final LatLng SAINT_LOUIS = new LatLng(38.627003, -90.199404);
		addCityToList(SAINT_LOUIS, CityName.SAINT_LOUIS);
		final LatLng WASHINGTON_DC = new LatLng(38.907192, -77.036871);
		addCityToList(WASHINGTON_DC, CityName.WASHINGTON_DC);
		final LatLng CHARLESTON = new LatLng(32.776475, -79.931051);
		addCityToList(CHARLESTON, CityName.CHARLESTON);
		final LatLng RALEIGH = new LatLng(35.779590, -78.638179);
		addCityToList(RALEIGH, CityName.RALEIGH);
	}

	/* Helper function to save lines when adding markers to list of all markers */
	private void addCityToList(LatLng coordinates, String title)
	{
		City city = new City(coordinates.latitude, coordinates.longitude, title);
		cities.put(title, city);
	}

	private void drawMarkers()
	{
		/* Able to store an arbitrary object in a marker with Marker.setTag(Object)
		* and get the object with Marker.getTag(). Can use in future if we want */
		Set<String> keys = cities.keySet();
		for (String key : keys)
		{
			City city = cities.get(key);
			googleMap.addMarker(new MarkerOptions().position( new LatLng(
					city.getLatitude(), city.getLongitude()))
			.title(city.getCityName()
			));
		}
	}

	/* Creates all the edges of the board game */
	private void createEdges()
	{
		City vancouver = cities.get(CityName.VANCOUVER);
		City seattle = cities.get(CityName.SEATTLE);
		City portland = cities.get(CityName.PORTLAND);
		City sanFrancisco = cities.get(CityName.SAN_FRANCISCO);
		City losAngeles = cities.get(CityName.LOS_ANGELES);
		City calgary = cities.get(CityName.CALGARY);
		City saltLakeCity = cities.get(CityName.SALT_LAKE_CITY);
		City lasVegas = cities.get(CityName.LAS_VEGAS);
		City phoenix = cities.get(CityName.PHOENIX);
		City helena = cities.get(CityName.HELENA);
		City denver = cities.get(CityName.DENVER);
		City santaFe = cities.get(CityName.SANTA_FE);
		City elPaso = cities.get(CityName.EL_PASO);
		City winnipeg = cities.get(CityName.WINNIPEG);
		City duluth = cities.get(CityName.DULUTH);
		City omaha = cities.get(CityName.OMAHA);
		City kansasCity = cities.get(CityName.KANSAS_CITY);
		City oklahomaCity = cities.get(CityName.OKLAHOMA_CITY);
		City dallas = cities.get(CityName.DALLAS);
		City houston = cities.get(CityName.HOUSTON);
		City newOrleans = cities.get(CityName.NEW_ORLEANS);
		City littleRock = cities.get(CityName.LITTLE_ROCK);
		City saintLouis = cities.get(CityName.SAINT_LOUIS);
		City chicago = cities.get(CityName.CHICAGO);
		City saultStMarie = cities.get(CityName.SAULT_ST_MARIE);
		City toronto = cities.get(CityName.TORONTO);
		City montreal = cities.get(CityName.MONTREAL);
		City boston = cities.get(CityName.BOSTON);
		City newYork = cities.get(CityName.NEW_YORK_CITY);
		City pittsburgh = cities.get(CityName.PITTSBURGH);
		City washingtonDC = cities.get(CityName.WASHINGTON_DC);
		City raleigh = cities.get(CityName.RALEIGH);
		City nashville = cities.get(CityName.NASHVILLE);
		City atlanta = cities.get(CityName.ATLANTA);
		City charleston = cities.get(CityName.CHARLESTON);
		City miami = cities.get(CityName.MIAMI);

		// Vancouver to Seattle
		addEdge(vancouver, seattle, TrainColor.GRAY, 1, true);

		// Seattle to Vancouver
		addEdge(seattle, vancouver, TrainColor.GRAY, 1, true);

		// Seattle to Portland
		addEdge(seattle, portland, TrainColor.GRAY, 1, true);

		// Portland to Seattle
		addEdge(portland, seattle, TrainColor.GRAY, 1, true);

		// Vancouver to Calgary
		addEdge(vancouver, calgary, TrainColor.GRAY, 3, false);

		// Seattle to Calgary
		addEdge(seattle, calgary, TrainColor.GRAY, 4, false);

		// Portland to San Francisco
		addEdge(portland, sanFrancisco, TrainColor.GREEN, 5, true);

		// San Francisco to Portland
		addEdge(sanFrancisco, portland, TrainColor.PINK, 5, true);

		// San Francisco to Los Angeles
		addEdge(sanFrancisco, losAngeles, TrainColor.YELLOW, 3, true);

		// Los Angeles to San Francisco
		addEdge(losAngeles, sanFrancisco, TrainColor.PINK, 3, true);

		// San Francisco to Salt Lake City
		addEdge(sanFrancisco, saltLakeCity, TrainColor.ORANGE, 5, true);

		// Salt Lake City to San Francisco
		addEdge(saltLakeCity, sanFrancisco, TrainColor.WHITE, 5, true);

		// Portland to Salt Lake City
		addEdge(portland, saltLakeCity, TrainColor.BLUE, 6, false);

		// Los Angeles to Las Vegas
		addEdge(losAngeles, lasVegas, TrainColor.GRAY, 2, false);

		// Los Angeles to Phoenix
		addEdge(losAngeles, phoenix, TrainColor.GRAY, 3, false);

		// Las Vegas to Salt Lake City
		addEdge(lasVegas, saltLakeCity, TrainColor.ORANGE, 3, false);

		// Calgary to Helena
		addEdge(calgary, helena, TrainColor.GRAY, 4, false);

		// Seattle to Helena
		addEdge(seattle, helena, TrainColor.YELLOW, 6, false);

		// Salt Lake City to Helena
		addEdge(saltLakeCity, helena, TrainColor.PINK, 3, false);

		// Los Angeles to El Paso
		addEdge(losAngeles, elPaso, TrainColor.BLACK, 6, false);

		// Phoenix to El Paso
		addEdge(phoenix, elPaso, TrainColor.GRAY, 3, false);

		// El Paso to Santa Fe
		addEdge(elPaso, santaFe, TrainColor.GRAY, 2, false);

		// Phoenix to Santa Fe
		addEdge(phoenix, santaFe, TrainColor.GRAY, 3, false);

		// Phoenix to Denver
		addEdge(phoenix, denver, TrainColor.WHITE, 5, false);

		// Santa Fe to Denver
		addEdge(santaFe, denver, TrainColor.GRAY, 2, false);

		// Salt Lake City to Denver
		addEdge(saltLakeCity, denver, TrainColor.RED, 3, true);

		// Denver to Salt Lake City
		addEdge(denver, saltLakeCity, TrainColor.YELLOW, 3, true);

		// Denver to Helena
		addEdge(denver, helena, TrainColor.GREEN, 4, false);

		// Calgary to Winnipeg
		addEdge(calgary, winnipeg, TrainColor.WHITE, 6, false);

		// Helena to Winnipeg
		addEdge(helena, winnipeg, TrainColor.BLUE, 4, false);

		// Winnipeg to Duluth
		addEdge(winnipeg, duluth, TrainColor.BLACK, 4, false);

		// Helena to Duluth
		addEdge(helena, duluth, TrainColor.ORANGE, 6, false);

		// Winnipeg to Saul St. Marie
		addEdge(winnipeg, saultStMarie, TrainColor.GRAY, 6, false);

		// Duluth to Saul St. Marie
		addEdge(duluth, saultStMarie, TrainColor.GRAY, 3, false);

		// Duluth to Omaha
		addEdge(duluth, omaha, TrainColor.GRAY, 2, true);

		// Omaha to Duluth
		addEdge(omaha, duluth, TrainColor.GRAY, 2, true);

		// Helena to Omaha
		addEdge(helena, omaha, TrainColor.RED, 5, false);

		// Denver to Omaha
		addEdge(denver, omaha, TrainColor.PINK, 4, false);

		// Omaha to Kansas City
		addEdge(omaha, kansasCity, TrainColor.GRAY, 1, true);

		// Kansas City to Omaha
		addEdge(kansasCity, omaha, TrainColor.GRAY, 1, true);

		// Denver to Kansas City
		addEdge(denver, kansasCity, TrainColor.BLACK, 4, true);

		// Kansas City to Denver
		addEdge(kansasCity, denver, TrainColor.ORANGE, 4, true);

		// Kansas City to Oklahoma City
		addEdge(kansasCity, oklahomaCity, TrainColor.GRAY, 2, true);

		// Oklahoma City to Kansas City
		addEdge(oklahomaCity, kansasCity, TrainColor.GRAY, 2, true);

		// Denver to Oklahoma City
		addEdge(denver, oklahomaCity, TrainColor.RED, 4, false);

		// Santa Fe to Oklahoma City
		addEdge(santaFe, oklahomaCity, TrainColor.BLUE, 3, false);

		// El Paso to Dallas
		addEdge(elPaso, dallas, TrainColor.RED, 4, false);

		// El Paso to Houston
		addEdge(elPaso, houston, TrainColor.GREEN, 6, false);

		// Dallas to Houston
		addEdge(dallas, houston, TrainColor.GRAY, 1, true);

		// Houston to Dallas
		addEdge(houston, dallas, TrainColor.GRAY, 1, true);

		// Oklahoma City to Dallas
		addEdge(oklahomaCity, dallas, TrainColor.GRAY, 2, true);

		// Dallas to Oklahoma City
		addEdge(dallas, oklahomaCity, TrainColor.GRAY, 2, true);

		// Oklahoma City to Little Rock
		addEdge(oklahomaCity, littleRock, TrainColor.GRAY, 2, false);

		// Dallas to Little Rock
		addEdge(dallas, littleRock, TrainColor.GRAY, 2, false);

		// Duluth to Chicago
		addEdge(duluth, chicago, TrainColor.RED, 3, false);

		// Omaha to Chicago
		addEdge(omaha, chicago, TrainColor.BLUE, 4, false);

		// Kansas City to Saint Louis
		addEdge(kansasCity, saintLouis, TrainColor.BLUE, 2, true);

		// Saint Louis to Kansas City
		addEdge(saintLouis, kansasCity, TrainColor.PINK, 2, true);

		// Saint Louis to Chicago
		addEdge(saintLouis, chicago, TrainColor.GREEN, 2, true);

		// Chicago to Saint Louis
		addEdge(chicago, saintLouis, TrainColor.WHITE, 2, true);

		// Saint Louis to Little Rock
		addEdge(saintLouis, littleRock, TrainColor.GRAY, 2, false);

		// Houston to New Orleans
		addEdge(houston, newOrleans, TrainColor.GRAY, 2, false);

		// Little Rock to New Orleans
		addEdge(littleRock, newOrleans, TrainColor.GREEN, 3, false);

		// Saul St. Marie to Toronto
		addEdge(saultStMarie, toronto, TrainColor.GRAY, 2, false);

		// Duluth to Toronto
		addEdge(duluth, toronto, TrainColor.PINK, 6, false);

		// Chicago to Toronto
		addEdge(chicago, toronto, TrainColor.WHITE, 4, false);

		// Toronto to Pittsburgh
		addEdge(toronto, pittsburgh, TrainColor.GRAY, 2, false);

		// Chicago to Pittsburgh
		addEdge(chicago, pittsburgh, TrainColor.ORANGE, 3, true);

		// Pittsburgh to Chicago
		addEdge(pittsburgh, chicago, TrainColor.BLACK, 3, true);

		// Saint Louis to Pittsburgh
		addEdge(saintLouis, pittsburgh, TrainColor.GREEN, 5, false);

		// Saint Louis to Nashville
		addEdge(saintLouis, nashville, TrainColor.GRAY, 2, false);

		// Little Rock to Nashville
		addEdge(littleRock, nashville, TrainColor.WHITE, 3, false);

		// Nashville to Pittsburgh
		addEdge(nashville, pittsburgh, TrainColor.YELLOW, 4, false);

		// Nashville to Atlanta
		addEdge(nashville, atlanta, TrainColor.GRAY, 1, false);

		// Atlanta to New Orleans
		addEdge(atlanta, newOrleans, TrainColor.YELLOW, 4, true);

		// New Orleans to Atlanta
		addEdge(newOrleans, atlanta, TrainColor.ORANGE, 4, true);

		// New Orleans to Miami
		addEdge(newOrleans, miami, TrainColor.RED, 6, false);

		// Atlanta to Miami
		addEdge(atlanta, miami, TrainColor.BLUE, 5, false);

		// Miami to Charleston
		addEdge(miami, charleston, TrainColor.PINK, 4, false);

		// Atlanta to Charleston
		addEdge(atlanta, charleston, TrainColor.GRAY, 2, false);

		// Nashville to Raleigh
		addEdge(nashville, raleigh, TrainColor.BLACK, 3, false);

		// Atlanta to Raleigh
		addEdge(atlanta, raleigh, TrainColor.GRAY, 2, true);

		// Raleigh to Atlanta
		addEdge(raleigh, atlanta, TrainColor.GRAY, 2, true);

		// Charleston to Raleigh
		addEdge(charleston, raleigh, TrainColor.GRAY, 2, false);

		// Raleigh to Washington D.C.
		addEdge(raleigh, washingtonDC, TrainColor.GRAY, 2, true);

		// Washington D.C. Raleigh
		addEdge(washingtonDC, raleigh, TrainColor.GRAY, 2, true);

		// Washington D.C. to Pittsburgh
		addEdge(washingtonDC, pittsburgh, TrainColor.GRAY, 2, false);

		// Pittsburgh to New York
		addEdge(pittsburgh, newYork, TrainColor.WHITE, 2, true);

		// New York to Pittsburgh
		addEdge(newYork, pittsburgh, TrainColor.GREEN, 2, true);

		// Pittsburgh to Raleigh
		addEdge(pittsburgh, raleigh, TrainColor.GRAY, 2, false);

		// New York to Washington D.C.
		addEdge(newYork, washingtonDC, TrainColor.ORANGE, 2, true);

		// Washington D.C. to New York
		addEdge(washingtonDC, newYork, TrainColor.BLACK, 2, true);

		// Boston to New York
		addEdge(boston, newYork, TrainColor.YELLOW, 2, true);

		// New York to Boston
		addEdge(newYork, boston, TrainColor.RED, 2, true);

		// Montreal to Boston
		addEdge(montreal, boston, TrainColor.GRAY, 2, true);

		// Boston to Montreal
		addEdge(boston, montreal, TrainColor.GRAY, 2, true);

		// Saul St. Marie to Montreal
		addEdge(saultStMarie, montreal, TrainColor.BLACK, 5, false);

		// Toronto to Montreal
		addEdge(toronto, montreal, TrainColor.GRAY, 3, false);

		// New York to Montreal
		addEdge(newYork, montreal, TrainColor.BLUE, 3, false);
	}

	private void addEdge(City city1, City city2, TrainColor color, int length, boolean isDoubleEdge)
	{
		Edge edge = new Edge(city1, city2, color, length, isDoubleEdge);
		edges.addEdge(city1, edge);
	}

	private void drawEdges()
	{

		for (Edge edge : edges.getAllEdges())
		{
			City city1 = edge.getFirstCity();
			LatLng city1coords = new LatLng(city1.getLatitude(), city1.getLongitude());
			City city2 = edge.getSecondCity();
			LatLng city2coords = new LatLng(city2.getLatitude(), city2.getLongitude());
			Polyline line;

			if (edge.isDoubleEdge())
			{
				if (edge.getColor() == TrainColor.GRAY)
				{
					Polyline underGray = showCurvedPolyline(city1coords, city2coords, TrainColor.LIGHT_GRAY);
					underGray.setWidth(20);
				}
				line = showCurvedPolyline(city1coords, city2coords, edge.getColor());
			}
			else
			{
				if (edge.getColor() == TrainColor.GRAY)
				{
					Polyline underGray = showStraightPolyline(city1, city2, TrainColor.LIGHT_GRAY);
					underGray.setWidth(20);
				}
				line = showStraightPolyline(city1, city2, edge.getColor());
			}

			line.setTag(edge.getCities());
			line.setClickable(true);
			List<PatternItem> pattern = Arrays.asList(new Dash(30), new Gap(20));
			line.setPattern(pattern);
			line.setWidth(20);
			lines.add(line);
		}
	}

	private int pickColor(TrainColor color)
	{
		switch(color)
		{
			case PINK: return getContext().getResources().getColor(R.color.colorEdgePink);
			case WHITE: return getContext().getResources().getColor(R.color.colorEdgeWhite);
			case BLUE: return getContext().getResources().getColor(R.color.colorEdgeBlue);
			case YELLOW: return getContext().getResources().getColor(R.color.colorEdgeYellow);
			case ORANGE: return getContext().getResources().getColor(R.color.colorEdgeOrange);
			case BLACK: return getContext().getResources().getColor(R.color.colorEdgeBlack);
			case RED: return getContext().getResources().getColor(R.color.colorEdgeRed);
			case GREEN: return getContext().getResources().getColor(R.color.colorEdgeGreen);
			case GRAY: return getContext().getResources().getColor(R.color.colorEdgeGray);
			case LIGHT_GRAY: return getContext().getResources().getColor(R.color.colorEdgeLightGray);
			default:
				System.out.println("No color found");
				return 0;
		}
	}

	private Polyline showStraightPolyline(City city1, City city2, TrainColor color)
	{
		LatLng city1coords = new LatLng(city1.getLatitude(), city1.getLongitude());
		LatLng city2coords = new LatLng(city2.getLatitude(), city2.getLongitude());
		PolylineOptions polylineOptions = new PolylineOptions().add(city1coords, city2coords)
				.color(pickColor(color));
		Polyline line = googleMap.addPolyline(polylineOptions);
		return line;
	}

	private Polyline showCurvedPolyline (LatLng p1, LatLng p2, TrainColor color) {

		int numPoints = 3;
		double a = SphericalUtil.computeDistanceBetween(p1, p2);
		a = a/2; // only need half the distance
		double b = 30000; // radius of ellipse
		double heading = SphericalUtil.computeHeading(p1, p2);
		double testHeading = SphericalUtil.computeHeading(p2, p1);
		if (testHeading > 0) testHeading -= 180;
		else if (testHeading < 0) testHeading += 180;
		double averageHeading = (testHeading + heading) / 2;
		//heading = Math.toRadians(heading);
		PolylineOptions options = new PolylineOptions();
		LatLng c = SphericalUtil.interpolate(p1, p2, 0.5);
		//googleMap.addMarker(new MarkerOptions().position(c).title("center point - interpolate")); // Just to see center. Debugging only

		for (int i = 0; i < numPoints; i++)
		{
			double time = (i * Math.PI) / 2;
			double x = a * Math.cos(time); //(a * Math.cos(time) * Math.cos(heading)) - (b * Math.sin(time) * Math.sin(heading));
			double y = b * Math.sin(time); //(a * Math.cos(time) * Math.sin(heading)) + (b * Math.sin(time) * Math.cos(heading));
			double distance = Math.sqrt(x*x - y*y);
			if (Double.isNaN(distance))
			{
				distance = Math.sqrt(y*y - x*x);
			}
			LatLng point = SphericalUtil.computeOffset(c, distance, averageHeading + Math.toDegrees(time));
			options.add(point);
		}
		Polyline line = googleMap.addPolyline(
				options.color(pickColor(color)).geodesic(false));
		return line;
	}
}
