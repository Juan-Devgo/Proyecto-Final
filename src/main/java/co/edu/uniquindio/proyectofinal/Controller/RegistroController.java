package co.edu.uniquindio.proyectofinal.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.AppServer;
import co.edu.uniquindio.proyectofinal.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

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

    @FXML
    private Button cargarImagen_bt;

    private String[] tiposUsuario = {"Administrador", "Vendedor"};

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    private Image image = new Image("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final//src/main/resources/imagenes/usuarioPredeterminado.png");

    @FXML
    void irALogin(ActionEvent event)throws IOException{
        AppServer.setRoot("login", 513, 407, "Login");
    }

    @FXML
    void cargarImagenPerfil(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imágen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivoSeleccionado = fileChooser.showOpenDialog(cargarImagen_bt.getScene().getWindow());
        if (archivoSeleccionado != null) {
            image = new Image(archivoSeleccionado.toURI().toString());
        } else {
            JOptionPane.showMessageDialog(null, "Error, no se seleccionó el archivo o no se cargó.", "Error cargando archivo", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.WARNING, "El archivo no se seleccionó o no se cargó en registro.");
        }
    }

    @FXML
    void registrarUsuario(ActionEvent event) throws IOException {
        String nombre = nombre_tf.getText();
        String apellido = apellido_tf.getText();
        String identificacion = identificacion_tf.getText();
        String direccion = direccion_tf.getText();
        String seleccionTipoUsuario = tipoUsuario_cb.getValue();

        Parent root; FXMLLoader loader;

        if(nombre != null && !nombre.isBlank() && apellido != null && !apellido.isBlank() && identificacion != null && !identificacion.isBlank() && direccion != null && !direccion.isBlank() && seleccionTipoUsuario != null) {
            switch (seleccionTipoUsuario) {
                case "Administrador":
                    marketPlace.crearAdministrador(nombre, apellido, identificacion);
                    Administrador admin = marketPlace.loginAdministrador(identificacion);

                    loader = AppServer.getFXMLLoader("adminPrincipal");
                    root = loader.load();
                    enviarAdminEnLinea(loader, admin);

                    AppServer.setRoot(root, 1115, 599, admin.getNombre());
                    utilidades.log("Administrador registrado y loggeado.");
                    break;
                case "Vendedor":
                    marketPlace.crearVendedor(nombre, apellido, identificacion, direccion);
                    Vendedor vendedor = marketPlace.loginVendedor(identificacion);
                    vendedor.setRutaImagen(image.getUrl());

                    loader = AppServer.getFXMLLoader("principal");
                    root = loader.load();
                    enviarVendedorEnLinea(loader, vendedor);

                    AppServer.setRoot(root, 1115, 599, vendedor.getNombre());
                    utilidades.log("Vendedor registrado y loggeado.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error inesperado, tipo de usuario no reconocido.", seleccionTipoUsuario, JOptionPane.ERROR_MESSAGE);
                    utilidades.log(Level.SEVERE, "Error inesperado.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Todas las casillas son obligatorias.", "Ingrese los datos solicitados", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.WARNING, "El usuario no envió correctamente los datos en registro.");
        }
    }

    @FXML
    void initialize() {
       this.tipoUsuario_cb.getItems().addAll(tiposUsuario);
    }

    private void enviarVendedorEnLinea(FXMLLoader loader, Vendedor vendedor) throws IOException {
        PrincipalController principalController = loader.getController();
        principalController.establecerVendedorEnLinea(vendedor);
    }

    private void enviarAdminEnLinea(FXMLLoader loader, Administrador admin) throws IOException {
        AdminPrincipalController adminPrincipalController = loader.getController();
        adminPrincipalController.establecerAdminEnLinea(admin);
    }
}