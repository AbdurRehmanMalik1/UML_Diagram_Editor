package Models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class Model implements Serializable {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private double x=0;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private double y=0;
    Model(){

    }
    public void setCoordinate(double layoutX, double layoutY) {
        x = layoutX;
        y = layoutY;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
