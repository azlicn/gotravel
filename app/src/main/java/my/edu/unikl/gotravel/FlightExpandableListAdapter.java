package my.edu.unikl.gotravel;


import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import my.edu.unikl.android.common.util.Util;
import my.edu.unikl.gotravel.model.rome2rio.FlightDetailBody;
import my.edu.unikl.gotravel.model.rome2rio.FlightDetailHeader;

public class FlightExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<FlightDetailHeader> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<FlightDetailBody>> _listDataChild;

    public FlightExpandableListAdapter(Context context, List<FlightDetailHeader> listDataHeader,
                                 HashMap<String, List<FlightDetailBody>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getId())
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        final FlightDetailBody flightDetailBody = (FlightDetailBody) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.flight_list_item, null);
        }



        String airlineWithUrl = Util.convertToHref(SearchResultActivity.mapAirlines.get(flightDetailBody.getAirline()).getName(), SearchResultActivity.mapAirlines.get(flightDetailBody.getAirline()).getUrl());

        TextView flight_body_airline = (TextView) convertView
                .findViewById(R.id.flight_body_airline);

        flight_body_airline.setMovementMethod(LinkMovementMethod.getInstance());
        flight_body_airline.setText(Html.fromHtml(airlineWithUrl));

        String layover = "";
        String layoverDuration = "";

        TableRow flight_body_layover_row = (TableRow)convertView
                .findViewById(R.id.flight_body_layover_row);

        if (!flightDetailBody.getLayover().isEmpty() && flightDetailBody.getLayover() != null) {

            layover = "Layover at " + flightDetailBody.getLayover();
            layoverDuration = flightDetailBody.getlDuration();
        }

        TextView flight_body_layover = (TextView) convertView
                .findViewById(R.id.flight_body_layover);

        flight_body_layover.setText(layover);

        if (!flightDetailBody.getLayover().isEmpty() && flightDetailBody.getLayover() != null) {

            flight_body_layover_row.setVisibility(View.VISIBLE);
        } else {
            flight_body_layover_row.setVisibility(View.GONE);
        }


        TextView flight_body_layover_duration = (TextView) convertView
                .findViewById(R.id.flight_body_layover_duration);

        flight_body_layover_duration.setText(layoverDuration);

        TextView flight_body_route = (TextView) convertView
                .findViewById(R.id.flight_body_route);

        flight_body_route.setText(flightDetailBody.getsCode() + " to " + flightDetailBody.gettCode());

        TextView flight_body_departure = (TextView) convertView
                .findViewById(R.id.flight_body_departure);

        flight_body_departure.setText(flightDetailBody.getDeparture());

        TextView flight_body_arrival = (TextView) convertView
                .findViewById(R.id.flight_body_arrival);

        flight_body_arrival.setText(flightDetailBody.getArrival());
       // System.out.println("DURATION ---> " + flightDetailBody.getDuration());

        TextView flight_body_duration = (TextView) convertView
                .findViewById(R.id.flight_body_duration);

        flight_body_duration.setText(Util.getDuration(flightDetailBody.getDuration()));


        TextView flight_body_terminal = (TextView) convertView
                .findViewById(R.id.flight_body_terminal);

        flight_body_terminal.setText((flightDetailBody.getSourceTerminal() != null ? "Departs Terminal : " + flightDetailBody.getSourceTerminal() : "") + (flightDetailBody.getTargetTerminal() != null ? "       Arrival Terminal : " + flightDetailBody.getTargetTerminal() : ""));

        TextView flight_body_aircraft = (TextView) convertView
                .findViewById(R.id.flight_body_aircraft);



        flight_body_aircraft.setText("Aircraft : " + flightDetailBody.getAircraft());


        TableRow flight_body_terminal_row = (TableRow)convertView
                .findViewById(R.id.flight_body_terminal_row);


        if (flightDetailBody.getSourceTerminal() == null && flightDetailBody.getTargetTerminal() == null) {

            flight_body_terminal_row.setVisibility(View.GONE);

        } else {

            flight_body_terminal_row.setVisibility(View.VISIBLE);

        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        System.out.println("groupPosition " + groupPosition + " " + this._listDataHeader.get(groupPosition));
        System.out.println("child " + this._listDataChild.get(this._listDataHeader.get(groupPosition).getId()));
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getId())
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        FlightDetailHeader flightDetailHeader = (FlightDetailHeader) getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.flight_group, null);
        }

        TextView flight_departure = (TextView) convertView
                .findViewById(R.id.flight_departure);
        flight_departure.setTypeface(null, Typeface.NORMAL);
        flight_departure.setText(flightDetailHeader.getDeparture());

        TextView flight_arrival = (TextView) convertView
                .findViewById(R.id.flight_arrival);
        flight_arrival.setTypeface(null, Typeface.NORMAL);
        flight_arrival.setText(flightDetailHeader.getArrival());


        TextView flight_price = (TextView) convertView
                .findViewById(R.id.flight_price);
        flight_price.setTypeface(null, Typeface.NORMAL);
        flight_price.setText(flightDetailHeader.getPrice());

        TextView flight_duration = (TextView) convertView
                .findViewById(R.id.flight_duration);
        flight_duration.setTypeface(null, Typeface.NORMAL);
        flight_duration.setText(Util.getDuration(flightDetailHeader.getDuration()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
