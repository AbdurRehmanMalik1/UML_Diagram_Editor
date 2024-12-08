package CodeGeneration;

import Models.*;
import java.io.FileWriter;
import java.util.List;

import Models.CD.Attribute;
import Models.CD.Method; // Assuming Method class exists based on your previous dump

/**
 * CodeGenerator is responsible for generating code from model objects.
 * It supports generating TypeScript code for ClassModel and InterfaceModel objects.
 */
public class CodeGenerator {
    private String selectedPath;

    /**
     * Default constructor for CodeGenerator.
     */
    public CodeGenerator() {
    }

    /**
     * Generates code for all models in the provided list.
     *
     * @param models       the list of models to generate code for
     * @param selectedPath the path where generated code should be saved
     */
    public void generateAllCode(List<Model> models, String selectedPath) {
        this.selectedPath = selectedPath;
        for (Model model : models) {
            generateCode(model);
        }
    }

    /**
     * Generates code for a specific model.
     *
     * @param model the model for which to generate code
     */
    public void generateCode(Model model) {
        if (model instanceof ClassModel) {
            generateClassCode((ClassModel) model);
        } else if (model instanceof InterfaceModel) {
            generateInterfaceCode((InterfaceModel) model);
        } else {
            System.out.println("Unsupported model type for code generation.");
        }
    }

    /**
     * Generates TypeScript code for a class model.
     *
     * @param classModel the class model to generate code for
     */
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

    /**
     * Generates TypeScript code for associations in a model.
     *
     * @param model the model containing associations
     * @param code  the StringBuilder to append the association code to
     */
    private void generateAssociationCode(Model model, StringBuilder code) {
        // Get all outgoing associations
        List<AssociationModel> outgoingAssociations = model.getOutgoingAssociations();

        // Process only outgoing associations
        for (AssociationModel association : outgoingAssociations) {
            addAssociationCode(association, code);
        }
    }

    /**
     * Adds TypeScript code for a specific association.
     *
     * @param association the association to add code for
     * @param code        the StringBuilder to append the association code to
     */
    private void addAssociationCode(AssociationModel association, StringBuilder code) {
        Model relatedModel = association.getEndModel();

        if (relatedModel != null) {
            String associationType = association.getType();
            String relatedModelName = relatedModel instanceof ClassModel
                    ? ((ClassModel) relatedModel).getClassName()
                    : (relatedModel instanceof InterfaceModel
                    ? ((InterfaceModel) relatedModel).getInterfaceName()
                    : relatedModel.getClass().getSimpleName());
            String referenceName = decapitalize(relatedModelName);

            // Add TypeScript code for the association based on its type
            switch (associationType.toLowerCase()) {
                case "association", "aggregation", "composition":
                    code.append("\tprivate ").append(referenceName).append(": ").append(relatedModelName).append(";\n");
                    break;
                default:
                    System.out.println("Unknown association type: " + associationType);
            }
        } else {
            System.out.println("Skipping association with null related model.");
        }
    }

    /**
     * Generates TypeScript code for an interface model.
     *
     * @param interfaceModel the interface model to generate code for
     */
    private void generateInterfaceCode(InterfaceModel interfaceModel) {
        StringBuilder code = new StringBuilder();
        code.append("interface ").append(interfaceModel.getInterfaceName()).append(" {\n");

        // Iterate through the list of methods and append them to the code
        for (Method method : interfaceModel.getMethods()) {
            code.append(methodToTypeScript(method));
        }

        code.append("}\n");
        writeToFile(interfaceModel.getInterfaceName() + ".ts", code.toString());
    }

    /**
     * Converts a method to its TypeScript representation.
     *
     * @param method the method to convert
     * @return the TypeScript representation of the method
     */
    private String methodToTypeScript(Method method) {
        try {
            String params = extractParameters(method.getText());
            String returnType = method.getReturnType() != null ? ": " + method.getReturnType().toLowerCase() : "";
            String accessModifier = method.getAccessModifier().toLowerCase();
            String methodName = extractMethodName(method.getText()); // Extract the method name
            String typeScriptMethod = "";
            if (method.isAbstract()) {
                typeScriptMethod = accessModifier + " " + methodName + "(" + params + ") " + returnType + ";\n";
                typeScriptMethod = "\tabstract " + typeScriptMethod;
            } else {
                typeScriptMethod = "\t" + accessModifier + " " + methodName + "(" + params + ") " + returnType + ";\n";
            }
            return typeScriptMethod;
        } catch (Exception e) {
            System.out.println("Invalid Method");
            return "\tpublic defaultMethod() : void;\n";
        }
    }

    /**
     * Extracts the parameters from a method's signature.
     *
     * @param methodText the method's text representation
     * @return the parameters as a string
     */
    private String extractParameters(String methodText) {
        String params = methodText.substring(methodText.indexOf('(') + 1, methodText.lastIndexOf(')')).trim();
        return params.isEmpty() ? "" : params;
    }

    /**
     * Extracts the method name from a method's signature.
     *
     * @param methodText the method's text representation
     * @return the method name
     */
    private String extractMethodName(String methodText) {
        int startIndex = methodText.indexOf('(');
        if (startIndex == -1) {
            return ""; // Invalid format or no parameters
        }

        return methodText.substring(0, startIndex).trim();
    }

    /**
     * Writes the generated code to a file.
     *
     * @param fileName the name of the file to write to
     * @param content  the content to write to the file
     */
    private void writeToFile(String fileName, String content) {
        try {
            // Construct the full file path using the selected directory
            String filePath = selectedPath + "/" + fileName;

            // Write the content to the file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(content);
            }

            System.out.println("File written successfully: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decapitalizes the first character of the input string.
     *
     * @param input the string to decapitalize
     * @return the decapitalized string
     */
    private String decapitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}
