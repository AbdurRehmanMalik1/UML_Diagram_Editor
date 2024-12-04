package Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@Entity
@Table(name = "actor")
public class ActorModel extends Model {
    @JsonInclude()
    @Column(name = "actor_name", nullable = false)
    private String actorName;

    public ActorModel() {
        super();
    }
    public ActorModel(ActorModel other){
        super(other);
        this.actorName = other.actorName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
