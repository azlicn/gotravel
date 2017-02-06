package my.edu.unikl.gotravel.model.rome2rio;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Place implements Serializable{

    private String kind;
    private String name;
    private String longName;
    private String pos;
    private String countryCode;
    private String regionCode;
    private LatLng latLng;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
        return "Place{" +
                "kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", longName='" + longName + '\'' +
                ", pos='" + pos + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
