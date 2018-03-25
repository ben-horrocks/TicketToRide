package cs340.TicketClient.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.TextView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.ui.IconGenerator;

import java.util.*;

import common.map.CityName;
import common.map.City;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.PlayerColor;
import common.IColor;
import common.cards.TrainColor;
import cs340.TicketClient.R;

import static common.cards.TrainColor.GRAY;

/**
 * Created by Kavika F.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
{
    private MapView mapView;
    private Bundle extras;
    private GoogleMap googleMap;
    private Map<String, City> cities = new HashMap<>();
    private EdgeGraph edges = GameModel.getInstance().getGameData().getGameboard();
    private Set<Polyline> lines = new HashSet<>();
    private Map<Marker, Edge> onClickMap;

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

        // Calls onMapReady
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
        LatLng northEast = new LatLng(49.01, -66.94);
        LatLngBounds unitedStates = new LatLngBounds(southWest, northEast);
        googleMap.setLatLngBoundsForCameraTarget(unitedStates);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unitedStates.getCenter(), 3));
        makeCityMarkers();
        drawMarkers();
        //createEdges();
        drawEdges();
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Override
            public View getInfoWindow(Marker marker)
            {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker)
            {
                View v = getLayoutInflater().inflate(R.layout.edge_info, null);

                TextView edgeTitle = (TextView) v.findViewById(R.id.edge_title);
                edgeTitle.setText(marker.getTitle());
                TextView edgeLength = (TextView) v.findViewById(R.id.edge_length);
                TextView edgeOwner = (TextView) v.findViewById(R.id.edge_owner);
                if (marker.getSnippet() != null)
                {
                    Scanner sc = new Scanner(marker.getSnippet());
                    sc.nextLine();
                    String text = sc.nextLine();
                    edgeLength.setText(text);
                    text = sc.nextLine();
                    edgeOwner.setText(text);
                } else
                {
                    edgeLength.setText("Owned by: ");
                    edgeOwner.setText("\'Murrica");
                }
                return v;
            }
        });
        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener()
        {
            @Override
            public void onPolylineClick(Polyline polyline)
            {
                if (polyline.getTag() instanceof Marker)
                {
                    Marker marker = (Marker) polyline.getTag();
                    String snippet = marker.getSnippet();
                    Scanner scan = new Scanner(snippet);
                    String title = scan.nextLine();
                    String length = scan.nextLine();
                    String owner = scan.nextLine();
                    String id = scan.nextLine();
                    marker.showInfoWindow();
                }
            }
        });
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
            googleMap.addMarker(new MarkerOptions().position(
                    new LatLng(city.getLatitude(), city.getLongitude())).title(city.getCityName()));
        }
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
            float lineWidth = 20;

            IColor color;
            if (edge.getOwnerColor() != null)
            {
                // Edge is owned. Use solid pattern and player color.
                color = edge.getOwnerColor();
                if (edge.isDoubleEdge())
                {
                    line = showCurvedPolyline(city1coords, city2coords, color, edge);
                } else
                {
                    line = showStraightPolyline(city1, city2, color, edge);
                }
                line.setClickable(true);
                line.setWidth(lineWidth);
                lines.add(line);
            } else
            {
                // Edge unowned. Use dashed pattern and edge color.
                color = edge.getColor();
                if (edge.isDoubleEdge())
                {
                    if (edge.getColor() == GRAY)
                    {
                        Polyline underGray =
                                showCurvedPolyline(city1coords, city2coords, TrainColor.LIGHT_GRAY,
                                                   edge);
                        underGray.setWidth(lineWidth);
                    }
                    line = showCurvedPolyline(city1coords, city2coords, color, edge);
                } else
                {
                    if (edge.getColor() == GRAY)
                    {
                        Polyline underGray =
                                showStraightPolyline(city1, city2, TrainColor.LIGHT_GRAY, edge);
                        underGray.setWidth(lineWidth);
                    }

                    line = showStraightPolyline(city1, city2, color, edge);
                }
                List<PatternItem> pattern = Arrays.asList(new Dash(30), new Gap(20));
                line.setPattern(pattern);
                line.setClickable(true);
                line.setWidth(lineWidth);
                lines.add(line);
            }
        }
    }

    private int pickColor(IColor color)
    {
        if (color instanceof TrainColor)
        {
            TrainColor trainColor = (TrainColor) color;
            switch (trainColor)
            {
                case PINK:
                    return getContext().getResources().getColor(R.color.colorEdgePink);
                case WHITE:
                    return getContext().getResources().getColor(R.color.colorEdgeWhite);
                case BLUE:
                    return getContext().getResources().getColor(R.color.colorEdgeBlue);
                case YELLOW:
                    return getContext().getResources().getColor(R.color.colorEdgeYellow);
                case ORANGE:
                    return getContext().getResources().getColor(R.color.colorEdgeOrange);
                case BLACK:
                    return getContext().getResources().getColor(R.color.colorEdgeBlack);
                case RED:
                    return getContext().getResources().getColor(R.color.colorEdgeRed);
                case GREEN:
                    return getContext().getResources().getColor(R.color.colorEdgeGreen);
                case GRAY:
                    return getContext().getResources().getColor(R.color.colorEdgeGray);
                case LIGHT_GRAY:
                    return getContext().getResources().getColor(R.color.colorEdgeLightGray);
                default:
                    System.out.println("No Edge Color Found");
                    return 0;
            }
        } else
        {
            PlayerColor playerColor = (PlayerColor) color;
            switch (playerColor)
            {
                case BLACK:
                    return getContext().getResources().getColor(R.color.colorEdgeBlack);
                case BLUE:
                    return getContext().getResources().getColor(R.color.colorEdgeBlue);
                case GREEN:
                    return getContext().getResources().getColor(R.color.colorEdgeGreen);
                case RED:
                    return getContext().getResources().getColor(R.color.colorEdgeRed);
                case YELLOW:
                    return getContext().getResources().getColor(R.color.colorEdgeYellow);
                default:
                    System.out.println("No Player Color Found");
                    return 0;
            }
        }
    }

    private Polyline showStraightPolyline(City city1, City city2, IColor color, Edge edge)
    {
        LatLng city1coords = new LatLng(city1.getLatitude(), city1.getLongitude());
        LatLng city2coords = new LatLng(city2.getLatitude(), city2.getLongitude());
        PolylineOptions polylineOptions =
                new PolylineOptions().add(city1coords, city2coords).color(pickColor(color));
        Polyline line = googleMap.addPolyline(polylineOptions);
        LatLng middle = SphericalUtil.interpolate(city1coords, city2coords, 0.5);
        Marker marker = addPolylineTag(edge, middle);
        line.setTag(marker);
        return line;
    }

    private Polyline showCurvedPolyline(LatLng p1, LatLng p2, IColor color, Edge edge)
    {

        int numPoints = 3;
        double a = SphericalUtil.computeDistanceBetween(p1, p2);
        a = a / 2; // only need half the distance
        double b = 30000; // radius of ellipse
        double heading = SphericalUtil.computeHeading(p1, p2);
        double testHeading = SphericalUtil.computeHeading(p2, p1);
        if (testHeading > 0)
        {
            testHeading -= 180;
        } else if (testHeading < 0)
        {
            testHeading += 180;
        }
        double averageHeading = (testHeading + heading) / 2;
        //heading = Math.toRadians(heading);
        PolylineOptions options = new PolylineOptions();
        LatLng c = SphericalUtil.interpolate(p1, p2, 0.5);
        //googleMap.addMarker(new MarkerOptions().position(c).title("center point - interpolate")); // Just to see center. Debugging only

        Marker marker = null;
        for (int i = 0; i < numPoints; i++)
        {
            double time = (i * Math.PI) / 2;
            double x = a * Math.cos(
                    time); //(a * Math.cos(time) * Math.cos(heading)) - (b * Math.sin(time) * Math.sin(heading));
            double y = b * Math.sin(
                    time); //(a * Math.cos(time) * Math.sin(heading)) + (b * Math.sin(time) * Math.cos(heading));
            double distance = Math.sqrt(x * x - y * y);
            if (Double.isNaN(distance))
            {
                distance = Math.sqrt(y * y - x * x);
            }
            LatLng point =
                    SphericalUtil.computeOffset(c, distance, averageHeading + Math.toDegrees(time));
            options.add(point);
            if (x < 1) // It's probably 0
            {
                marker = addPolylineTag(edge, point);
            }
        }
        Polyline line = googleMap.addPolyline(options.color(pickColor(color)).geodesic(false));
        line.setTag(marker);
        return line;
    }

    private Marker addPolylineTag(Edge edge, LatLng position)
    {
        IconGenerator iconGenerator = new IconGenerator(getContext());
        StringBuilder sb = new StringBuilder();
        String text =
                edge.getFirstCity().getCityName() + " to " + edge.getSecondCity().getCityName();
        sb.append("\nLength: ");
        sb.append(edge.getLength());
        sb.append("\nOwner: ");
        if (edge.getOwner() == null)
        {
            sb.append("None");
        } else
        {
            sb.append(edge.getOwner());
        }
        sb.append('\n');
        sb.append(edge.getID());
        // Bitmap iconBitmap = iconGenerator.makeIcon(sb.toString());
        Marker marker = googleMap.addMarker(
                new MarkerOptions().title(text).snippet(sb.toString()).position(position)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.invisible)));
        // .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap))

        return marker;
    }
}
