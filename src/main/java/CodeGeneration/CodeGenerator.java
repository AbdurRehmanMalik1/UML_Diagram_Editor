package CodeGeneration;

import Models.*;
import java.io.FileWriter;
import java.util.List;

import Models.CD.Attribute;
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
        code.append(classModel.isAbstract() ? "abstract class " : "class ");
        code.append(classModel.getClassName()).append(" {\n");

        // Add attributes using the refactored Attribute class
        for (Attribute attribute : classModel.getAttributes()) {
            String dataType = attribute.getDataType();
            String accessModifier = attribute.getAccessModifier().toLowerCase();
            String attributeName = attribute.getName();

            // Maintain the original casing for the data type
            code.append("\t").append(accessModifier).append(" ").append(attributeName).append(" : ")
                    .append(dataType).append(";\n");
        }

        // Add associations
        generateAssociationCode(classModel, code);

        // Add methods
        for (Method method : classModel.getMethods()) {
            code.append(methodToTypeScript(method));
        }

        code.append("}\n");

        writeToFile(classModel.getClassName() + ".ts", code.toString());
    }

    private void generateAssociationCode(ClassModel classModel, StringBuilder code) {
        List<AssociationModel> associations = classModel.getOutgoingAssociations();

        for (AssociationModel association : associations) {
            Model targetModel = association.getEndModel();
            if (targetModel instanceof ClassModel) {
                String type = association.getType();
                switch (type.toLowerCase()) {
                    case "association":
                        code.append("\tprivate ").append(decapitalize(((ClassModel) targetModel).getClassName()))
                                .append(": ").append(((ClassModel) targetModel).getClassName()).append(";\n");
                        break;
                    case "composition":
                        code.append("\tprivate ").append(((ClassModel) targetModel).getClassName())
                                .append(": ").append(((ClassModel) targetModel).getClassName()).append(" = new ")
                                .append(((ClassModel) targetModel).getClassName()).append("();\n");
                        break;
                    case "aggregation":
                        code.append("\tprivate ").append(((ClassModel) targetModel).getClassName())
                                .append(": ").append(((ClassModel) targetModel).getClassName()).append(";\n");
                        break;
                    default:
                        System.out.println("Unknown association type: " + type);
                }
            }
        }
    }

    private void generateInterfaceCode(InterfaceModel interfaceModel) {
        StringBuilder code = new StringBuilder();
        code.append("interface ").append(interfaceModel.getInterfaceName()).append(" {\n");

        for (String method : interfaceModel.getMethods()) {
            code.append("\t").append(method).append(";\n");
        }

        code.append("}\n");
        writeToFile(interfaceModel.getInterfaceName() + ".ts", code.toString());
    }

    private String methodToTypeScript(Method method) {
        String params = extractParameters(method.getText());
        String returnType = method.getReturnType() != null ? ": " + method.getReturnType().toLowerCase() : "";
        String accessModifier = method.getAccessModifier().toLowerCase();
        String methodName = extractMethodName(method.getText()); // Extract the method name

        String typeScriptMethod = "\t" + accessModifier + " " + methodName + "(" + params + ")" + returnType + ";\n";
        if (method.isAbstract()) {
            typeScriptMethod = "\tabstract " + typeScriptMethod;
        }

        return typeScriptMethod;
    }

    private String extractParameters(String methodText) {
        String params = methodText.substring(methodText.indexOf('(') + 1, methodText.lastIndexOf(')')).trim();
        return params.isEmpty() ? "" : params.replaceAll(":", "");
    }

    private String extractMethodName(String methodText) {
        int startIndex = methodText.indexOf('(');
        if (startIndex == -1) {
            return ""; // Invalid format or no parameters
        }

        String methodName = methodText.substring(0, startIndex).trim();
        return methodName;
    }

    private void writeToFile(String fileName, String content) {
        System.out.println(content);
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
