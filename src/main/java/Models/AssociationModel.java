package Models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "associations")
public class AssociationModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "association_id")
    private int id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "start_x")
    private double startX;

    @Column(name = "start_y")
    private double startY;

    @Column(name = "end_x")
    private double endX;

    @Column(name = "end_y")
    private double endY;

    @Column(name = "startMultiplicity")
    private String startMultiplicity;

    @Column(name = "endMultiplicity")
    private String endMultiplicity;

    @ManyToOne
    @JoinColumn(name = "start_object_id", nullable = false)
    private transient Model startModel; // Marked as transient to avoid full serialization

    @ManyToOne
    @JoinColumn(name = "end_object_id", nullable = false)
    private transient Model endModel; // Marked as transient to avoid full serialization

    public AssociationModel() {}

    public AssociationModel(String type) {
        this.type = type;
    }

    // Getters and setters for all fields

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartMultiplicity() {
        return startMultiplicity;
    }

    public void setStartMultiplicity(String startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    public String getEndMultiplicity() {
        return endMultiplicity;
    }

    public void setEndMultiplicity(String endMultiplicity) {
        this.endMultiplicity = endMultiplicity;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public Model getStartModel() {
        return startModel;
    }

    public void setStartModel(Model startModel) {
        this.startModel = startModel;
    }

    public Model getEndModel() {
        return endModel;
    }

    public void setEndModel(Model endModel) {
        this.endModel = endModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
