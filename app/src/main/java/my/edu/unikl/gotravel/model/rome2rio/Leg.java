package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Leg implements Serializable {

    private String url;
    private String host;
    private int days;
    private IndicativePrice indicativePrice;
    private List<Hop> hops = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    public List<Hop> getHops() {
        return hops;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "url='" + url + '\'' +
                ", host='" + host + '\'' +
                ", days=" + days +
                ", indicativePrice=" + indicativePrice +
                ", hops=" + hops +
                '}';
    }
}
