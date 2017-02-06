package my.edu.unikl.gotravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import my.edu.unikl.gotravel.model.rome2rio.Agency;
import my.edu.unikl.gotravel.model.rome2rio.FlightDetailBody;
import my.edu.unikl.gotravel.model.rome2rio.FlightDetailHeader;
import my.edu.unikl.gotravel.model.rome2rio.Hop;
import my.edu.unikl.gotravel.model.rome2rio.Itinerary;
import my.edu.unikl.gotravel.model.rome2rio.Leg;
import my.edu.unikl.gotravel.model.rome2rio.Place;
import my.edu.unikl.gotravel.model.rome2rio.Route;
import my.edu.unikl.gotravel.model.rome2rio.RouteStop;
import my.edu.unikl.gotravel.model.rome2rio.Segment;
import my.edu.unikl.android.common.util.Util;

public class RouteStopListAdapter extends ArrayAdapter<RouteStop> {

    private List<RouteStop> routeStopList;
    private List<Segment> segmentList;
    private List<Place> placeList;
    private Activity context;
    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    private AgencyListAdapter agencyListAdapter;
    private ListView agencyListView;

    private FlightExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    private List<FlightDetailHeader> flightListDataHeader;
    private HashMap<String, List<FlightDetailBody>> flightListDataBody;

    private Place destinationPlace;

    private GoogleMap map;

    private Route route;

    private String origin;
    private String destination;


    public RouteStopListAdapter(List<RouteStop> segmentStopList, Activity context) {
        super(context, R.layout.overview_result, segmentStopList);
        this.routeStopList = segmentStopList;
        this.context = context;


    }

    public int getCount() {
        if (routeStopList != null)
            return routeStopList.size();
        return 0;
    }

