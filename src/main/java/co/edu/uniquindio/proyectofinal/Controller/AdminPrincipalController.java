package co.edu.uniquindio.proyectofinal.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.AppServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class AdminPrincipalController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buscarProductos_bt;

    @FXML
    private Button tableroControl_bt;

    @FXML
    private Button buscarVendedores_bt;

    @FXML
    private ChoiceBox<String> categoria_cb;

    @FXML
    private TextField cedula_tf;

    @FXML
    private TextField codigo_tf;

    @FXML
    private ListView<Producto> productos_lv;

    @FXML
    private ListView<Vendedor> vendedores_lv;

    @FXML
    private GridPane muroPublicaciones_gp;

    @FXML
    private TextField nombreProducto_tf;

    @FXML
    private TextField nombreVendedor_tf;

    private String[] categorias = {"Tecnología", "Hogar", "Ropa", "Juguetes", "Vehículos"};

    @FXML
    private VBox vendedores_vb;

    private Administrador adminEnLinea;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    @FXML
    void buscarProductos(ActionEvent event)throws IOException {
        if(adminEnLinea!= null){

            String nombre = nombreProducto_tf.getText();
            String codigo = codigo_tf.getText();
            String categoria = categoria_cb.getValue();
            LinkedList<Producto> productosEncontrados = new LinkedList<>();

            if(categoria != null){
                productosEncontrados.addAll(marketPlace.buscarProductosPorCategoria(categoria));
            }

            if(nombre != null && !nombre.isBlank()){
                productosEncontrados.addAll(marketPlace.buscarProductosPorNombre(nombre));
            }

            if(codigo != null && !codigo.isBlank()){
                productosEncontrados.addAll(marketPlace.buscarProductosPorCodigo(codigo));
            }

            productosEncontrados = productosEncontrados.stream().distinct().collect(Collectors.toCollection(LinkedList::new));
            productos_lv.getItems().setAll(productosEncontrados);

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }

    }

    @FXML
    void initialize() {
        categoria_cb.getItems().setAll(categorias);

    }

    public void establecerAdminEnLinea(Administrador admin) {
        if(admin != null) {
            adminEnLinea = admin;
            inicializarMuroProductos(marketPlace.getProductos().size() - 1, 0, 0);
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al administrador, el administrador es nulo.");
        }
    }

    @FXML
    void buscarVendedores(ActionEvent event)throws IOException {
        if(adminEnLinea != null){

            String nombre = nombreVendedor_tf.getText();
            String cedula = cedula_tf.getText();
            LinkedList<Vendedor> vendedoresEncontrados = new LinkedList<>();

            if(nombre != null && !nombre.isBlank()){
                vendedoresEncontrados.addAll(marketPlace.buscarVendedoresPorNombre(nombre));
            }

            if(cedula != null && !cedula.isBlank()){
                vendedoresEncontrados.addAll(marketPlace.buscarVendedoresPorCedula(cedula));
            }

            vendedoresEncontrados = vendedoresEncontrados.stream().distinct().collect(Collectors.toCollection(LinkedList::new));
            vendedores_lv.getItems().setAll(vendedoresEncontrados);

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }
    }

    private void inicializarMuroProductos(int posicion, int columna, int fila) {
        try{
            
            if(posicion < 0) {
                return;
            }

            Producto producto = marketPlace.getProductos().get(posicion);

            FXMLLoader loader = AppServer.getFXMLLoader("producto");
            Parent root = loader.load();
            ProductoController productoController = loader.getController();
            productoController.establecerAdminEnLinea(adminEnLinea);
            productoController.establecerProductoActual(producto);

            muroPublicaciones_gp.add(root, columna, fila);

            if(columna == 1){
                inicializarMuroProductos(posicion - 1, 0, fila + 1);
            } else {
                inicializarMuroProductos(posicion - 1, columna + 1, fila);
            }
                           
        } catch (IOException e) {
            utilidades.log(Level.SEVERE, "Error inesperado inicializando los productos en el muro");
            e.fillInStackTrace();
        }
    }

    @FXML
    void irATableroControl(ActionEvent event) throws IOException {
        FXMLLoader loader = AppServer.getFXMLLoader("tableroControl");
        Parent root = loader.load();
        TableroControlController controller = loader.getController();
        controller.establecerAdminEnLinea(adminEnLinea);

        AppServer.setRoot(root, 721, 571, adminEnLinea.getNombre());
        
    }

}
