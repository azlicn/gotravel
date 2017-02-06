package my.edu.unikl.gotravel.model.rome2rio;

import java.util.ArrayList;
import java.util.List;

public class RouteStop extends Stop{

    private String regionCode;

    private String timeZone;

    private String code;

    private List<Segment> segments = new ArrayList<>();

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }



    @Override
    public String toString() {
        return super.toString() + " RouteStop{" +
                "regionCode='" + regionCode + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
