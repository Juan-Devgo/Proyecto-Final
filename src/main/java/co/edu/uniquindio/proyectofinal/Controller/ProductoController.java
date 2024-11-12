package co.edu.uniquindio.proyectofinal.Controller; 

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinal.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductoController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imagenProducto_iv;

    @FXML
    private Button muroProducto_bt;

    @FXML
    private Label nombreProducto_lb;

    @FXML
    void irAMuroProducto(ActionEvent event) throws IOException {
        // implementar logica para instancia 
        App.setRoot("muroProducto");
    }

    @FXML
    void initialize() {
        
    }

}
