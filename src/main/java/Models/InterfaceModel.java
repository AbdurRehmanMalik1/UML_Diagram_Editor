package Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interface")
public class InterfaceModel extends Model {

    @JsonInclude()
    @Column(name = "interface_name", nullable = false)
    private String interfaceName;

    @JsonInclude()
    @ElementCollection
    @CollectionTable(name = "interface_methods", joinColumns = @JoinColumn(name = "interface_id"))
    @Column(name = "method")
    private List<String> methods;

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

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    public void removeMethod(String method) {
        methods.remove(method);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Interface Name: ").append(interfaceName).append("\n");

        sb.append("Methods: \n");
        for (String method : methods) {
            sb.append("  ").append(method).append("\n");
        }

        return sb.toString();
    }
}
