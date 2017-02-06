package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Segment implements Serializable {

    private String kind;
    private String subkind;
    private String vehicle;
    private int isMajor;
    private int isImperial;
    private double distance;
    private int duration;
    private int transferDuration;
    private String sName;
    private String sPos;
    private String tName;
    private String tPos;
    private IndicativePrice indicativePrice;
    private List<SegmentStop> stops = new ArrayList<>();
    private List<Itinerary> itineraries = new ArrayList<>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSubkind() {
        return subkind;
    }

    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public int getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(int isMajor) {
        this.isMajor = isMajor;
    }

    public int getIsImperial() {
        return isImperial;
    }

    public void setIsImperial(int isImperial) {
        this.isImperial = isImperial;
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

    public int getTransferDuration() {
        return transferDuration;
    }

    public void setTransferDuration(int transferDuration) {
        this.transferDuration = transferDuration;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPos() {
        return sPos;
    }

    public void setsPos(String sPos) {
        this.sPos = sPos;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettPos() {
        return tPos;
    }

    public void settPos(String tPos) {
        this.tPos = tPos;
    }

    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    public List<SegmentStop> getStops() {
        return stops;
    }

    public void setStops(List<SegmentStop> stops) {
        this.stops = stops;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "kind='" + kind + '\'' +
                ", subkind='" + subkind + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", isMajor=" + isMajor +
                ", isImperial=" + isImperial +
                ", distance=" + distance +
                ", duration=" + duration +
                ", transferDuration=" + transferDuration +
                ", sName='" + sName + '\'' +
                ", sPos='" + sPos + '\'' +
                ", tName='" + tName + '\'' +
                ", tPos='" + tPos + '\'' +
                ", indicativePrice=" + indicativePrice +
                ", stops=" + stops +
                ", itineraries=" + itineraries +
                '}';
    }
}
