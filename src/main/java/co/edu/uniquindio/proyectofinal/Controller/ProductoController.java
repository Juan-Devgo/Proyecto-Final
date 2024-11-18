package co.edu.uniquindio.proyectofinal.Controller; 

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductoController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label fecha_lb;

    @FXML
    private ImageView imagenProducto_iv;

    @FXML
    private Button muroProducto_bt;

    @FXML
    private Label nombreProducto_lb;

    private Producto productoActual;

    private Vendedor vendedorEnLinea;

    private Administrador adminEnLinea;

    private Utilidades utilidades = Utilidades.getInstancia();

    @FXML
    void irAMuroProducto(ActionEvent event) throws IOException {
        if(vendedorEnLinea != null){

            FXMLLoader loader = AppServer.getFXMLLoader("muroProducto");
            Parent root = loader.load();
            MuroProductoController muroProductoController = loader.getController();
            muroProductoController.establecerProductoActual(productoActual);
            muroProductoController.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 498, 400, productoActual.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en producto.");
        }
    }

    @FXML
    void initialize() {
        
    }

    public void establecerProductoActual(Producto producto) {
        if (producto != null){
            productoActual = producto;
            imagenProducto_iv.setImage(new Image(productoActual.getRutaImagen()));
            nombreProducto_lb.setText(productoActual.getNombre());
            fecha_lb.setText(productoActual.getFechaYHoraPublicacionGUI());
        } else{
            JOptionPane.showMessageDialog(null, "Error intentando establecer el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en producto.");
        }
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en producto.");
        }
    }

    public void establecerAdminEnLinea(Administrador admin) {
        if(admin != null){
            adminEnLinea = admin;
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el administrador", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al administrador, el administrador es nulo en producto.");
        }
    }
}