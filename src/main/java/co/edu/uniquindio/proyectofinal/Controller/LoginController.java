package co.edu.uniquindio.proyectofinal.Controller;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinal.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField identificacion_tf;

    @FXML
    private Button login_bt;

    @FXML
    private Button registro_bt;

    @FXML
    private ChoiceBox<String> tipoUsuario_cb;
    private String[] tiposUsuario = {"Administrador", "Vendedor"};

    @FXML
    void irARegistro(ActionEvent event) throws IOException {
         App.setRoot("registro");
    }

    @FXML
    void loggearUsuario(ActionEvent event) throws IOException{
        String identificacion = identificacion_tf.getText();
        // Validacion
         App.setRoot("principal");
    }

    @FXML
    void initialize() {

       
    }

}
