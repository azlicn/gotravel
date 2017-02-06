package my.edu.unikl.gotravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import my.edu.unikl.gotravel.model.rome2rio.Place;
import my.edu.unikl.gotravel.model.rome2rio.Route;
import my.edu.unikl.android.common.util.Util;

public class RouteListAdapter extends ArrayAdapter<Route> {

    private List<Route> routeList;
    private List<Place> placeList;
    private Activity context;
    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    

    Map<Integer, String> mapKinds = new HashMap<>();

    public RouteListAdapter(List<Route> routeList, Activity context) {
        super(context, R.layout.activity_search_result, routeList);
        this.routeList = routeList;
        this.context = context;

    }

    public int getCount() {
        if (routeList != null)
            return routeList.size();
        return 0;
    }

    public Route getItem(int position) {
        if (routeList != null)
            return routeList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (routeList != null)
            return routeList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        mapKinds = new HashMap<>();

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_search_result, null);


            final Route route = routeList.get(position);
            final Place originPlace = placeList.get(0);
            final Place destinationPlace = placeList.get(1);
            System.out.println("NAME ROUTE " + route.getName());

            TextView name = (TextView) row.findViewById(R.id.route_name);

            name.setText(route.getName());

            TextView duration = (TextView) row.findViewById(R.id.duration);

            duration.setText(Util.getDuration(route.getDuration()));

            TextView indicativePrice = (TextView) row.findViewById(R.id.indicativePrice);

            indicativePrice.setText(Util.getPriceFormat(route.getIndicativePrice()));

            LinearLayout layout = (LinearLayout) row.findViewById(R.id.image_group);
            System.out.println(route.getName() + " route.getKinds() " + route.getKinds());


                Set<String> valueSet = new HashSet<String>(route.getKinds().values());

                Iterator<String> it = valueSet.iterator();

                Map<Integer , String> uniqueMap = new HashMap<Integer , String>();

                while(it.hasNext()){
                    String value = it.next();

                    for(Map.Entry<Integer , String> e : route.getKinds().entrySet()){
                        if(value.equals(e.getValue())  && !uniqueMap.containsValue(value)){
                            uniqueMap.put(e.getKey(), value);
                        }
                    }
                }

                Map<Integer, String> treeMap = new TreeMap<>(uniqueMap);
                for (Integer key : treeMap.keySet()) {
                    String kind = route.getKinds().get(key);

                    //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
                    ImageView imageView = new ImageView(parent.getRootView().getContext());
                    imageView.setImageResource(Util.getTransportIcon(kind));
                    //imageView.setLayoutParams(layoutParams);
                    imageView.setId(key);
                    layout.addView(imageView);
                }




            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), MapActivity.class);
                    intent.putExtra(SearchActivity.ROUTE, route);
                    intent.putExtra(SearchActivity.PLACE_ORIGIN, originPlace);
                    intent.putExtra(SearchActivity.PLACE_DESTINATION, destinationPlace);


                    v.getContext().startActivity(intent);


                }
            });
            }

        return(row);

    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
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


}
