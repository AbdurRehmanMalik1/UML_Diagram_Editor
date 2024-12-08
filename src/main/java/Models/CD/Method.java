package Models.CD;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

public class Method implements Serializable {
    @JsonInclude()
    private String returnType;
    @JsonInclude()
    private String text;
    @JsonInclude()
    private boolean isAbstract;
    private String accessModifier = "public";

    public Method() {
    }

    public Method(Method other) {
        this.returnType = other.returnType;
        this.text = other.text;
        this.isAbstract = other.isAbstract;
        this.accessModifier = other.accessModifier;
    }

    public Method(String returnType, String text) {
        this.returnType = returnType;
        this.text = text;
    }

    public Method(String returnType, String text, String accessModifier) {
        this.returnType = returnType;
        this.text = text;
        this.accessModifier = accessModifier;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }
    @Override
    public String toString() {
        String prefix = switch (accessModifier) {
            case "public" -> "+"; // For public methods
            case "protected" -> "#"; // For protected methods
            case "private" -> "-"; // For private methods
            default -> "+"; // Default to public if no valid modifier is set
        };
        return prefix + " " + text + " : "+ returnType;
    }

}
