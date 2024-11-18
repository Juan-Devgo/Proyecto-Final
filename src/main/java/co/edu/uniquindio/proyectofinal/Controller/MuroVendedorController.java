package co.edu.uniquindio.proyectofinal.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.AppServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class MuroVendedorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button agregarResenia_bt;

    @FXML
    private Label direccion_lb;

    @FXML
    private Button enviarSolicitud_bt;

    @FXML
    private ImageView imagenVendedor_iv;

    @FXML
    private Button like_bt;

    @FXML
    private ImageView like_iv;

    @FXML
    private Button mapa_bt;

    @FXML
    private GridPane muroProductos_gp;

    @FXML
    private Label nombreVendedor_lb;

    @FXML
    private Label numeroLikes_lb;

    @FXML
    private Button principal_bt;

    @FXML
    private Label resenias_lb;


    private Vendedor vendedorEnLinea;

    private Vendedor vendedorMostrado;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    private static final Image imageLike = new Image("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final/src/main/resources/imagenes/amor.png");

    @FXML
    void irAPrincipal(ActionEvent event) throws IOException {
        if(vendedorEnLinea != null){
            utilidades.log("Cambiando a ventana principal...");

            FXMLLoader loader = AppServer.getFXMLLoader("principal");
            Parent root = loader.load();
            PrincipalController controller = loader.getController();
            controller.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 1115, 599, vendedorEnLinea.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en muroVendedor.");
        }
    }

    @FXML
    void irAMapa(ActionEvent event) {
        try {
            FXMLLoader loader = AppServer.getFXMLLoader("geolocalizacion");
            Parent root = loader.load();
            GeolocalizacionController geolocalizacionController = loader.getController();
            geolocalizacionController.establecerVendedorEnLinea(vendedorEnLinea);
            geolocalizacionController.establecerVendedorAMostrar(vendedorMostrado);
            
            AppServer.setRoot(root, 633, 515, "Ubicación de " + vendedorMostrado.getNombre());
            
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    void enviarSolicitud(ActionEvent event) {
        if(vendedorEnLinea != null){
            if(vendedorMostrado != null){

                if(vendedorMostrado.buscarSolicitudPorEmisor(vendedorEnLinea).isEmpty()){
                    marketPlace.solicitarVinculoVendedor(vendedorEnLinea, vendedorMostrado);
                } else {
                    JOptionPane.showMessageDialog(null, "Usted ya ha enviado la solicitud.", "Solicitud enviada", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
                utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en muroVendedor.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en muroVendedor.");
        }
    }

    @FXML
    void agregarResenia(ActionEvent event) {
        if(vendedorEnLinea != null){
            if(vendedorMostrado != null){
                if(!vendedorEnLinea.equals(vendedorMostrado)){

                    String resenia = JOptionPane.showInputDialog(null, "Ingrese su reseña.");

                    if(resenia != null && !resenia.isBlank()){
                        vendedorMostrado.recibirResenia(vendedorEnLinea, resenia);
                        inicializarListaResenias(vendedorMostrado.getResenias());
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No puedes agregarte una reseña a ti mismo.", "Reseñas", JOptionPane.INFORMATION_MESSAGE);
                    utilidades.log(Level.WARNING, "El usuario ha querido autoreseñarse.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
                utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en muroVendedor.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en muroVendedor.");
        }
    }

    @FXML
    void darLike(ActionEvent event) {
        utilidades.log("Botón like presionado a " + vendedorMostrado.getNombre() + "de " + vendedorEnLinea.getNombre() + ".");
        if(vendedorEnLinea != null){
            if(vendedorMostrado != null){

                if(!vendedorMostrado.getLikes().contains(vendedorEnLinea)){
                    
                    vendedorMostrado.recibirLike(vendedorEnLinea);
                    utilidades.log("Like marcado.");
                } else {

                    vendedorMostrado.perderLike(vendedorEnLinea);
                    utilidades.log("Like desmarcado.");
                }

                numeroLikes_lb.setText(String.valueOf(vendedorMostrado.getCantidadLikes()));


            } else {
                JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
                utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en muroVendedor.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en muroVendedor.");
        }
    }


    @FXML
    void initialize() {
        like_iv.setImage(imageLike);
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en muroVendedor.");
        }
    }

    private void inicializarListaResenias(LinkedList<Comentario> resenias) {

        String reseniasStr= "";
        for (Comentario resenia: resenias){
            reseniasStr += resenia.getEmisor().getNombre() + " " + resenia.getEmisor().getApellido() + " " + resenia.getFechaYHoraGUI() + "\n" + resenia.getMensaje() + "\n\n";
        }
        resenias_lb.setText(reseniasStr);
    }

    private void inicializarMuroProductos(int posicion, int columna, int fila) {
        try{
            
            if(posicion < 0) {
                return;
            }

            Producto producto = vendedorMostrado.getProductos().get(posicion);

            FXMLLoader loader = AppServer.getFXMLLoader("producto");
            Parent root = loader.load();
            ProductoController productoController = loader.getController();
            productoController.establecerVendedorEnLinea(vendedorEnLinea);
            productoController.establecerProductoActual(producto);

            muroProductos_gp.add(root, columna, fila);

            if(columna == 1){
                inicializarMuroProductos(posicion - 1, 0, fila + 1);
            }

            inicializarMuroProductos(posicion - 1, columna + 1, fila);

                
        } catch (IOException e) {
            utilidades.log(Level.SEVERE, "Error inesperado inicializando los productos en el muro");
            e.fillInStackTrace();
        }
    }

    public void establecerVendedorAMostrar(Vendedor vendedor) {
        if(vendedor != null) {

            vendedorMostrado = vendedor;
            imagenVendedor_iv.setImage(new Image(vendedorMostrado.getRutaImagen()));
            direccion_lb.setText(String.valueOf(vendedorMostrado.getDireccion()));
            nombreVendedor_lb.setText(String.valueOf(vendedorMostrado.getNombre()));
            numeroLikes_lb.setText(String.valueOf(vendedorMostrado.getCantidadLikes()));

            inicializarMuroProductos(vendedorMostrado.getProductos().size() - 1, 0, 0);
            inicializarListaResenias(vendedorMostrado.getResenias());

            if(vendedorMostrado.equals(vendedorEnLinea)) {
                enviarSolicitud_bt.setDisable(true);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en muroVendedor.");
        }
    }
}