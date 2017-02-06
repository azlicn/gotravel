package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Route implements Serializable {

    private String name;
    private double distance;
    private int duration;
    private IndicativePrice indicativePrice;
    private int totalTransferDuration;
    private List<RouteStop> stops = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();


    // private Set<String> kinds = new HashSet<>();
    private Map<Integer, String> kinds = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalTransferDuration() {
        return totalTransferDuration;
    }

    public void setTotalTransferDuration(int totalTransferDuration) {
        this.totalTransferDuration = totalTransferDuration;
    }

    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    public List<RouteStop> getStops() {
        return stops;
    }

    public void setStops(List<RouteStop> stops) {
        this.stops = stops;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    /*public Set<String> getKinds() {

        System.out.println(" SEGMENTS %%%% " + this.segments);

        if (!this.segments.isEmpty() && this.segments.size() > 0) {

            for (Segment segment: this.segments) {
                if (segment.getKind() != null) {
                    kinds.add(segment.getKind());
                }

            }
        }
        return kinds;
    }

    public void setKinds(Set<String> kinds) {

        this.kinds = kinds;
    }*/

    public Map<Integer, String> getKinds() {

        if (!this.segments.isEmpty() && this.segments.size() > 0) {
            Integer count = 0;
            for (Segment segment: this.segments) {
                if (segment.getKind() != null) {
                    if (segment.getSubkind() != null) {
                        if (!segment.getKind().equals(segment.getSubkind())){
                            if (segment.getSubkind().equals("unknown"))
                                kinds.put(++count, segment.getKind());
                            else
                                kinds.put(++count, segment.getSubkind());
                        } else{
                            kinds.put(++count, segment.getKind());
                        }
                    } else {
                        kinds.put(++count, segment.getKind());
                    }
                }

            }
        }
        return kinds;
    }

    public void setKinds(Map<Integer, String> kinds) {
        this.kinds = kinds;
    }

    @Override
    public String toString() {
        return "Route{" +
                " kinds=" + kinds +
                ", name='" + name + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                ", indicativePrice=" + indicativePrice +
                ", totalTransferDuration=" + totalTransferDuration +
                ", stops=" + stops +
                ", segments=" + segments +

                '}';
    }
}
