package co.edu.uniquindio.proyectofinal.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinal.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class RegistroController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField apellido_tf;

    @FXML
    private TextField direccion_tf;

    @FXML
    private TextField identificacion_tf;

    @FXML
    private Button login_bt;

    @FXML
    private TextField nombre_tf;

    @FXML
    private Button registrarse_bt;

    @FXML
    private ChoiceBox<String> tipoUsuario_cb;
    private String[] tiposUsuario = {"Administrador", "Vendedor"};;

      @FXML
    private Button cargarImagen_bt;


    @FXML
    void irALogin(ActionEvent event)throws IOException{
        App.setRoot("login");

    }

     @FXML
    void cargarImagenPerfil(ActionEvent event) {

    }

    @FXML
    void registrarUsuario(ActionEvent eventt){
        String nombre = nombre_tf.getText();
        String apellido = apellido_tf.getText(); 
        String identificacion = identificacion_tf.getText(); 
        String direccion = direccion_tf.getText(); 

        if((nombre != null && !nombre.isBlank()) && (apellido != null && apellido.isBlank()) && (identificacion != null && !identificacion.isBlank()) && (direccion != null && direccion.isBlank())){
            if( tipoUsuario_cb.getValue() == "Vendedor"){
            }
            else{
            }
        }
    }

    @FXML
    void initialize() {
       this.tipoUsuario_cb.getItems().addAll(tiposUsuario);

    }
}