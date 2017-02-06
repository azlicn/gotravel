package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;

public class Line implements Serializable {

    private String name;
    private String vehicle;
    private String agency;
    private String frequency;
    private int duration;


    private int days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Line{" +
                "name='" + name + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", agency='" + agency + '\'' +
                ", frequency='" + frequency + '\'' +
                ", duration=" + duration +
                ", days=" + days +
                '}';
    }
}
