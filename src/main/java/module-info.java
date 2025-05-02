module org.example.clientsevermsgexample {
    requires javafx.controls;
    requires javafx.fxml;



    opens org.example.clientsevermsgexample to javafx.fxml;

    exports org.example.clientsevermsgexample.model;
    opens org.example.clientsevermsgexample.model to javafx.fxml;
    exports org.example.clientsevermsgexample.controller;
    opens org.example.clientsevermsgexample.controller to javafx.fxml;

}