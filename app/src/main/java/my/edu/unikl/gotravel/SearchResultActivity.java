package my.edu.unikl.gotravel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.droidparts.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.edu.unikl.gotravel.model.rome2rio.Aircraft;
import my.edu.unikl.gotravel.model.rome2rio.Airport;
import my.edu.unikl.gotravel.model.rome2rio.Company;
import my.edu.unikl.gotravel.model.rome2rio.Route;

public class SearchResultActivity extends AppCompatActivity {

    private String URL;// = this.getResources().getString(R.string.rome2rio_url); //"http://free.rome2rio.com/api/1.2/json/Search";

    private String ROME2RIO_KEY;// = this.getResources().getString(R.string.rome2rio_key);//"KQlGErVa";

    private Gson gson = new Gson();

    private static final Type placeType = new TypeToken<List<my.edu.unikl.gotravel.model.rome2rio.Place>>() {
    }.getType();
    private static final Type airlineType = new TypeToken<List<Company>>() {
    }.getType();
    private static final Type airportType = new TypeToken<List<Airport>>() {
    }.getType();
    private static final Type aircraftType = new TypeToken<List<Aircraft>>() {
    }.getType();
    private static final Type agencyType = new TypeToken<List<Company>>() {
    }.getType();
    private static final Type routeType = new TypeToken<List<Route>>() {
    }.getType();
    private List<my.edu.unikl.gotravel.model.rome2rio.Place> placeList = new ArrayList<>();
    private List<Airport> airportList = new ArrayList<>();
    private List<Company> airlineList = new ArrayList<>();
    private List<Aircraft> aircraftList = new ArrayList<>();
    private List<Company> agencyList = new ArrayList<>();
    private List<Route> routeList= new ArrayList<>();

    private ProgressDialog mDialog;

    private TextView mJsonTextView;

    private JSONArray places;
    private JSONArray airports;
    private JSONArray airlines;
    private JSONArray aircrafts;
    private JSONArray routes;
    private JSONArray agencies;
    private String origin;
    private String destination;
    private double originLatitude = -34;
    private double originLongitude = 151;

    private double destinationLatitude = 0;
    private double destinationLongitude = 0;
    private RouteListAdapter adapter;
    private ListView routeListView;

    public static final String MAP_KEY_PLACE = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_PLACE";
    public static final String MAP_KEY_AIRPORT = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_AIRPORT";
    public static final String MAP_KEY_AIRCRAFT = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_AIRCRAFT";
    public static final String MAP_KEY_AIRLINE = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_AIRLINE";
    public static final String MAP_KEY_ROUTE = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_ROUTE";
    public static final String MAP_KEY_AGENCY = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_AGENCY";
    public static final String MAP_KEY_ROUTE_DETAIL = "my.edu.unikl.gotravel.SearchResultActivity.MAP_KEY_ROUTE_DETAIL";

    public static Map<String, Company> mapAirlines;
    public static Map<String, Company> mapAgencies;
    public static Map<String, Aircraft> mapAircrafts;
    public static Map<String, Airport> mapAirports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        URL = getString(R.string.rome2rio_url);

        ROME2RIO_KEY = getString(R.string.rome2rio_key);

        Intent intent = getIntent();
        originLatitude = Double.parseDouble(intent.getStringExtra(SearchActivity.ORIGIN_LATITUDE));
        originLongitude = Double.parseDouble(intent.getStringExtra(SearchActivity.ORIGIN_LONGITUDE));
        destinationLatitude = Double.parseDouble(intent.getStringExtra(SearchActivity.DESTINATION_LATITUDE));
        destinationLongitude = Double.parseDouble(intent.getStringExtra(SearchActivity.DESTINATION_LONGITUDE));
        origin = intent.getStringExtra(SearchActivity.ORIGIN_NAME);
        destination = intent.getStringExtra(SearchActivity.DESTINATION_NAME);

        adapter = new RouteListAdapter(new ArrayList<Route>(), this);
        adapter.setOriginLatitude(originLatitude);
        adapter.setOriginLongitude(originLongitude);
        adapter.setDestinationLatitude(destinationLatitude);
        adapter.setDestinationLongitude(destinationLongitude);

