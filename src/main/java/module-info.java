module org.example.scdproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.example.scdproject to javafx.fxml;
    exports org.example.scdproject;
}