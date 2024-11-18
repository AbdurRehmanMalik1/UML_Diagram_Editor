module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    //2.13.x allow the modules so do not change these
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;

    // Make the UML package accessible for Jackson to serialize/deserialize
    opens UML to com.fasterxml.jackson.databind;  // Open UML package to Jackson
    opens Models to com.fasterxml.jackson.databind;
    // Make the Main package (holding the application) accessible to JavaFX
    opens Main to javafx.fxml;



    exports Main;
    opens UML.Objects to com.fasterxml.jackson.databind;
    opens UML.Diagrams to com.fasterxml.jackson.databind;

}