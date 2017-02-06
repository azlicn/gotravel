package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;

public class Airport extends Place implements Serializable {

    private String code;
    private String timeZone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return super.toString() + "Airport{" +
                "code='" + code + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
