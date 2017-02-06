package my.edu.unikl.gotravel.model.rome2rio;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Stop implements Serializable{

    private String name;
    private String pos;
    private String kind;
    private String countryCode;
    private transient LatLng latLng;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public LatLng getLatLng() {


        if (this.getPos() != null && !this.getPos().isEmpty()) {

            String[] position = this.getPos().split(",");

            latLng = new LatLng(Double.valueOf(position[0]), Double.valueOf(position[1]));


        }
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "name='" + name + '\'' +
                ", pos='" + pos + '\'' +
                ", kind='" + kind + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
