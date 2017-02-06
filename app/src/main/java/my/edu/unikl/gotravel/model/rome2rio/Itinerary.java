package my.edu.unikl.gotravel.model.rome2rio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Itinerary implements Serializable {

    private int isHidden;
    private int isReturn;
    private List<Leg> legs = new ArrayList<>();

    public int getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

    public int getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(int isReturn) {
        this.isReturn = isReturn;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "isHidden=" + isHidden +
                ", isReturn=" + isReturn +
                ", legs=" + legs +
                '}';
    }
}
