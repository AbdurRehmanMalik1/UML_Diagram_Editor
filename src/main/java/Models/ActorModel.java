package Models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ActorModel extends Model {
    @JsonInclude()
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
