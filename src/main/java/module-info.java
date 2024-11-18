module org.example.scdproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive com.fasterxml.jackson.databind;
    requires java.desktop;

    // Make the UML package accessible for Jackson to serialize/deserialize
    opens UML to com.fasterxml.jackson.databind;  // Open UML package to Jackson
    opens Models to com.fasterxml.jackson.databind;
    // Make org.example.scdproject package accessible to JavaFX
    opens org.example.scdproject to javafx.fxml;

    exports org.example.scdproject;
}
