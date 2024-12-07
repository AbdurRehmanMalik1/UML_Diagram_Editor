package Models.CD;

import java.io.Serializable;

public class Attribute  implements Serializable {
    private String name;
    private String dataType;
    private String accessModifier = "private";
    public Attribute(){

    }
    public Attribute(String name, String type, String accessModifier) {
        this.name = name;
        this.dataType = type;
        this.accessModifier = accessModifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }
    @Override
    public String toString() {
        String accessSymbol = switch (accessModifier.toLowerCase()) {
            case "public" -> "+";
            case "private" -> "-";
            case "protected" -> "#";
            default -> "~";
        };
        return accessSymbol + " " + name + " : " + dataType;
    }
}
