package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;

public class Codeshare implements Serializable {

    private String airline;
    private String flight;

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "Codeshare{" +
                "airline='" + airline + '\'' +
                ", flight='" + flight + '\'' +
                '}';
    }
}
