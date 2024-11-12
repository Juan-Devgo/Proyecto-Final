package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;

import java.io.IOException;

import co.edu.uniquindio.proyectofinal.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SolicitudController {

    @FXML
    private Button aceptar_bt;

    @FXML
    private TableColumn<?, ?> cedula_t;

    @FXML
    private Button eliminar_bt;

    @FXML
    private TableView<?> listaSolicitudes_tv;

    @FXML
    private TableColumn<?, ?> nombre_t;

    @FXML
    private Button volver_bt;

    @FXML
    void aceptarSolicitud(ActionEvent event) {

    }

    @FXML
    void eliminarSolicitud(ActionEvent event) {

    }

    @FXML
    void cambiarPrincipal(ActionEvent event)throws IOException {
        App.setRoot("principal");
    }

}