    public RouteStop getItem(int position) {
        if (routeStopList != null)
            return routeStopList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (routeStopList != null)
            return routeStopList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;


            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.overview_result, null);
            }

            final int nextPosition = position + 1;
            final RouteStop routeStop = routeStopList.get(position);
            final Segment segment = segmentList.get(position);
            System.out.println("ROUTE STOP " + routeStop.toString());

            TextView name = (TextView) row.findViewById(R.id.route_name);

            String withCode = routeStop.getCode() != null ? "(" + routeStop.getCode() + ")" : "";

            name.setText(routeStop.getName() + withCode);

            TextView duration = (TextView) row.findViewById(R.id.duration);

            String durationString = segment.getKind() != null ? (Util.getDuration(segment.getDuration()) + " by " + Util.getKind(segment)) : "";

            duration.setText(durationString);

            TextView indicativePrice = (TextView) row.findViewById(R.id.indicativePrice);

            String indicativePriceString = segment.getKind() != null ? Util.getPriceFormat(segment.getIndicativePrice()) : "";

            indicativePrice.setText(indicativePriceString);


            ImageView icon = (ImageView) row.findViewById(R.id.transport_image);

            String kind = Util.getKind(segment);


            icon.setImageResource(Util.getTransportIcon(kind));

            // Default
            if (nextPosition == 1) {
                populateRouteDetail(parent, nextPosition, routeStop, routeStopList, segment);
            }



             row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {



                    if (nextPosition < routeStopList.size()) {
                        populateRouteDetail(v, nextPosition, routeStop, routeStopList, segment);
                    } else {

                        Intent intent = new Intent(context, PlaceActivity.class);

                        intent.putExtra(MapActivity.MAP_DESTINATION_PLACE, destinationPlace);
                        intent.putExtra(MapActivity.MAP_JSON_ROUTE, route);
                        intent.putExtra(SearchActivity.ORIGIN_NAME, origin);
                        intent.putExtra(SearchActivity.DESTINATION_NAME, destination);

                        context.startActivity(intent);

                    }



                }
            });



        return(row);

    }

    public List<RouteStop> getRouteStopList() {
        return routeStopList;
    }

    public void setRouteStopList(List<RouteStop> routeStopList) {
        this.routeStopList = routeStopList;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public double getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }

    public double getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(double originLongitude) {
        this.originLongitude = originLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public Place getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(Place destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    private void populateRouteDetail(View view, int position, final RouteStop routeStop, List<RouteStop> routeStopList, final Segment segment){


        StringBuffer name = new StringBuffer(routeStop.getName());

        final RouteStop nextRouteStop = routeStopList.get(position);
        name = name.append(" to ").append(nextRouteStop.getName());

        final View parent = (View) view.getRootView();
        if (parent != null) {

            TabHost routeTabs=(TabHost)parent.findViewById(R.id.tabHost);
            TextView title = (TextView) routeTabs.getTabWidget().getChildTabViewAt(1).findViewById(android.R.id.title);
            title.setText(name.toString());


            LayoutInflater inflater = (LayoutInflater)  parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout train_detail_result = (LinearLayout)inflater.inflate(R.layout.train_detail_result, null);
            final LinearLayout car_detail_result = (LinearLayout)inflater.inflate(R.layout.car_detail_result, null);
            final LinearLayout flight_detail_result = (LinearLayout)inflater.inflate(R.layout.flight_detail_result, null);
            final LinearLayout route_detail = (LinearLayout)parent.findViewById(R.id.route_detail);

            routeTabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {

                    if (tabId.equals("detailTag")) {

                        String kind = Util.getKind(segment);

                        System.out.println("KIND ****** " + kind);
                        route_detail.removeAllViews();

                        if (kind.equals("train") || kind.equals("subway") || kind.equals("bus") || kind.equals("ferry") || kind.equals("carferry")) {

                            route_detail.addView(train_detail_result);
                            agencyListAdapter = new AgencyListAdapter(new ArrayList<Agency>(), context);
                            agencyListView = (ListView) context.findViewById(R.id.detail_train_list);
                            agencyListView.setAdapter(agencyListAdapter);
                            agencyListAdapter.setAgencyList(segment.getItineraries().get(0).getLegs().get(0).getHops().get(0).getAgencies());
                            agencyListAdapter.setLineList(segment.getItineraries().get(0).getLegs().get(0).getHops().get(0).getLines());
                            agencyListAdapter.notifyDataSetChanged();
                            updateMap(routeStop, nextRouteStop, kind);


                        }  else if (kind.equals("car") || kind.equals("taxi")) {

                            route_detail.addView(car_detail_result);
                            TextView distanceText = (TextView)parent.findViewById(R.id.car_distance);
                            distanceText.setText(String.valueOf(segment.getDistance()) + " km");

                            TextView durationText = (TextView)parent.findViewById(R.id.car_duration);
                            durationText.setText(Util.getDuration(segment.getDuration()));

                            updateMap(routeStop, nextRouteStop, kind);


                        }  else if (kind.equals("flight")) {

                            route_detail.addView(flight_detail_result);
                            // get the listview
                            expListView = (ExpandableListView) parent.findViewById(R.id.flight_expand);
                            // preparing list data
                            prepareFlightDetailHeader(segment.getItineraries());
                            Collections.sort(flightListDataHeader, new Comparator<FlightDetailHeader>() {
                                public int compare(FlightDetailHeader o1, FlightDetailHeader o2) {
                                    return o1.getDuration() - o2.getDuration();
                                }
                            });

                            listAdapter = new FlightExpandableListAdapter(context, flightListDataHeader, flightListDataBody);
                            // setting list adapter
                            expListView.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();
                            updateMap(routeStop, nextRouteStop, kind);

                        } else
                        {



                        }

                    }


                }
            });




        }



    }


    private void prepareFlightDetailHeader(List<Itinerary> itineraries) {

        flightListDataHeader = new ArrayList<>();

        flightListDataBody = new HashMap<>();


        for (Itinerary itinerary: itineraries) {

            List<Leg> legs = itinerary.getLegs();



            for (Leg leg : legs) {

                FlightDetailHeader flightDetailHeader = new FlightDetailHeader();

                flightDetailHeader.setPrice(Util.getPriceFormat(leg.getIndicativePrice()));
                List<FlightDetailBody> flightDetailBodies = new ArrayList<>();

                if (!leg.getHops().isEmpty() && leg.getHops().size() > 0) {


                    flightDetailHeader.setDeparture(leg.getHops().get(0).getsTime());
                    flightDetailHeader.setArrival(leg.getHops().get(leg.getHops().size() - 1).gettTime());

                    int totalDuration = 0;

                    for (Hop hop : leg.getHops()) {

                        totalDuration += hop.getDuration() + hop.getlDuration();

                    }

                    flightDetailHeader.setDuration(totalDuration);
                    flightDetailHeader.setFrequency(String.valueOf(leg.getDays()));


                    for (Hop hop : leg.getHops()) {

                        FlightDetailBody flightDetailBody = new FlightDetailBody();

                        flightDetailBody.setDeparture(hop.getsTime());
                        flightDetailBody.setArrival(hop.gettTime());
                        flightDetailBody.setDuration(hop.getDuration());
                        flightDetailBody.setLayover(hop.getlDuration() > 0 ? SearchResultActivity.mapAirports.get(hop.getsCode()).getName() + " (" + hop.getsCode() + ")" : "");
                        flightDetailBody.setAirline(hop.getAirline());


                        String aircraft = null;

                       if (hop.getAircraft() != null) {

                           aircraft = SearchResultActivity.mapAircrafts.get(hop.getAircraft()).getManufacturer() + " " + SearchResultActivity.mapAircrafts.get(hop.getAircraft()).getModel();

                       }

                        flightDetailBody.setAircraft(aircraft);
                        flightDetailBody.setsCode(hop.getsCode());
                        flightDetailBody.settCode(hop.gettCode());
                        flightDetailBody.setlDuration(hop.getlDuration() > 0 ? Util.getDuration(hop.getlDuration()) : "");
                        flightDetailBody.setSourceTerminal(hop.getsTerminal());
                        flightDetailBody.setTargetTerminal(hop.gettTerminal());
                        flightDetailBody.setSource(hop.getsTerminal() != null ? Boolean.TRUE : Boolean.FALSE);

                        flightDetailBodies.add(flightDetailBody);
                    }


                }

                flightListDataHeader.add(flightDetailHeader);
                flightListDataBody.put(flightDetailHeader.getId(), flightDetailBodies);
            }

        }
        System.out.println("Size " + flightListDataHeader.size() + " " + flightListDataHeader.toString());
        System.out.println("Size " + flightListDataBody.size() + " " + flightListDataBody.toString());


    }


    private void updateMap(RouteStop startStop, RouteStop endStop, String kind) {


        // Add a marker in Origin and move the camera
        LatLng origin = startStop.getLatLng();

        map.moveCamera(CameraUpdateFactory.newLatLng(origin));
        LatLng destination = endStop.getLatLng();


        map.moveCamera(CameraUpdateFactory.newLatLng(origin));

        final LatLngBounds bounds = new LatLngBounds.Builder().include(origin).include(destination).build();

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                // Move camera.
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
                // Remove listener to prevent position reset on camera move.
                map.setOnCameraChangeListener(null);
            }
        });

        if (!kind.equals("flight")) {

            Polyline line = map.addPolyline(new PolylineOptions()
                    .add(origin, destination)
                    .width(8)
                    .color(Util.getPoligonColor(kind)));

        }


    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
