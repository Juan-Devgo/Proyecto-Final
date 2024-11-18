package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.AppServer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListCell;

public class PrincipalController {

    @FXML
    private Button buscarProductos_bt;

    @FXML
    private Button buscarVendedores_bt;

    @FXML
    private Button cambiarChat_bt;

    @FXML
    private ChoiceBox<String> categoria_cb;

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
    private ListView<Producto> productos_lv;

    @FXML
    private ListView<Vendedor> vendedores_lv;

    @FXML
    private Button cambiarSolicitudes_bt;

    private Vendedor vendedorEnLinea;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    private String[] categorias = {"Tecnología", "Hogar", "Ropa", "Juguetes", "Vehículos"};

    @FXML
    void initialize() {
        categoria_cb.getItems().setAll(categorias);
    }
        
    @FXML
    void buscarProductos(ActionEvent event)throws IOException {
        if(vendedorEnLinea != null){

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
            productos_lv.setOnMouseClicked(new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent event) { 
                    try{
                        if (event.getClickCount() == 2) { 
                            Producto productoSeleccionado = productos_lv.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = AppServer.getFXMLLoader("muroProducto");
                            Parent root = loader.load();
                            MuroProductoController controller = loader.getController();
                            controller.establecerVendedorEnLinea(vendedorEnLinea);
                            controller.establecerProductoActual(productoSeleccionado);

                            AppServer.setRoot(root, 498, 400, vendedorEnLinea.getNombre());

                        }
                    } catch (IOException e) {
                        utilidades.log(Level.SEVERE,"Error en listView al seleccionar un prodcuto");
                        e.fillInStackTrace();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }

    }

    @FXML
    void buscarVendedores(ActionEvent event) throws IOException {
        if(vendedorEnLinea != null){

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
            vendedores_lv.setOnMouseClicked(new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent event) { 
                    try{
                        if (event.getClickCount() == 2) { 
                            Vendedor vendedorSeleccionado = vendedores_lv.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = AppServer.getFXMLLoader("muroVendedor");
                            Parent root = loader.load();
                            MuroVendedorController controller = loader.getController();
                            controller.establecerVendedorEnLinea(vendedorEnLinea);
                            controller.establecerVendedorAMostrar(vendedorSeleccionado);

                            AppServer.setRoot(root, 735, 519, vendedorEnLinea.getNombre());

                        }
                    } catch (IOException e) {
                        e.fillInStackTrace();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }
    }

    @FXML
    void cambiarChat(ActionEvent event)throws IOException {
        if(vendedorEnLinea != null){

            FXMLLoader loader = AppServer.getFXMLLoader("chat");
            Parent root = loader.load();
            ChatController chatController = loader.getController();
            chatController.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 700, 500, vendedorEnLinea.getNombre());

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }
    }

    @FXML
    void cambiarPerfil(ActionEvent event)throws IOException {
        if(vendedorEnLinea != null){

            FXMLLoader loader = AppServer.getFXMLLoader("perfil");
            Parent root = loader.load();
            PerfilController perfilController = loader.getController();
            perfilController.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 709, 539, vendedorEnLinea.getNombre());

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
            productoController.establecerVendedorEnLinea(vendedorEnLinea);
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
                
    public void establecerVendedorEnLinea(Vendedor vendedor) {

        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            inicializarMuroProductos(marketPlace.getProductos().size() - 1, 0, 0);

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al vendedor, el vendedor es nulo en principal.");
        }
    }

        public void irAmuroProducto(Producto producto)throws IOException{
            utilidades.log("Cambiando ventana a MuroProducto"); 
            FXMLLoader loader = AppServer.getFXMLLoader("muroProducto");
            Parent root = loader.load();
            MuroProductoController muroProductoController = loader.getController();
            muroProductoController.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 498, 400, vendedorEnLinea.getNombre());
            
        }
        
}