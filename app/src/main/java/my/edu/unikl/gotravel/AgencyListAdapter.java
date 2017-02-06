package my.edu.unikl.gotravel;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

import my.edu.unikl.android.common.util.Util;
import my.edu.unikl.gotravel.model.rome2rio.Action;
import my.edu.unikl.gotravel.model.rome2rio.Agency;
import my.edu.unikl.gotravel.model.rome2rio.Company;
import my.edu.unikl.gotravel.model.rome2rio.Line;

public class AgencyListAdapter extends ArrayAdapter<Agency> {

    private List<Agency> agencyList;
    private List<Line> lineList;
    private Activity context;

    public AgencyListAdapter(List<Agency> agencyList, Activity context) {
        super(context, R.layout.train_detail_result, agencyList);
        this.agencyList = agencyList;
        this.context = context;

    }

    public int getCount() {
        if (agencyList != null)
            return agencyList.size();
        return 0;
    }

    public Agency getItem(int position) {
        if (agencyList != null)
            return agencyList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (agencyList != null)
            return agencyList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.train_detail_result, null);


            Agency agency = agencyList.get(position);
            List<Action> actions = agency.getActions();

            Company company = SearchResultActivity.mapAgencies.get(agency.getAgency());

            String agencyWithUrl = Util.convertToHref(company.getName(), company.getUrl());


            StringBuffer lineBuffer = new StringBuffer("");

            for (Line line: lineList) {

                if (line.getName() != null && !line.getName().equals("")) {
                    lineBuffer.append(line.getName()).append(", ");
                }
            }

            TextView agencyText = (TextView)row.findViewById(R.id.train_agency);
            agencyText.setMovementMethod(LinkMovementMethod.getInstance());
            agencyText.setText(Html.fromHtml(agencyWithUrl));

            TextView frequencyText = (TextView)row.findViewById(R.id.train_frequency);
            frequencyText.setText(Util.getFrequency(agency.getFrequency()));

            TextView durationText = (TextView)row.findViewById(R.id.train_duration);
            durationText.setText(Util.getDuration(agency.getDuration()));
            if (!lineBuffer.toString().equals("")) {
                String path = lineBuffer.toString().substring(0, lineBuffer.toString().length() - 2);
                TextView lineText = (TextView) row.findViewById(R.id.line_agency);
                lineText.setText("Line : " + path);
            }

            StringBuffer scheduleBuffer = new StringBuffer("");

            for (Action action:actions) {

                String name = action.getDisplayUrl().substring(0, action.getDisplayUrl().indexOf("."));

                scheduleBuffer.append(Util.convertToHref(WordUtils.capitalize(name), action.getUrl())).append(", ");

            }

            if (!scheduleBuffer.toString().equals("")) {
                String path = scheduleBuffer.toString().substring(0, scheduleBuffer.toString().length() - 2);
                TextView scheduleText = (TextView) row.findViewById(R.id.schedule_agency);
                scheduleText.setMovementMethod(LinkMovementMethod.getInstance());
                scheduleText.setText(Html.fromHtml("Schedules at " + " " + path));
            }

        }
        return(row);

    }

    public List<Agency> getAgencyList() {
        return agencyList;
    }

    public void setAgencyList(List<Agency> agencyList) {
        this.agencyList = agencyList;
    }


    public List<Line> getLineList() {
        return lineList;
    }

    public void setLineList(List<Line> lineList) {
        this.lineList = lineList;
    }
}
