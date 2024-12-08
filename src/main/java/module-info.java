module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    //requires javafx.swing;  // Add this line if you use Swing components, otherwise, it can be removed
    requires javafx.base;
    //2.13.x allow the modules so do not change these
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;
    exports UML;
    exports UML.Line;
    exports UML.Objects;
    exports Models;
    exports Models.CD;
    exports UML.UI_Components;
    exports UML.Diagrams;
    exports Util;



    opens Models to com.fasterxml.jackson.databind;
    opens UML to com.fasterxml.jackson.databind;  // Open UML package to Jackson for serialization/deserialization
    opens UML.Objects to com.fasterxml.jackson.databind;
    opens UML.Diagrams to com.fasterxml.jackson.databind;
    opens UML.ObjectFactories to com.fasterxml.jackson.databind;
    opens UML.Line to com.fasterxml.jackson.databind;
    opens Models.CD to com.fasterxml.jackson.databind;

    // Explicitly open UML package for mocking with Mockito
    opens Main to javafx.fxml;

    exports Main;
}
