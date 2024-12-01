package Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.ArrayList;


@Entity
@Table(name="use_case")
public class UseCaseModel extends Model{
    @JsonInclude()
    @Column(name = "interface_name", nullable = false)
    private String useCaseName;

    public UseCaseModel(){
        super();
    }

    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }
}
