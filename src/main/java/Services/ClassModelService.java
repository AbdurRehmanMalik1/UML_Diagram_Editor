package Services;

import Models.ClassModel;
import Repositories.ClassModelRepository;

public class ClassModelService {
    final ClassModelRepository classModelRepository;
    public ClassModelService(){
        classModelRepository= new ClassModelRepository();
    }
    public int saveClass(ClassModel classModel){
        int returnedId = classModelRepository.save(classModel);
        //classModel.setId(returnedId);
        return returnedId;
    }
}
