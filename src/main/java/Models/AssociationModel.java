package Models;

import jakarta.persistence.*;

@Entity
@Table(name = "associations")
public class AssociationModel {

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

    @Column(name = "startMultiplicity", nullable = true)
    private Integer startMultiplicity;

    @Column(name = "endMultiplicity", nullable = true)
    private Integer endMultiplicity;

    @ManyToOne
    @JoinColumn(name = "start_object_id", nullable = false)
    private Model startModel;

    @ManyToOne
    @JoinColumn(name = "end_object_id", nullable = false)
    private Model endModel;

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

    public Integer getStartMultiplicity() {
        return startMultiplicity;
    }

    public void setStartMultiplicity(Integer startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    public Integer getEndMultiplicity() {
        return endMultiplicity;
    }

    public void setEndMultiplicity(Integer endMultiplicity) {
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
