package my.edu.unikl.gotravel.model.rome2rio;


import java.io.Serializable;

public class FlightDetailBody implements Serializable{

    private String airline;
    private String departure;
    private String arrival;
    private int duration;
    private String lDuration;
    private String layover;
    private String sourceTerminal;
    private String targetTerminal;
    private String aircraft;
    private String sCode;
    private String tCode;
    private Boolean source;


    public FlightDetailBody() {

        super();
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getlDuration() {
        return lDuration;
    }

    public void setlDuration(String lDuration) {
        this.lDuration = lDuration;
    }

    public String getLayover() {
        return layover;
    }

    public void setLayover(String layover) {
        this.layover = layover;
    }

    public String getSourceTerminal() {
        return sourceTerminal;
    }

    public void setSourceTerminal(String sourceTerminal) {
        this.sourceTerminal = sourceTerminal;
    }

    public String getTargetTerminal() {
        return targetTerminal;
    }

    public void setTargetTerminal(String targetTerminal) {
        this.targetTerminal = targetTerminal;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }


    public Boolean getSource() {
        return source;
    }

    public void setSource(Boolean source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "FlightDetailBody{" +
                "airline='" + airline + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", duration=" + duration +
                ", lDuration='" + lDuration + '\'' +
                ", layover='" + layover + '\'' +
                ", sourceTerminal='" + sourceTerminal + '\'' +
                ", targetTerminal='" + targetTerminal + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", sCode='" + sCode + '\'' +
                ", tCode='" + tCode + '\'' +
                ", source=" + source +
                '}';
    }
}
