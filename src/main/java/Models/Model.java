package Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "models")
public abstract class Model implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int id;

    @Column(name = "type", nullable = false)
    private String type;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "coordinate_x")
    private double x = 0;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "coordinate_y")
    private double y = 0;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @OneToMany(mappedBy = "startModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssociationModel> incomingAssociations = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @OneToMany(mappedBy = "endModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssociationModel> outgoingAssociations = new ArrayList<>();

    // Default constructor for JPA
    protected Model() {
    }

    public Model(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public void setCoordinate(double x,double y){
        this.x = x;
        this.y = y;
    }
    public List<AssociationModel> getIncomingAssociations() {
        return incomingAssociations;
    }

    public void setIncomingAssociations(List<AssociationModel> incomingAssociations) {
        this.incomingAssociations = incomingAssociations;
    }

    public List<AssociationModel> getOutgoingAssociations() {
        return outgoingAssociations;
    }

    public void setOutgoingAssociations(List<AssociationModel> outgoingAssociations) {
        this.outgoingAssociations = outgoingAssociations;
    }

    public void addIncomingAssociation(AssociationModel association) {
        incomingAssociations.add(association);
        association.setStartModel(this);
    }

    public void removeIncomingAssociation(AssociationModel association) {
        incomingAssociations.remove(association);
        association.setStartModel(null);
    }

    public void addOutgoingAssociation(AssociationModel association) {
        outgoingAssociations.add(association);
        association.setEndModel(this);
    }

    public void removeOutgoingAssociation(AssociationModel association) {
        outgoingAssociations.remove(association);
        association.setEndModel(null);
    }
}
