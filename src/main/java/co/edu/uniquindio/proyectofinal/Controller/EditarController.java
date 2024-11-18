package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.Model.Exceptions.ProductoNoEncontradoException;
import co.edu.uniquindio.proyectofinal.AppServer;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class EditarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button actualizar_bt;

     @FXML
    private Button eliminar_bt;

    @FXML
    private ListView<Producto> listaProductos_lv;

    @FXML
    private Button volverAMuro_bt;

    private Vendedor vendedorEnLinea;

    private Producto productoSeleccionado;

    private Utilidades utilidades = Utilidades.getInstancia();

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    @FXML
    void eliminarProducto(ActionEvent event) {
        if (productoSeleccionado != null) {
            utilidades.log("Eliminando producto: " + productoSeleccionado + " .");
            try{
                marketPlace.eliminarProducto(vendedorEnLinea, productoSeleccionado);
            } catch (ProductoNoEncontradoException e) {
                vendedorEnLinea.getProductos().remove(productoSeleccionado);
            }
            utilidades.log("Producto eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            utilidades.log(Level.WARNING, "El usuario no seleccionó correctamente el producto en editar.");
        }
    }
    
    @FXML
    void irAActualizar(ActionEvent event) throws IOException {
        utilidades.log("Cambiando ventana a actualizar.");
        if (productoSeleccionado != null) {

            FXMLLoader loader = AppServer.getFXMLLoader("actualizarProducto");
            Parent root = loader.load();
            ActualizarProductoController controller = loader.getController();
            controller.establecerVendedorEnLinea(vendedorEnLinea);
            controller.establecerProductoActual(productoSeleccionado);

            AppServer.setRoot(root, 400, 400, productoSeleccionado.getNombre());
             
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            utilidades.log(Level.WARNING, "El usuario no seleccionó correctamente el producto en editar.");
        }
    }
    
    @FXML
    void irAMuroVendedor(ActionEvent event)throws IOException {
        utilidades.log("Cambiando ventana a MuroVendedor.");
        if(vendedorEnLinea != null) {

        FXMLLoader loader = AppServer.getFXMLLoader("muroVendedor"); 
        Parent root = loader.load(); 
        MuroVendedorController muroVendedorController = loader.getController();
        muroVendedorController.establecerVendedorEnLinea(vendedorEnLinea);
        AppServer.setRoot(root, 735, 519, vendedorEnLinea.getNombre());
        
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en editar.");
        }
    }

    @FXML
    void irAPerfil(ActionEvent event) throws IOException {
        utilidades.log("Cambiando ventana a perfil");
        if(vendedorEnLinea != null){

        FXMLLoader loader = AppServer.getFXMLLoader("perfil"); 
        Parent root = loader.load(); 
        PerfilController perfilController = loader.getController();
        perfilController.establecerVendedorEnLinea(vendedorEnLinea);

        AppServer.setRoot(root, 709, 539, vendedorEnLinea.getNombre());
        
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en editar.");
        }
    }

    public void establecerVendedorEnLinea(Vendedor vendedor)throws IOException {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            inicializarListaProductos(vendedorEnLinea.getProductos());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en editar.");
        }
    }

    @FXML
    void initialize() {
        
    }

    void inicializarListaProductos(LinkedList<Producto> productos)throws IOException {
        if(vendedorEnLinea != null){
            
            listaProductos_lv.getItems().setAll(productos);
            listaProductos_lv.setOnMouseClicked(new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent event) { 
                    if (event.getClickCount() == 2) { 
                        productoSeleccionado = listaProductos_lv.getSelectionModel().getSelectedItem();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }

    }
    

}