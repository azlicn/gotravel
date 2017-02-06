package my.edu.unikl.gotravel;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;
import my.edu.unikl.android.common.logger.Log;
import my.edu.unikl.android.common.util.Util;
import my.edu.unikl.gotravel.model.rome2rio.Place;
import my.edu.unikl.gotravel.model.rome2rio.Route;
import my.edu.unikl.gotravel.model.rome2rio.RouteStop;
import my.edu.unikl.gotravel.model.rome2rio.Segment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = "MapActivity";
    public GoogleMap mMap;
    private String origin;
    private String destination;
    private double originLatitude = -34;
    private double originLongitude = 151;

    private double destinationLatitude = 0;
    private double destinationLongitude = 0;
    private Route route;
    private Place originPlace;
    private Place destinationPlace;
    private String jsonRoutes;

    private RouteStopListAdapter routeStopListAdapter;
    private ListView routeStopListView;

    public static final String MAP_DESTINATION_PLACE = "my.edu.unikl.gotravel.MapActivity.MAP_DESTINATION_PLACE";
    public static final String MAP_JSON_ROUTE = "my.edu.unikl.gotravel.MapActivity.MAP_JSON_ROUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        System.out.println(TAG + " " + intent.toString());
        System.out.println("SIZE " + TAG + " " + intent.getExtras().size());
        System.out.println("LATITUDE " + intent.getStringExtra(SearchActivity.ORIGIN_LATITUDE));
        System.out.println("LONGITUDE " + intent.getStringExtra(SearchActivity.ORIGIN_LONGITUDE));
        System.out.println("ORIGIN " + intent.getStringExtra(SearchActivity.ORIGIN_NAME));
        System.out.println("DESTINATION " + intent.getStringExtra(SearchActivity.DESTINATION_NAME));

        route =   (Route) intent.getSerializableExtra(SearchActivity.ROUTE);
        originPlace =   (Place) intent.getSerializableExtra(SearchActivity.PLACE_ORIGIN);
        destinationPlace =   (Place) intent.getSerializableExtra(SearchActivity.PLACE_DESTINATION);
        jsonRoutes =   (String) intent.getSerializableExtra(SearchActivity.JSON_ROUTE);

        origin = originPlace.getName();//intent.getStringExtra(SearchActivity.ORIGIN_NAME);
        destination = destinationPlace.getName();//intent.getStringExtra(SearchActivity.DESTINATION_NAME);
        String[] originString = originPlace.getPos().split(",");
        String[] destinationString = destinationPlace.getPos().split(",");
        originLatitude = Double.parseDouble(originString[0]);
        originLongitude = Double.parseDouble(originString[1]);
        destinationLatitude = Double.parseDouble(destinationString[0]);
        destinationLongitude = Double.parseDouble(destinationString[1]);

        routeStopListAdapter = new RouteStopListAdapter(new ArrayList<RouteStop>(), this);
        routeStopListAdapter.setRoute(route);
        routeStopListAdapter.setOriginLatitude(originLatitude);
        routeStopListAdapter.setOriginLongitude(originLongitude);
        routeStopListAdapter.setDestinationLatitude(destinationLatitude);
        routeStopListAdapter.setDestinationLongitude(destinationLongitude);
        routeStopListAdapter.setDestinationPlace(destinationPlace);
        routeStopListAdapter.setOrigin(originPlace.getName());
        routeStopListAdapter.setDestination(destinationPlace.getName());
        routeStopListView = (ListView) findViewById(R.id.overview_list);
        routeStopListView.setAdapter(routeStopListAdapter);

        List<Place> places = new ArrayList<>();
        places.add(originPlace);
        places.add(destinationPlace);

        Segment blankSegment = new Segment();

        route.getSegments().add(route.getSegments().size(), blankSegment);
        routeStopListAdapter.setRouteStopList(route.getStops());
        routeStopListAdapter.setSegmentList(route.getSegments());
        routeStopListAdapter.setPlaceList(places);
        routeStopListAdapter.notifyDataSetChanged();

        TextView route_name = (TextView) findViewById(R.id.route_name);
        route_name.setText(route.getName());

        getSupportActionBar().setTitle(origin + " to " + destination);


        Log.i(TAG, "$$$$$ route: " + route.getName());

        Log.i(TAG, "$$$$$ originLatitude: " + originLatitude);
        Log.i(TAG, "$$$$$ originLongitude: " + originLongitude);
        Log.i(TAG, "$$$$$ destinationLatitude: " + destinationLatitude);
        Log.i(TAG, "$$$$$ destinationLongitude: " + destinationLongitude);

        TabHost routeTabs=(TabHost)findViewById(R.id.tabHost);

        routeTabs.setup();

        TabHost.TabSpec spec = routeTabs.newTabSpec("overviewTag");

        spec.setContent(R.id.route_overview);
        spec.setIndicator("Overview");
        routeTabs.addTab(spec);

        spec=routeTabs.newTabSpec("detailTag");
        spec.setContent(R.id.route_detail);
        spec.setIndicator("");
        routeTabs.addTab(spec);

        routeTabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> originAddresses = new ArrayList<>();
        List<Address> destinationAddress = new ArrayList<>();
        String originName = "NONE";
        String destinationName = "NONE";
        try {
            originAddresses = gcd.getFromLocation(originLatitude, originLongitude, 1);
            destinationAddress = gcd.getFromLocation(destinationLatitude, destinationLongitude, 1);
        } catch (IOException exception){
            Log.e(TAG, "Address not found", exception);
        }
        if (originAddresses.size() > 0){
            originName = (originAddresses.get(0).getLocality() == null ? " " : originAddresses.get(0).getLocality() + ", ")  + originAddresses.get(0).getCountryName();
        }
        if (destinationAddress.size() > 0){
            destinationName = (destinationAddress.get(0).getLocality() == null ? " " : destinationAddress.get(0).getLocality() + ", ")  + destinationAddress.get(0).getCountryName();
        }

        // Add a marker in Origin and move the camera
        LatLng origin = new LatLng(originLatitude, originLongitude);
      //  mMap.addMarker(new MarkerOptions().position(origin).title(originName).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

            //mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
        LatLng destination = new LatLng(destinationLatitude, destinationLongitude);

        //mMap.addMarker(new MarkerOptions().position(destination).title(destinationName).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));

        int position = 0;
        for (RouteStop routeStop: route.getStops()) {

            LatLng routeLatLng = routeStop.getLatLng();
            Segment segment = route.getSegments().get(position);
            String kind = Util.getKind(segment);
            System.out.println("KIND " + kind);

            //mMap.addMarker(new MarkerOptions().position(routeLatLng).title(routeStop.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.addMarker(new MarkerOptions().position(routeLatLng).title(routeStop.getName()).icon(BitmapDescriptorFactory.fromResource(Util.getMarkerIcon(kind))));

            position++;
        }

        final LatLngBounds bounds = new LatLngBounds.Builder().include(origin).include(destination).build();
       // mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                // Move camera.
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
                // Remove listener to prevent position reset on camera move.
                mMap.setOnCameraChangeListener(null);
            }
        });

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(origin, destination)
                .width(8)
                .color(Color.argb(150, 75,0,130)));

        routeStopListAdapter.setMap(mMap);
    }


}

