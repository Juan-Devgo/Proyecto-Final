module co.edu.uniquindio.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.web;
    requires org.json;

    opens co.edu.uniquindio.proyectofinal to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal;
    exports co.edu.uniquindio.proyectofinal.Controller;
    opens co.edu.uniquindio.proyectofinal.Controller to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.Model;
    opens co.edu.uniquindio.proyectofinal.Model to java.desktop;
}