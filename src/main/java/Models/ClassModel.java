package Models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class ClassModel extends Model{

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String className;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<String> attributes;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<String> methods;


    public ClassModel(){
        className = "";
        attributes = new ArrayList<>();
        methods = new ArrayList<>();
    }
    public String getClassName() {
        return className;
    }
    public List<String> getAttributes() {
        return attributes;
    }

    public List<String> getMethods() {
        return methods;
    }
    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public void addAttribute(String attribute){
        attributes.add(attribute);
    }
    public void addMethod(String method){
        methods.add(method);
    }
    public void removeAttribute(String attribute){
        attributes.remove(attribute);
    }
    public void removeMethod(String method){
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
        for (String method : methods) {
            sb.append("  ").append(method).append("\n");
        }

        return sb.toString();
    }

}
