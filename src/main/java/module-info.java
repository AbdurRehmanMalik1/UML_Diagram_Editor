module org.example.scdproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;

    // Make the UML package accessible for Jackson to serialize/deserialize
    opens UML to com.fasterxml.jackson.databind;  // Open UML package to Jackson
    opens Models to com.fasterxml.jackson.databind;
    // Make org.example.scdproject package accessible to JavaFX
    opens org.example.scdproject to javafx.fxml;

    exports org.example.scdproject;
    opens UML.Objects to com.fasterxml.jackson.databind;
    opens UML.Diagrams to com.fasterxml.jackson.databind;

}