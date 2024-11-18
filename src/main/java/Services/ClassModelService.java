package Services;

import Models.ClassModel;
import Repositories.ClassModelRepository;

public class ClassModelService {
    ClassModelRepository classModelRepository;
    public ClassModelService(){
        classModelRepository= new ClassModelRepository();
    }
    public int saveClass(ClassModel classModel){
       return classModelRepository.save(classModel);
    }
}
