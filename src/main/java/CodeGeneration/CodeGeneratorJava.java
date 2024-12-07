package CodeGeneration;

import Models.*;
import java.io.FileWriter;
import java.util.List;

import Models.CD.Attribute;
import Models.CD.Method; // Assuming Method class exists based on your previous dump

public class CodeGeneratorJava {

    public CodeGeneratorJava() {
    }

    public void generateAllCode(List<Model> models) {
        for (Model model : models) {
            generateCode(model);
        }
    }

    public void generateCode(Model model) {
        if (model instanceof ClassModel) {
            generateClassCode((ClassModel) model);
        } else if (model instanceof InterfaceModel) {
            generateInterfaceCode((InterfaceModel) model);
        } else {
            System.out.println("Unsupported model type for code generation.");
        }
    }

    private void generateClassCode(ClassModel classModel) {
        StringBuilder code = new StringBuilder();

        // Handle inheritance
        code.append(classModel.isAbstract() ? "public abstract class " : "public class ");
        code.append(classModel.getClassName());
        code.append(" {\n");

        // Add attributes using the refactored Attribute class
        for (Attribute attribute : classModel.getAttributes()) {
            code.append("\t")
                    .append(attribute.getAccessModifier())
                    .append(" ")
                    .append(attribute.getDataType())
                    .append(" ")
                    .append(attribute.getName())
                    .append(";\n");
        }

        // Add associations
        generateAssociationCode(classModel, code);

        // Add methods
        for (Method method : classModel.getMethods()) {
            code.append(methodToJava(method));
        }

        code.append("}\n");

        writeToFile(classModel.getClassName() + ".java", code.toString());
    }

    private void generateAssociationCode(ClassModel classModel, StringBuilder code) {
        List<AssociationModel> associations = classModel.getOutgoingAssociations();

        for (AssociationModel association : associations) {
            Model targetModel = association.getEndModel();
            if (targetModel instanceof ClassModel) {
                String type = association.getType();
                switch (type.toLowerCase()) {
                    case "association":
                        code.append("\tprivate ").append(((ClassModel) targetModel).getClassName())
                                .append(" ").append(decapitalize(((ClassModel) targetModel).getClassName()))
                                .append(";\n");
                        break;
                    case "composition":
                        code.append("\tprivate final ").append(targetModel.getClass().getSimpleName())
                                .append(" ").append(decapitalize(targetModel.getClass().getSimpleName()))
                                .append(" = new ").append(targetModel.getClass().getSimpleName()).append("();\n");
                        break;
                    case "aggregation":
                        code.append("\tprivate ").append(targetModel.getClass().getSimpleName())
                                .append(" ").append(decapitalize(targetModel.getClass().getSimpleName()))
                                .append(";\n");
                        break;
                    default:
                        System.out.println("Unknown association type: " + type);
                }
            }
        }
    }

    private void generateInterfaceCode(InterfaceModel interfaceModel) {
        StringBuilder code = new StringBuilder();
        code.append("public interface ").append(interfaceModel.getInterfaceName()).append(" {\n");

        for (String method : interfaceModel.getMethods()) {
            code.append("\t").append(method).append(";\n");
        }

        code.append("}\n");
        writeToFile(interfaceModel.getInterfaceName() + ".java", code.toString());
    }

    private  String methodToJava(Method method){
        String out =  "\t"+method.getAccessModifier() +" "+ method.getReturnType()+"\n";
        return out;
    }





    private void writeToFile(String fileName, String content) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String decapitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}
