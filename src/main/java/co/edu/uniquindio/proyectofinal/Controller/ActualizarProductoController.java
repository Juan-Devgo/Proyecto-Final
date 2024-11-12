package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;
import java.io.IOException;
import co.edu.uniquindio.proyectofinal.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ActualizarProductoController {

    @FXML
    private Button actualizar_bt;

    @FXML
    private TextField categoria_tf;

    @FXML
    private TextField codigo_tf;

    @FXML
    private TextArea descripcion_ta;

    @FXML
    private TextField nombre_tf;

    @FXML
    private TextField precio_tf;

    @FXML
    private Button volver_bt;

    @FXML
    void actualizarProducto(ActionEvent event) {

    }

    @FXML
    void cambiarMuroProducto(ActionEvent event)throws IOException {
        App.setRoot("muroProducto");
    }

}
