package Models;

import Models.CD.Method;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="class")
public class ClassModel extends Model{

    @JsonInclude()
    @Column(name = "class_name" ,nullable = false)
    private String className;

    @JsonInclude()
    @ElementCollection
    @CollectionTable(name = "class_attributes", joinColumns = @JoinColumn(name = "class_id"))
    @Column(name = "attribute")
    private List<String> attributes;

    @JsonInclude()
    @ElementCollection
    @CollectionTable(name = "class_methods", joinColumns = @JoinColumn(name = "class_id"))
    @Column(name = "method")
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
    public List<String> getAttributes() {
        return attributes;
    }

    public List<Method> getMethods() {
        return methods;
    }
    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public void addAttribute(String attribute){
        attributes.add(attribute);
    }
    public void addMethod(Method method){
        methods.add(method);
    }
    public void removeAttribute(String attribute){
        attributes.remove(attribute);
    }
    public void removeMethod(Method method){
        methods.remove(method);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Class Name: ").append(className).append("\n");

        sb.append("Attributes: \n");
        for (String attribute : attributes) {
            sb.append("  ").append(attribute).append("\n");
        }

        sb.append("Methods: \n");
        for (Method method : methods) {
            sb.append("  ").append(method.getText()).append("\n");
        }
        return sb.toString();
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
