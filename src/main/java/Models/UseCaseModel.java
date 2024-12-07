package Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.ArrayList;


public class UseCaseModel extends Model{
    @JsonInclude()
    private String useCaseName;

    public UseCaseModel(){
        super();
    }
    public UseCaseModel(UseCaseModel other){
        super(other);
        this.useCaseName = other.useCaseName;
    }
    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }
}
