package CodeGeneration;

import Main.ClassController;
import Models.*;
import java.io.FileWriter;
import java.lang.Exception;
import java.util.List;
import Models.CD.Method; // Assuming Method class exists based on your previous dump

public class CodeGenerator {

    public CodeGenerator() {
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
        // String parentClass = classModel.getParentClass();
        code.append(classModel.isAbstract() ? "public abstract class " : "public class ");
        code.append(classModel.getClassName());
        // if (parentClass != null && !parentClass.isEmpty()) {
        //code.append(" extends ").append(parentClass);
        //}
        code.append(" {\n");

        // Add attributes
        for (String attribute : classModel.getAttributes()) {
            String[] parts = attribute.split(":");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String dataType = parts[1].trim();
                code.append("\tprivate ").append(dataType).append(" ").append(attributeName).append(";\n");
            }
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
            if(targetModel instanceof ClassModel) {
                String type = association.getType();
                System.out.println("Is a classModel");
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

    private String methodToJava(Method method) {
        StringBuilder methodCode = new StringBuilder();
        String[] parts = method.getText().split(":");
        if (parts.length == 2) {
            String methodName = parts[0].trim();
            String dataType = parts[1].trim();
            methodCode.append("\tpublic ").append(dataType).append(" ").append(methodName).append("() {\n");
            methodCode.append("\t\t// TODO: Implement this method\n");
            methodCode.append("\t}\n");
        }
        return methodCode.toString();
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