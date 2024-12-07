package Models;



import java.io.Serial;
import java.io.Serializable;

public class AssociationModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private String associationName;

    private String type;

    private double startX;

    private double startY;

    private double endX;

    private double endY;

    private String startMultiplicity;

    private String endMultiplicity;

    private transient Model startModel;

    private transient Model endModel;

    public AssociationModel() {}

    public AssociationModel(String type) {
        this.type = type;
    }
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

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }
}
