package Models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"  // You can use any unique field, like `id`
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassModel.class, name = "Class"),
        @JsonSubTypes.Type(value = InterfaceModel.class, name = "Interface")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "models")
public abstract class Model implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static int modelIdCounter = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    public int id;

    @JsonInclude()
    @Column(name = "coordinate_x")
    private double x = 0;

    @JsonInclude()
    @Column(name = "coordinate_y")
    private double y = 0;

    //@JsonInclude(JsonInclude.Include.ALWAYS)
    @OneToMany(mappedBy = "startModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    transient private List<AssociationModel> incomingAssociations = new ArrayList<>();

    //@JsonInclude(JsonInclude.Include.ALWAYS)
    @OneToMany(mappedBy = "endModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    transient private List<AssociationModel> outgoingAssociations = new ArrayList<>();

    protected Model() {
        id = generateUniqueId();
    }
    private synchronized int generateUniqueId() {
        return modelIdCounter++;
    }

    public int getModelId() {
        return id;
    }

    public void setModelId(int id) {
        this.id = id;
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

    public void addStartAssociation(AssociationModel association) {
        outgoingAssociations.add(association);  // Now it's clear this is an "outgoing" or "start" association
        association.setStartModel(this);         // This model is the start of the association
    }

    public void removeStartAssociation(AssociationModel association) {
        outgoingAssociations.remove(association);
        association.setStartModel(null);
    }

    public void addEndAssociation(AssociationModel association) {
        incomingAssociations.add(association);  // This is the "incoming" or "end" association
        association.setEndModel(this);           // This model is the end of the association
    }

    public void removeEndAssociation(AssociationModel association) {
        incomingAssociations.remove(association);
        association.setEndModel(null);
    }
}
