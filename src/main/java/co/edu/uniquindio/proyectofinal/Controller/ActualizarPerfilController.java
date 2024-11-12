package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.App;
import co.edu.uniquindio.proyectofinal.Model.*;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ActualizarPerfilController {

    @FXML
    private Button actualizar_bt;

    @FXML
    private TextField apellido_tf;

    @FXML
    private TextField cedula_tf;

    @FXML
    private TextField direccion_tf;

    @FXML
    private TextField nombre_tf;

    @FXML
    private Button volver_bt;

    @FXML
    void actualizarUsuario(ActionEvent event) {

    }

    @FXML
    void cambiarPerfil(ActionEvent event)throws IOException {
        App.setRoot("perfil");
    }

}
