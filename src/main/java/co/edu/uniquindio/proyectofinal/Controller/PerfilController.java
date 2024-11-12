package co.edu.uniquindio.proyectofinal.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinal.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PerfilController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cargarImagen_bt;

    @FXML
    private ChoiceBox<?> categoria_cb;

    @FXML
    private TextField codigo_tf;

    @FXML
    private TextArea descripcion_ta;

    @FXML
    private Label direccion_lb;

    @FXML
    private Button editarr_bt;

    @FXML
    private Label identificacion_lb;

    @FXML
    private ImageView imagenVendedor_lb;

    @FXML
    private Label likes_lb;

    @FXML
    private Label listaAliadps_lb;

    @FXML
    private ListView<?> listaProductos_lb;

    @FXML
    private Label listaReseñas_lb;

    @FXML
    private Button muro_bt;

    @FXML
    private Button muro_bt1;

    @FXML
    private Label nombre_lb;

    @FXML
    private TextField nombre_tf;

    @FXML
    private TextField precio_tf;

    @FXML
    private Button publicar_bt;

    @FXML
    private Button solicitudes_bt;

    @FXML
    private Button principal_bt;


     @FXML
    void irAprincipal(ActionEvent event) {

    }

    @FXML
    void IrAEditar(ActionEvent event) throws IOException {
        App.setRoot("editar");

    }

    @FXML
    void cargarImageProducto(ActionEvent event) {

    }

    @FXML
    void irAActualizar(ActionEvent event) throws IOException {
        App.setRoot("actualizarPerfil");
    }

    @FXML
    void irASolicitudes(ActionEvent event) throws IOException {
        App.setRoot("solicitudes");

    }

    @FXML
    void irAmuro(ActionEvent event) throws IOException {
        App.setRoot("muroVendedor");

    }

    @FXML
    void realizarPublicacion(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
