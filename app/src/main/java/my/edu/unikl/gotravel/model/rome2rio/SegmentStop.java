package my.edu.unikl.gotravel.model.rome2rio;

public class SegmentStop extends Stop {

    private String timeZone;

    private String code;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return super.toString() + "SegmentStop{" +
                "timeZone='" + timeZone + '\'' +
                "code='" + code + '\'' +
                '}';
    }
}
