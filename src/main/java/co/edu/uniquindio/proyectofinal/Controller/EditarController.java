package co.edu.uniquindio.proyectofinal.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EditarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button actualizar_bt;

    @FXML
    private TableColumn<?, ?> codigo_t;

    @FXML
    private TableView<?> listaProductos_t;

    @FXML
    private TableColumn<?, ?> nombre_t;

    @FXML
    private Button volverAMuro_bt;

    @FXML
    void irAActualizar(ActionEvent event) throws IOException {

    }

    @FXML
    void irAMuroVendedor(ActionEvent event) {

    }

    @FXML
    void initialize() {
        
    }

}
