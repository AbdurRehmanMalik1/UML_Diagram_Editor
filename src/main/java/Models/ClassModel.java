package Models;

import Models.CD.Attribute;
import Models.CD.Method;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ClassModel extends Model{

    @JsonInclude()
    private String className;

    @JsonInclude()
    private List<Attribute> attributes;

    @JsonInclude()
    private List<Method> methods;

    @JsonInclude()
    @JsonProperty("abstract")
    private boolean isAbstract;

    public ClassModel(){
        super();
        attributes = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public ClassModel(ClassModel other) {
        super(other);
        this.className = other.className;
        this.attributes = new ArrayList<>(other.attributes);
        this.methods = new ArrayList<>();
        for (Method method : other.methods) {
            this.methods.add(new Method(method));
        }
        this.isAbstract = other.isAbstract;
    }
    public String getClassName() {
        return className;
    }
    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Method> getMethods() {
        return methods;
    }
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }
    public void addMethod(Method method){
        methods.add(method);
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
