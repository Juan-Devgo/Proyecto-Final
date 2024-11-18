package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.AppServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.scene.control.ChoiceBox;

public class ActualizarProductoController {

    @FXML
    private Button actualizar_bt;

    @FXML
    private Button cargarImagen_bt;

    @FXML
    private ChoiceBox<String> categoria_cb;

    @FXML
    private TextField codigo_tf;

    @FXML
    private TextArea descripcion_tf;

    @FXML
    private TextField nombre_tf;

    @FXML
    private TextField precio_tf;

    @FXML
    private Button volver_bt;

    private Producto productoActual;

    private Vendedor vendedorEnLinea;

    private Utilidades utilidades = Utilidades.getInstancia();

    private Image image;

    private String[] categorias = {"Tecnología", "Hogar", "Ropa", "Juguetes", "Vehículos"};

    @FXML
    void initialize(){
        categoria_cb.getItems().setAll(categorias);
        codigo_tf.setDisable(true);
    }

    @FXML
    void actualizarProducto(ActionEvent event) {
        if(productoActual != null) {
            utilidades.log("Actualizando producto...");
            String nombre = nombre_tf.getText();
            Double precio = Double.parseDouble(precio_tf.getText());
            String descripcion = descripcion_tf.getText();
            String categoria = categoria_cb.getValue();

            if(nombre != null && !nombre.isBlank()) {
                productoActual.setNombre(nombre);
            }

            if(!precio.isNaN()) {
                productoActual.setPrecio(precio);
            }

            if(descripcion != null && !descripcion.isBlank()) {
                productoActual.setDescripcion(descripcion);
            }

            if(categoria != null && !categoria.isBlank()) {
                productoActual.setCategoria(categoria);
            }

            if(!image.getUrl().equals(productoActual.getRutaImagen())){
                productoActual.setRutaImagen(image.getUrl());
            }

            utilidades.log("Producto actualizado.");
        } else{
            JOptionPane.showMessageDialog(null, "Error intentando establecer el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en actualizarProducto.");
        }
    }

    @FXML
    void cambiarMuroProducto(ActionEvent event)throws IOException {
        if (vendedorEnLinea != null) {
            
            utilidades.log("Cambiando a ventana muroProducto.");
            FXMLLoader loader = AppServer.getFXMLLoader("muroProducto");
            Parent root = loader.load();
            MuroProductoController muroProductoController = loader.getController();
            muroProductoController.establecerVendedorEnLinea(vendedorEnLinea);
            muroProductoController.establecerProductoActual(productoActual);

            AppServer.setRoot(root, 498, 400, productoActual.getNombre());
        } else{
            JOptionPane.showMessageDialog(null, "Error intentando establecer el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en actualizarProducto.");
        }
    }

    @FXML
    void cargarImagen(ActionEvent event) {
        utilidades.log("Abriendo interfaz para cambiar de imágen para " + productoActual.getNombre() + ".");

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

    public void establecerProductoActual(Producto producto) {
        utilidades.log("Estableciendo producto actual...");
        if (producto != null){
            productoActual = producto;
            nombre_tf.setText(productoActual.getNombre());
            codigo_tf.setText(productoActual.getCodigo());
            precio_tf.setText(productoActual.getPrecio().toString());
            descripcion_tf.setText(productoActual.getDescripcion());
            categoria_cb.setValue(productoActual.getCategoria());

        } else{
            JOptionPane.showMessageDialog(null, "Error intentando establecer el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en actualizarProducto.");
        }
        utilidades.log("Producto actual establecido.");
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        utilidades.log("Estableciendo vendedor en línea...");
        if (vendedor != null) {
            vendedorEnLinea = vendedor;
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo.");
        }
        utilidades.log("Vendedor en línea establecido.");
    }
}