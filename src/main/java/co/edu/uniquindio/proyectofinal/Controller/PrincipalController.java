package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.App;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PrincipalController {

    @FXML
    private Button buscarProductos_bt;

    @FXML
    private Button buscarVendedores_bt;

    @FXML
    private Button cambiarChat_bt;

    @FXML
    private ChoiceBox<?> categoria_cb;

    @FXML
    private TextField cedula_tf;

    @FXML
    private TextField codigo_tf;

    @FXML
    private GridPane muroPublicaciones_gp;

    @FXML
    private TextField nombreProducto_tf;

    @FXML
    private TextField nombreVendedor_tf;

    @FXML
    private Button perfil_bt;

    @FXML
    private VBox vendedores_vb;

    @FXML
    private Button cambiarSolicitudes_bt;

    @FXML
    void buscarProductos(ActionEvent event)throws IOException {

    }

    @FXML
    void buscarVendedores(ActionEvent event)throws IOException {

    }

    @FXML
    void cambiarChat(ActionEvent event)throws IOException {
        App.setRoot("chat");
    }

    @FXML
    void cambiarPerfil(ActionEvent event)throws IOException {
        App.setRoot("perfil");
    }

    @FXML
    void cambiarSolicitudes(ActionEvent event)throws IOException {
        App.setRoot("solicitud");
    }

}