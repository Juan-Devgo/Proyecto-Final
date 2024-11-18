package co.edu.uniquindio.proyectofinal.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.AppServer;
import co.edu.uniquindio.proyectofinal.Model.*;
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

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    @FXML
    void irARegistro(ActionEvent event) throws IOException {
        utilidades.log("Cambiando ventana a registro.");
        AppServer.setRoot("registro", 338, 462, "Registro");
    }

    @FXML
    void loggearUsuario(ActionEvent event) throws IOException{
        utilidades.log("Loggeando usuario...");
        String identificacion = identificacion_tf.getText();
        String seleccionTipoUsuario = tipoUsuario_cb.getValue();
        FXMLLoader loader; Parent root;

        if(seleccionTipoUsuario != null && identificacion != null && !identificacion.isBlank()){
            switch (seleccionTipoUsuario) {
                case "Administrador":
                    if (utilidades.getAdmin("cedula").equals(identificacion)) {
                        Administrador admin = marketPlace.loginAdministrador(identificacion);
                        utilidades.log("Administrador encontrado.");

                        loader = AppServer.getFXMLLoader("adminPrincipal");
                        root = loader.load();
                        enviarAdminEnLinea(loader, admin);

                        AppServer.setRoot(root, 1115, 599, admin.getNombre());
                        utilidades.log("Administrador loggeado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el Administrador", seleccionTipoUsuario, JOptionPane.ERROR_MESSAGE);
                        utilidades.log(Level.WARNING, "Error, administrador no encontrado.");
                        identificacion_tf.setText("");
                    }
                    break;

                case "Vendedor":
                    if (marketPlace.buscarVendedorPorCedula(identificacion).isPresent()) {
                        Vendedor vendedor = marketPlace.buscarVendedorPorCedula(identificacion).get();
                        utilidades.log("Vendedor encontrado.");

                        loader = AppServer.getFXMLLoader("principal");
                        root = loader.load();
                        PrincipalController controller = loader.getController();
                        controller.establecerVendedorEnLinea(vendedor);

                        AppServer.setRoot(root, 1115, 599, vendedor.getNombre());
                        utilidades.log("Vendedor loggeado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el Vendedor.", seleccionTipoUsuario, JOptionPane.ERROR_MESSAGE);
                        utilidades.log(Level.WARNING, "Error, vendedor no encontrado.");
                        identificacion_tf.setText("");
                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Error inesperado, tipo de usuario no reconocido.", seleccionTipoUsuario, JOptionPane.ERROR_MESSAGE);
                    utilidades.log(Level.SEVERE, "Error inesperado.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ambas casillas son obligatorias.", "Ingrese los datos solicitados", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.WARNING, "El usuario no envió correctamente los datos en login.");
        }
    }

    @FXML
    void initialize() {
        tipoUsuario_cb.getItems().setAll(tiposUsuario);
    }

    private void enviarAdminEnLinea(FXMLLoader loader, Administrador admin) throws IOException {
        AdminPrincipalController adminPrincipalController = loader.getController();
        adminPrincipalController.establecerAdminEnLinea(admin);
    }
}