package Models;

import Models.CD.Method;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;


public class InterfaceModel extends Model {

    @JsonInclude()

    private String interfaceName;

    @JsonInclude()
    private List<Method> methods;

    public InterfaceModel() {
        super();
        methods = new ArrayList<>();
    }
    public InterfaceModel(InterfaceModel other) {
        super(other);
        this.interfaceName = other.interfaceName;
        this.methods = new ArrayList<>(other.methods);
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("Interface Name: ").append(interfaceName).append("\n");
//
//        sb.append("Methods: \n");
//        for (String method : methods) {
//            sb.append("  ").append(method).append("\n");
//        }
//
//        return sb.toString();
//    }
}