        routeListView = (ListView) findViewById(R.id.route_list);
        routeListView.addHeaderView(new View(this));
        routeListView.addFooterView(new View(this));

        routeListView.setAdapter(adapter);

        (new AsyncListViewLoader()).execute(URL);


    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, Map<String, List> > {


        private final ProgressDialog dialog = new ProgressDialog(SearchResultActivity.this);

        @Override
        protected void onPostExecute(Map<String, List>  result) {

            mapAirlines = new HashMap<>();
            mapAircrafts = new HashMap<>();
            mapAirports = new HashMap<>();
            mapAgencies = new HashMap<>();
            super.onPostExecute(result);
            dialog.dismiss();


            adapter.setRouteList(result.get(MAP_KEY_ROUTE));
            adapter.setPlaceList(result.get(MAP_KEY_PLACE));

            List<Company> airlines = result.get(MAP_KEY_AIRLINE);
            List<Company> agencies = result.get(MAP_KEY_AGENCY);
            List<Aircraft> aircrafts = result.get(MAP_KEY_AIRCRAFT);
            List<Airport> airports = result.get(MAP_KEY_AIRPORT);

            if (!airlines.isEmpty() && airlines.size() > 1) {

                for (Company airline: airlines) {
                    mapAirlines.put(airline.getCode(), airline);
                }
            }

            if (!aircrafts.isEmpty() && aircrafts.size() > 1) {

                for (Aircraft aircraft: aircrafts) {
                    mapAircrafts.put(aircraft.getCode(), aircraft);
                }
            }

            if (!airports.isEmpty() && airports.size() > 1) {

                for (Airport airport: airports) {
                    mapAirports.put(airport.getCode(), airport);
                }
            }

            if (!agencies.isEmpty() && agencies.size() > 1) {

                for (Company agency: agencies) {
                    mapAgencies.put(agency.getCode(), agency);
                }
            }

            adapter.notifyDataSetChanged();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Retrieving data...");
            dialog.show();
        }

        @Override
        protected Map<String, List>  doInBackground(String... params) {

            Map<String, List> maps = new HashMap<>();

            try {
                URL u = new URL(URL + "?key=" + ROME2RIO_KEY + "&oName=" + URLEncoder.encode(origin, "UTF-8").replaceAll("%2B","+") + "&dName=" + URLEncoder.encode(destination, "UTF-8").replaceAll("%2B","+")  + "&currencyCode=MYR");
                System.out.println("URL " + u.toString());
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");

                conn.connect();
                InputStream is = conn.getInputStream();


                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                IOUtils.copy(is, baos);
                System.out.println("OUTPUT " + baos);
                JSONObject response = new JSONObject(baos.toString());

                places = response.getJSONArray("places");
                airports = response.getJSONArray("airports");
                airlines = response.getJSONArray("airlines");
                aircrafts = response.getJSONArray("aircrafts");
                routes = response.getJSONArray("routes");
                agencies = response.getJSONArray("agencies");

                placeList = gson.fromJson(places.toString(), placeType);
                airportList = gson.fromJson(airports.toString(), airportType);
                airlineList = gson.fromJson(airlines.toString(), airlineType);
                aircraftList = gson.fromJson(aircrafts.toString(), aircraftType);
                agencyList = gson.fromJson(agencies.toString(), agencyType);
                routeList = gson.fromJson(routes.toString(), routeType);

                Collections.sort(routeList, new Comparator<Route>() {
                    public int compare(Route o1, Route o2) {
                        return Double.compare(o1.getIndicativePrice().getPrice(),o2.getIndicativePrice().getPrice());
                    }
                });

                maps.put(MAP_KEY_PLACE, placeList);
                maps.put(MAP_KEY_AIRPORT, airportList);
                maps.put(MAP_KEY_AIRLINE, airlineList);
                maps.put(MAP_KEY_AIRCRAFT, aircraftList);
                maps.put(MAP_KEY_AGENCY, agencyList);
                maps.put(MAP_KEY_ROUTE, routeList);
                System.out.println("routes " + routeList);

                return maps;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;


        }


    }

}
