module progetto{
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens progetto to javafx.fxml;
    opens progetto.controllers to javafx.fxml;
    
    exports progetto.controllers;
    exports progetto;
}
