package Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.io.Serializable;


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Model implements Serializable {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "coordinate_x")
    private double x=0;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "coordinate_y")
    private double y=0;
    Model(){

    }
    public Model(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setCoordinate(double x, double y) {
        this.x =  x;
        this.y = y;
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
