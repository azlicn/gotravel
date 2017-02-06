package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hop implements Serializable {

    private String sName;
    private String sPos;
    private String tName;
    private String tPos;
    private int frequency;
    private int duration;
    private IndicativePrice indicativePrice;

    private String sCode;
    private String tCode;
    private String sTerminal;
    private String tTerminal;
    private String sTime;
    private String tTime;
    private String flight;
    private String airline;
    private String aircraft;
    private int dayChange;
    private int lDuration;
    private int lDayChange;

    private List<Codeshare> codeshares = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();


    private List<Agency> agencies = new ArrayList<>();

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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
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

    public String getsTerminal() {
        return sTerminal;
    }

    public void setsTerminal(String sTerminal) {
        this.sTerminal = sTerminal;
    }

    public String gettTerminal() {
        return tTerminal;
    }

    public void settTerminal(String tTerminal) {
        this.tTerminal = tTerminal;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public int getDayChange() {
        return dayChange;
    }

    public void setDayChange(int dayChange) {
        this.dayChange = dayChange;
    }

    public int getlDuration() {
        return lDuration;
    }

    public void setlDuration(int lDuration) {
        this.lDuration = lDuration;
    }

    public int getlDayChange() {
        return lDayChange;
    }

    public void setlDayChange(int lDayChange) {
        this.lDayChange = lDayChange;
    }

    public List<Codeshare> getCodeshares() {
        return codeshares;
    }

    public void setCodeshares(List<Codeshare> codeshares) {
        this.codeshares = codeshares;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }

    @Override
    public String toString() {
        return "Hop{" +
                "sName='" + sName + '\'' +
                ", sPos='" + sPos + '\'' +
                ", tName='" + tName + '\'' +
                ", tPos='" + tPos + '\'' +
                ", frequency=" + frequency +
                ", duration=" + duration +
                ", indicativePrice=" + indicativePrice +
                ", sCode='" + sCode + '\'' +
                ", tCode='" + tCode + '\'' +
                ", sTerminal='" + sTerminal + '\'' +
                ", tTerminal='" + tTerminal + '\'' +
                ", sTime='" + sTime + '\'' +
                ", tTime='" + tTime + '\'' +
                ", flight='" + flight + '\'' +
                ", airline='" + airline + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", dayChange=" + dayChange +
                ", lDuration=" + lDuration +
                ", lDayChange=" + lDayChange +
                ", codeshares=" + codeshares +
                ", lines=" + lines +
                ", agencies=" + agencies +
                '}';
    }
}
