package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.AppServer;
import co.edu.uniquindio.proyectofinal.Model.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class ActualizarPerfilController {

    @FXML
    private Button actualizar_bt;

    @FXML
    private Button cargarImagen_bt;

    @FXML
    private TextField apellido_tf;

    @FXML
    private TextField cedula_tf;

    @FXML
    private TextField direccion_tf;

    @FXML
    private TextField nombre_tf;

    @FXML
    private Button volver_bt;

    private Vendedor vendedorEnLinea;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    private Image image;

    @FXML 
    void initialize(){
        utilidades.log("Inicializando " + getClass().getName() + "...");
    }

    @FXML
    void actualizarUsuario(ActionEvent event) {
        utilidades.log("Actualizando vendedor...");

        String nombre = nombre_tf.getText();
        String apellido = apellido_tf.getText();
        String cedula = cedula_tf.getText();
        String direccion = direccion_tf.getText();

        if(nombre != null && !nombre.isBlank()){
            vendedorEnLinea.setNombre(nombre);
        } 

        if(apellido != null && !apellido.isBlank()){
            vendedorEnLinea.setApellido(apellido);
        }

        if(marketPlace.buscarUsuarioPorCedula(cedula).isEmpty() || cedula.equals(vendedorEnLinea.getCedula())){
            if(cedula != null && !cedula.isBlank()){
                vendedorEnLinea.setCedula(cedula);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Esta cédula ya está registrada por otro usuario", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.WARNING, "El usuario intentó actualizar su cédula con otra ya registrada.");
        }

        if(direccion != null && !direccion.isBlank()){
            vendedorEnLinea.setDireccion(direccion);
        }

        if(!image.getUrl().equals(vendedorEnLinea.getRutaImagen())){
            vendedorEnLinea.setRutaImagen(image.getUrl());
        }

        utilidades.log("Vendedor actualizado.");
    }

    @FXML
    void cambiarPerfil(ActionEvent event)throws IOException {
        utilidades.log("Cambiando a ventana perfil.");

        FXMLLoader loader = AppServer.getFXMLLoader("perfil");
        Parent root = loader.load();
        PerfilController perfilController = loader.getController();
        perfilController.establecerVendedorEnLinea(vendedorEnLinea);     

        AppServer.setRoot(root, 709, 539, vendedorEnLinea.getNombre());
    }

    @FXML
    void cargarImagen(ActionEvent event) {
        utilidades.log("Abriendo interfaz para cambiar de imágen para " + vendedorEnLinea.getNombre() + ".");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivoSeleccionado = fileChooser.showOpenDialog(cargarImagen_bt.getScene().getWindow());
        if (archivoSeleccionado != null) {
            image = new Image(archivoSeleccionado.toURI().toString());
            utilidades.log("Imagen lista para ser establecida.");
        } else {
            JOptionPane.showMessageDialog(null, "Error, no se seleccionó el archivo o no se cargó.", "Error cargando archivo", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.WARNING, "El archivo no se seleccionó o no se cargó en perfil.");
        }
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        utilidades.log("Estableciendo vendedor en línea en actualizarPerfil...");
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            nombre_tf.setText(vendedorEnLinea.getNombre());
            apellido_tf.setText(vendedorEnLinea.getApellido());
            cedula_tf.setText(vendedorEnLinea.getCedula());
            direccion_tf.setText(vendedorEnLinea.getDireccion());
            image = new Image(vendedorEnLinea.getRutaImagen());
              
        } else {
            JOptionPane.showMessageDialog(null, "Error ", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en actualizarPerfil.");
        }
        utilidades.log("Vendedor en línea establecido.");
    }
}