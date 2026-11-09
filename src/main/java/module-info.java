module com.trpl {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.trpl to javafx.fxml;
    exports com.trpl;
}
