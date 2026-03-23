module com.example.lab3_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires metadata.extractor;

    opens com.example.lab3_fx to javafx.fxml;
    exports com.example.lab3_fx;
}