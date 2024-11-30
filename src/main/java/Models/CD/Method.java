package Models.CD;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
public class Method implements Serializable {
    @JsonInclude()
    private String text;
    @JsonInclude()
    private boolean isAbstract;

    Method(){
    }
    public Method(Method other) {
        this.text = other.text;
        this.isAbstract = other.isAbstract;
    }

    public Method(String text) {
        this.text = text;
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

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
