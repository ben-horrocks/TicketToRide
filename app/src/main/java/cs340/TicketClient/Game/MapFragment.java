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

import cs340.TicketClient.R;
import cs340.TicketClient.client_common.City;
import cs340.TicketClient.client_common.Edge;
import cs340.TicketClient.client_common.EdgeColor;

/**
 * Created by Kavika F.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
{
	private MapView mapView;
	private Bundle extras;
	private GoogleMap googleMap;
	private Map<String, City> cities = new HashMap<>();
	private Set<Edge> edges = new HashSet<>();
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
		addCityToList(LOS_ANGELES, "Los Angeles");
		final LatLng NEW_YORK_CITY = new LatLng(40.712775, -74.005973);
		addCityToList(NEW_YORK_CITY, "New York City");
		final LatLng DULUTH = new LatLng(46.786672, -92.100485);
		addCityToList(DULUTH, "Duluth");
		final LatLng HOUSTON = new LatLng(29.760427, -95.369803);
		addCityToList(HOUSTON, "Houston");
		final LatLng SAULT_ST_MARIE = new LatLng(46.495300, -84.345317);
		addCityToList(SAULT_ST_MARIE, "Sault St. Marie");
		final LatLng NASHVILLE = new LatLng(36.162664, -86.781602);
		addCityToList(NASHVILLE, "Nashville");
		final LatLng ATLANTA = new LatLng(33.748995, -84.387982);
		addCityToList(ATLANTA, "Atlanta");
		final LatLng PORTLAND = new LatLng(45.523062, -122.676482);
		addCityToList(PORTLAND, "Portland");
		final LatLng VANCOUVER = new LatLng(49.282729, -123.120738);
		addCityToList(VANCOUVER, "Vancouver");
		final LatLng MONTREAL = new LatLng(45.501689, -73.567256);
		addCityToList(MONTREAL, "Montreal");
		final LatLng EL_PASO = new LatLng(31.761878, -106.485022);
		addCityToList(EL_PASO, "El Paso");
		final LatLng TORONTO = new LatLng(43.653226, -79.383184);
		addCityToList(TORONTO, "Toronto");
		final LatLng MIAMI = new LatLng(25.761680, -80.191790);
		addCityToList(MIAMI, "Miami");
		final LatLng PHOENIX = new LatLng(33.448377, -112.074037);
		addCityToList(PHOENIX, "Phoenix");
		final LatLng DALLAS = new LatLng(32.776664, -96.796988);
		addCityToList(DALLAS, "Dallas");
		final LatLng CALGARY = new LatLng(51.048615, -114.070846);
		addCityToList(CALGARY, "Calgary");
		final LatLng SALT_LAKE_CITY = new LatLng(40.760779, -111.891047);
		addCityToList(SALT_LAKE_CITY, "Salt Lake City");
		final LatLng WINNIPEG = new LatLng(49.895136, -97.138374);
		addCityToList(WINNIPEG, "Winnipeg");
		final LatLng LITTLE_ROCK = new LatLng(34.746481, -92.289595);
		addCityToList(LITTLE_ROCK, "Little Rock");
		final LatLng SAN_FRANCISCO = new LatLng(37.774929, -122.419416);
		addCityToList(SAN_FRANCISCO, "San Francisco");
		final LatLng KANSAS_CITY = new LatLng(39.099727, -94.578567);
		addCityToList(KANSAS_CITY, "Kansas City");
		final LatLng CHICAGO = new LatLng(41.878114, -87.629798);
		addCityToList(CHICAGO, "Chicago");
		final LatLng DENVER = new LatLng(39.739236, -104.990251);
		addCityToList(DENVER, "Denver");
		final LatLng PITTSBURGH = new LatLng(40.440625, -79.995886);
		addCityToList(PITTSBURGH, "Pittsburgh");
		final LatLng SANTA_FE = new LatLng(35.686975, -105.937799);
		addCityToList(SANTA_FE, "Santa Fe");
		final LatLng BOSTON = new LatLng(42.360082, -71.058880);
		addCityToList(BOSTON, "Boston");
		final LatLng NEW_ORLEANS = new LatLng(29.951066, -90.071532);
		addCityToList(NEW_ORLEANS, "New Orleans");
		final LatLng SEATTLE = new LatLng(47.606209, -122.332071);
		addCityToList(SEATTLE, "Seattle");
		final LatLng HELENA = new LatLng(46.588371, -112.024505);
		addCityToList(HELENA, "Helena");
		final LatLng OKLAHOMA_CITY = new LatLng(35.467560, -97.516428);
		addCityToList(OKLAHOMA_CITY, "Oklahoma City");
		final LatLng LAS_VEGAS = new LatLng(36.169941, -115.139830);
		addCityToList(LAS_VEGAS, "Las Vegas");
		final LatLng OMAHA = new LatLng(41.252363, -95.997988);
		addCityToList(OMAHA, "Omaha");
		final LatLng SAINT_LOUIS = new LatLng(38.627003, -90.199404);
		addCityToList(SAINT_LOUIS, "Saint Louis");
		final LatLng WASHINGTON_DC = new LatLng(38.907192, -77.036871);
		addCityToList(WASHINGTON_DC, "Washington D.C.");
		final LatLng CHARLESTON = new LatLng(32.776475, -79.931051);
		addCityToList(CHARLESTON, "Charleston");
		final LatLng RALEIGH = new LatLng(35.779590, -78.638179);
		addCityToList(RALEIGH, "Raleigh");
	}

	/* Helper function to save lines when adding markers to list of all markers */
	private void addCityToList(LatLng coordinates, String title)
	{
		MarkerOptions marker = new MarkerOptions().position(coordinates).title(title);
		City city = new City(marker, title);
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
			googleMap.addMarker(city.getCoordinates());
		}
	}

	/* Creates all the edges of the board game */
	private void createEdges()
	{
		City vancouver = cities.get("Vancouver");
		City seattle = cities.get("Seattle");
		City portland = cities.get("Portland");
		City sanFrancisco = cities.get("San Francisco");
		City losAngeles = cities.get("Los Angeles");
		City calgary = cities.get("Calgary");
		City saltLakeCity = cities.get("Salt Lake City");
		City lasVegas = cities.get("Las Vegas");
		City phoenix = cities.get("Phoenix");
		City helena = cities.get("Helena");
		City denver = cities.get("Denver");
		City santaFe = cities.get("Santa Fe");
		City elPaso = cities.get("El Paso");
		City winnipeg = cities.get("Winnipeg");
		City duluth = cities.get("Duluth");
		City omaha = cities.get("Omaha");
		City kansasCity = cities.get("Kansas City");
		City oklahomaCity = cities.get("Oklahoma City");
		City dallas = cities.get("Dallas");
		City houston = cities.get("Houston");
		City newOrleans = cities.get("New Orleans");
		City littleRock = cities.get("Little Rock");
		City saintLouis = cities.get("Saint Louis");
		City chicago = cities.get("Chicago");
		City saultStMarie = cities.get("Sault St. Marie");
		City toronto = cities.get("Toronto");
		City montreal = cities.get("Montreal");
		City boston = cities.get("Boston");
		City newYork = cities.get("New York City");
		City pittsburgh = cities.get("Pittsburgh");
		City washingtonDC = cities.get("Washington D.C.");
		City raleigh = cities.get("Raleigh");
		City nashville = cities.get("Nashville");
		City atlanta = cities.get("Atlanta");
		City charleston = cities.get("Charleston");
		City miami = cities.get("Miami");

		// Vancouver to Seattle
		addEdge(vancouver, seattle, EdgeColor.GRAY, 1, false);

		// Seattle to Vancouver
		addEdge(seattle, vancouver, EdgeColor.GRAY, 1, true);

		// Seattle to Portland
		addEdge(seattle, portland, EdgeColor.GRAY, 1, true);

		// Portland to Seattle
		addEdge(portland, seattle, EdgeColor.GRAY, 1, true);

		// Vancouver to Calgary
		addEdge(vancouver, calgary, EdgeColor.GRAY, 3, false);

		// Seattle to Calgary
		addEdge(seattle, calgary, EdgeColor.GRAY, 4, false);

		// Portland to San Francisco
		addEdge(portland, sanFrancisco, EdgeColor.GREEN, 5, true);

		// San Francisco to Portland
		addEdge(sanFrancisco, portland, EdgeColor.PINK, 5, true);

		// San Francisco to Los Angeles
		addEdge(sanFrancisco, losAngeles, EdgeColor.YELLOW, 3, true);

		// Los Angeles to San Francisco
		addEdge(losAngeles, sanFrancisco, EdgeColor.PINK, 3, true);

		// San Francisco to Salt Lake City
		addEdge(sanFrancisco, saltLakeCity, EdgeColor.ORANGE, 5, true);

		// Salt Lake City to San Francisco
		addEdge(saltLakeCity, sanFrancisco, EdgeColor.WHITE, 5, false);

		// Portland to Salt Lake City
		addEdge(portland, saltLakeCity, EdgeColor.BLUE, 6, false);

		// Los Angeles to Las Vegas
		addEdge(losAngeles, lasVegas, EdgeColor.GRAY, 2, false);

		// Los Angeles to Phoenix
		addEdge(losAngeles, phoenix, EdgeColor.GRAY, 3, false);

		// Las Vegas to Salt Lake City
		addEdge(lasVegas, saltLakeCity, EdgeColor.ORANGE, 3, false);

		// Calgary to Helena
		addEdge(calgary, helena, EdgeColor.GRAY, 4, false);

		// Seattle to Helena
		addEdge(seattle, helena, EdgeColor.YELLOW, 6, false);

		// Salt Lake City to Helena
		addEdge(saltLakeCity, helena, EdgeColor.PINK, 3, false);

		// Los Angeles to El Paso
		addEdge(losAngeles, elPaso, EdgeColor.BLACK, 6, false);

		// Phoenix to El Paso
		addEdge(phoenix, elPaso, EdgeColor.GRAY, 3, false);

		// El Paso to Santa Fe
		addEdge(elPaso, santaFe, EdgeColor.GRAY, 2, false);

		// Phoenix to Santa Fe
		addEdge(phoenix, sanFrancisco, EdgeColor.GRAY, 3, false);

		// Phoenix to Denver
		addEdge(phoenix, denver, EdgeColor.WHITE, 5, false);

		// Santa Fe to Denver
		addEdge(santaFe, denver, EdgeColor.GRAY, 2, false);

		// Salt Lake City to Denver
		addEdge(saltLakeCity, denver, EdgeColor.RED, 3, true);

		// Denver to Salt Lake City
		addEdge(denver, saltLakeCity, EdgeColor.YELLOW, 3, false);

		// Denver to Helena
		addEdge(denver, helena, EdgeColor.GREEN, 4, false);

		// Calgary to Winnipeg
		addEdge(calgary, winnipeg, EdgeColor.WHITE, 6, false);

		// Helena to Winnipeg
		addEdge(helena, winnipeg, EdgeColor.BLUE, 4, false);

		// Winnipeg to Duluth
		addEdge(winnipeg, duluth, EdgeColor.BLACK, 4, false);

		// Helena to Duluth
		addEdge(helena, duluth, EdgeColor.ORANGE, 6, false);

		// Winnipeg to Saul St. Marie
		addEdge(winnipeg, saultStMarie, EdgeColor.GRAY, 6, false);

		// Duluth to Saul St. Marie
		addEdge(duluth, saultStMarie, EdgeColor.GRAY, 3, false);

		// Duluth to Omaha
		addEdge(duluth, omaha, EdgeColor.GRAY, 2, true);

		// Omaha to Duluth
		addEdge(omaha, duluth, EdgeColor.GRAY, 2, true);

		// Helena to Omaha
		addEdge(helena, omaha, EdgeColor.RED, 5, false);

		// Denver to Omaha
		addEdge(denver, omaha, EdgeColor.PINK, 4, false);

		// Omaha to Kansas City
		addEdge(omaha, kansasCity, EdgeColor.GRAY, 1, true);

		// Kansas City to Omaha
		addEdge(kansasCity, omaha, EdgeColor.GRAY, 1, true);

		// Denver to Kansas City
		addEdge(denver, kansasCity, EdgeColor.BLACK, 4, true);

		// Kansas City to Denver
		addEdge(kansasCity, denver, EdgeColor.ORANGE, 4, false);

		// Kansas City to Oklahoma City
		addEdge(kansasCity, oklahomaCity, EdgeColor.GRAY, 2, true);

		// Oklahoma City to Kansas City
		addEdge(oklahomaCity, kansasCity, EdgeColor.GRAY, 2, true);

		// Denver to Oklahoma City
		addEdge(denver, oklahomaCity, EdgeColor.RED, 4, false);

		// Santa Fe to Oklahoma City
		addEdge(sanFrancisco, oklahomaCity, EdgeColor.BLUE, 3, false);

		// El Paso to Dallas
		addEdge(elPaso, dallas, EdgeColor.RED, 4, false);

		// El Paso to Houston
		addEdge(elPaso, houston, EdgeColor.GREEN, 6, false);

		// Dallas to Houston
		addEdge(dallas, houston, EdgeColor.GRAY, 1, true);

		// Houston to Dallas
		addEdge(houston, dallas, EdgeColor.GRAY, 1, true);

		// Oklahoma City to Dallas
		addEdge(oklahomaCity, dallas, EdgeColor.GRAY, 2, true);

		// Dallas to Oklahoma City
		addEdge(dallas, oklahomaCity, EdgeColor.GRAY, 2, true);

		// Oklahoma City to Little Rock
		addEdge(oklahomaCity, littleRock, EdgeColor.GRAY, 2, false);

		// Dallas to Little Rock
		addEdge(dallas, littleRock, EdgeColor.GRAY, 2, false);

		// Duluth to Chicago
		addEdge(duluth, chicago, EdgeColor.RED, 3, false);

		// Omaha to Chicago
		addEdge(omaha, chicago, EdgeColor.BLUE, 4, false);

		// Kansas City to Saint Louis
		addEdge(kansasCity, saintLouis, EdgeColor.BLUE, 2, true);

		// Saint Louis to Kansas City
		addEdge(saintLouis, kansasCity, EdgeColor.PINK, 2, false);

		// Saint Louis to Chicago
		addEdge(saintLouis, chicago, EdgeColor.GREEN, 2, true);

		// Chicago to Saint Louis
		addEdge(chicago, saintLouis, EdgeColor.WHITE, 2, true);

		// Saint Louis to Little Rock
		addEdge(saintLouis, littleRock, EdgeColor.GRAY, 2, false);

		// Houston to New Orleans
		addEdge(houston, newOrleans, EdgeColor.GRAY, 2, false);

		// Little Rock to New Orleans
		addEdge(littleRock, newOrleans, EdgeColor.GREEN, 3, false);

		// Saul St. Marie to Toronto
		addEdge(saultStMarie, toronto, EdgeColor.GRAY, 2, false);

		// Duluth to Toronto
		addEdge(duluth, toronto, EdgeColor.PINK, 6, false);

		// Chicago to Toronto
		addEdge(chicago, toronto, EdgeColor.WHITE, 4, false);

		// Toronto to Pittsburgh
		addEdge(toronto, pittsburgh, EdgeColor.GRAY, 2, false);

		// Chicago to Pittsburgh
		addEdge(chicago, pittsburgh, EdgeColor.ORANGE, 3, true);

		// Pittsburgh to Chicago
		addEdge(pittsburgh, chicago, EdgeColor.BLACK, 3, true);

		// Saint Louis to Pittsburgh
		addEdge(saintLouis, pittsburgh, EdgeColor.GREEN, 5, false);

		// Saint Louis to Nashville
		addEdge(saintLouis, nashville, EdgeColor.GRAY, 2, false);

		// Little Rock to Nashville
		addEdge(littleRock, nashville, EdgeColor.WHITE, 3, false);

		// Nashville to Pittsburgh
		addEdge(nashville, pittsburgh, EdgeColor.YELLOW, 4, false);

		// Nashville to Atlanta
		addEdge(nashville, atlanta, EdgeColor.GRAY, 1, false);

		// Atlanta to New Orleans
		addEdge(atlanta, newOrleans, EdgeColor.YELLOW, 4, true);

		// New Orleans to Atlanta
		addEdge(newOrleans, atlanta, EdgeColor.ORANGE, 4, true);

		// New Orleans to Miami
		addEdge(newOrleans, miami, EdgeColor.RED, 6, false);

		// Atlanta to Miami
		addEdge(atlanta, miami, EdgeColor.BLUE, 5, false);

		// Miami to Charleston
		addEdge(miami, charleston, EdgeColor.PINK, 4, false);

		// Atlanta to Charleston
		addEdge(atlanta, charleston, EdgeColor.GRAY, 2, false);

		// Nashville to Raleigh
		addEdge(nashville, raleigh, EdgeColor.BLACK, 3, false);

		// Atlanta to Raleigh
		addEdge(atlanta, raleigh, EdgeColor.GRAY, 2, true);

		// Raleigh to Atlanta
		addEdge(raleigh, atlanta, EdgeColor.GRAY, 2, true);

		// Charleston to Raleigh
		addEdge(charleston, raleigh, EdgeColor.GRAY, 2, false);

		// Raleigh to Washington D.C.
		addEdge(raleigh, washingtonDC, EdgeColor.GRAY, 2, true);

		// Washington D.C. Raleigh
		addEdge(washingtonDC, raleigh, EdgeColor.GRAY, 2, true);

		// Washington D.C. to Pittsburgh
		addEdge(washingtonDC, pittsburgh, EdgeColor.GRAY, 2, false);

		// Pittsburgh to New York
		addEdge(pittsburgh, newYork, EdgeColor.WHITE, 2, true);

		// New York to Pittsburgh
		addEdge(newYork, pittsburgh, EdgeColor.GREEN, 2, false);

		// New York to Washington D.C.
		addEdge(newYork, washingtonDC, EdgeColor.ORANGE, 2, true);

		// Washington D.C. to New York
		addEdge(washingtonDC, newYork, EdgeColor.BLACK, 2, true);

		// Boston to New York
		addEdge(boston, newYork, EdgeColor.YELLOW, 2, true);

		// New York to Boston
		addEdge(newYork, boston, EdgeColor.RED, 2, true);

		// Montreal to Boston
		addEdge(montreal, boston, EdgeColor.GRAY, 2, true);

		// Boston to Montreal
		addEdge(boston, montreal, EdgeColor.GRAY, 2, true);

		// Saul St. Marie to Montreal
		addEdge(saultStMarie, montreal, EdgeColor.BLACK, 5, false);

		// Toronto to Montreal
		addEdge(toronto, montreal, EdgeColor.GRAY, 3, false);

		// New York to Montreal
		addEdge(newYork, montreal, EdgeColor.BLUE, 3, false);
	}

	private void addEdge(City city1, City city2, EdgeColor color, int length, boolean isDoubleEdge)
	{
		Edge edge = new Edge(city1, city2, color, length, isDoubleEdge);
		edges.add(edge);
	}

	private void drawEdges()
	{
		double curvature = 0.15;
		for (Edge edge : edges)
		{
			City city1 = edge.getFirstCity();
			City city2 = edge.getSecondCity();
			Polyline line;
			/*
			if (edge.isDoubleEdge())
			{
				line = showCurvedPolyline(city1.getCoordinates().getPosition(),
						city2.getCoordinates().getPosition(), curvature, edge.getColor());
			}
			else
			{
				line = showStraightPolyline(city1, city2, edge.getColor());
			}
			*/
			line = showStraightPolyline(city1, city2, edge.getColor()); // just for now
			line.setTag(edge);
			lines.add(line);
		}
	}

	private int pickColor(EdgeColor color)
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
			default:
				System.out.println("No color found");
				return 0;
		}
	}

	private Polyline showStraightPolyline(City city1, City city2, EdgeColor color)
	{
		PolylineOptions polylineOptions = new PolylineOptions()
				.add(city1.getCoordinates().getPosition(), city2.getCoordinates().getPosition())
				.color(pickColor(color));
		Polyline line = googleMap.addPolyline(polylineOptions);
		return line;
	}

	private Polyline showCurvedPolyline (LatLng p1, LatLng p2, double curvature, EdgeColor color) {

		int numPoints = 10;
		double a = SphericalUtil.computeDistanceBetween(p1, p2);
		a = a/2; // only need half the distance
		double b = 75000; // 7.5km
		double heading = SphericalUtil.computeHeading(p1, p2);
		heading = Math.toRadians(heading);
		PolylineOptions options = new PolylineOptions();
		//options.add(p2);
		LatLng c = SphericalUtil.interpolate(p1, p2, 0.5);
		//googleMap.addMarker(new MarkerOptions().position(c).title("center point")); // Just to see center. Debugging only

		for (int i = 0; i <= numPoints; i++)
		{
			double time = (2 * i * Math.PI) / (numPoints);
			double x = (a * Math.cos(time) * Math.cos(heading)) - (b * Math.sin(time) * Math.sin(heading));//a * Math.cos(time);//
			double y = (a * Math.cos(time) * Math.sin(heading)) + (b * Math.sin(time) * Math.cos(heading));//b * Math.sin(time);//
			double distance = Math.sqrt(x*x - y*y);
			LatLng point = SphericalUtil.computeOffset(c, distance, Math.toDegrees(heading) + Math.toDegrees(time));
			options.add(point);
		}
		List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dash(30), new Gap(20));
		Polyline line = googleMap.addPolyline(
				options.width(10).color(pickColor(color)).pattern(pattern).geodesic(false));
		return line;
	}
}
