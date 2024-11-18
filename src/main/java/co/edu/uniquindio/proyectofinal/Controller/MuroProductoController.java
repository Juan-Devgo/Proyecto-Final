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
import javafx.scene.layout.AnchorPane;

public class MuroProductoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button agregarComentario_bt;

    @FXML
    private Label categoria_lb;

    @FXML
    private Label estado_lb;

    @FXML
    private Label codigo_lb;

    @FXML
    private Label comentarios_lb;

    @FXML
    private Button comprar_bt;

    @FXML
    private Label descripcion_lb;

    @FXML
    private ImageView imagenProducto_iv;

    @FXML
    private ImageView imagenVendedor_iv;

    @FXML
    private ImageView like_iv;

    @FXML
    private Button like_bt;

    @FXML
    private Button muro_bt;

    @FXML
    private Label nombreProducto_lb;

    @FXML
    private Label nombreVendedor_lb;

    @FXML
    private Label numeroLikes_lb;

    @FXML
    private AnchorPane panelPrincipal_p;

    @FXML
    private Label precio_lb;

    @FXML
    private Button principal_bt;

    private Vendedor vendedorEnLinea;

    private Producto productoActual;

    private Utilidades utilidades = Utilidades.getInstancia();

    private static final Image imageLike = new Image("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final/src/main/resources/imagenes/amor.png");
    
    @FXML
    void irAPrincipal(ActionEvent event)throws IOException {
        if(vendedorEnLinea != null) {
            utilidades.log("Cambiando ventana a principal.");

            FXMLLoader loader = AppServer.getFXMLLoader("principal");
            Parent root = loader.load();
            PrincipalController principalController = loader.getController();
            principalController.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 1115, 599, vendedorEnLinea.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en muroProducto.");
        }
    }

    @FXML
    void irAMuro(ActionEvent event) throws IOException {
        if(vendedorEnLinea != null) {
            if(productoActual != null) {

                utilidades.log("Cambiando ventana a muroVendedor.");

                FXMLLoader loader = AppServer.getFXMLLoader("muroVendedor");
                Parent root = loader.load();
                MuroVendedorController muroVendedorController = loader.getController();
                muroVendedorController.establecerVendedorEnLinea(vendedorEnLinea);
                enviarVendedorAMostrar(loader, productoActual.getVendedor());

                AppServer.setRoot(root, 735, 519, productoActual.getVendedor().getNombre());

            } else{
                JOptionPane.showMessageDialog(null, "Error intentando establecer el producto", "Error", JOptionPane.ERROR_MESSAGE);
                utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en muroProducto.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en muroProducto.");
        }
    }

    @FXML
    void irAgregarComentario(ActionEvent event) {
        if(vendedorEnLinea != null) {
            if(productoActual != null) {

                utilidades.log(vendedorEnLinea.getNombre() + " agregando comentario a " + productoActual.getNombre());
                String comentario = JOptionPane.showInputDialog("Ingrese el comentario");
                productoActual.comentar(vendedorEnLinea, comentario);
                inicializarListaComentarios(productoActual.getComentarios());
                utilidades.log("Comentario realizado.");

            } else{
                JOptionPane.showMessageDialog(null, "Error intentando establecer el producto", "Error", JOptionPane.ERROR_MESSAGE);
                utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en muroProducto.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en muroProducto.");
        }
    }

    @FXML
    void marcarLike(ActionEvent event) {
        utilidades.log("Botón like presionado a " + productoActual.getNombre() + "de " + vendedorEnLinea.getNombre() + ".");
        if(!productoActual.getLikes().contains(vendedorEnLinea)){
            productoActual.recibirLike(vendedorEnLinea);
            utilidades.log("Like marcado.");
        } else {
            productoActual.perderLike(vendedorEnLinea);
            utilidades.log("Like desmarcado.");
        }
        numeroLikes_lb.setText(String.valueOf(productoActual.getCantidadLikes()));
    }

    @FXML
    void realizarCompra(ActionEvent event) {
        if(productoActual.getEstado().equals(EstadoProducto.PUBLICADO)){

            utilidades.log("Comprando " + productoActual.getNombre() + "...");

            try{

                Double dinero = Double.parseDouble(JOptionPane.showInputDialog("Ingrese su dinero."));

                if (dinero >= productoActual.getPrecio()) {
                    productoActual.cambiarAVendido();
                    estado_lb.setText(String.valueOf(productoActual.getEstado()));
                    JOptionPane.showMessageDialog(null, "El artículo ha sido comprado con éxito", "Compra", JOptionPane.INFORMATION_MESSAGE);
                    utilidades.log("Artículo comprado.");
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No te alcanzó para pagar el artículo.", "Compra", JOptionPane.INFORMATION_MESSAGE);
                    utilidades.log("La compra no fue realizada.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El dinero proporcionado no corresponde a un número.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "El producto no se encuentra publicado actualmente.", "Compra", JOptionPane.INFORMATION_MESSAGE);
            utilidades.log("Se ha intentado comprar un artículo no publicado");
        }
    }

    @FXML
    void initialize() {
       like_iv.setImage(imageLike);
       comentarios_lb.setWrapText(true);
       descripcion_lb.setWrapText(true);
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en muroProducto.");
        }
    }

    public void establecerProductoActual(Producto producto){
        if (producto != null){
            productoActual = producto;
            imagenProducto_iv.setImage(new Image(productoActual.getRutaImagen()));
            categoria_lb.setText(productoActual.getCategoria());
            codigo_lb.setText(productoActual.getCodigo());
            precio_lb.setText(String.valueOf(productoActual.getPrecio()));
            estado_lb.setText(String.valueOf(productoActual.getEstado()));
            descripcion_lb.setText(productoActual.getDescripcion()); 
            nombreProducto_lb.setText(productoActual.getNombre()); 
            numeroLikes_lb.setText(String.valueOf(productoActual.getCantidadLikes()));
            imagenVendedor_iv.setImage(new Image(productoActual.getVendedor().getRutaImagen()));
            nombreVendedor_lb.setText(productoActual.getVendedor().getNombre());
            inicializarListaComentarios(productoActual.getComentarios());

            if(!productoActual.getEstado().equals(EstadoProducto.PUBLICADO)){
                comprar_bt.setDisable(true);
            }
            
        } else{
            JOptionPane.showMessageDialog(null, "Error intentando establecer el producto", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer el producto, el producto es nulo en muroProducto.");
        }
    }

    private void inicializarListaComentarios(LinkedList<Comentario> resenias) {

        String reseniasStr = "";
        Vendedor vendedor = productoActual.getVendedor();
        if(vendedor.getAliados().contains(vendedorEnLinea)){
            for (Comentario resenia: resenias){
                reseniasStr += resenia.getEmisor().getNombre() + " " + resenia.getEmisor().getApellido() + "\n" + resenia.getFechaYHoraGUI() + "\n" + resenia.getMensaje() + "\n\n";
            }
        }
        comentarios_lb.setText(reseniasStr);
    }
    
    private void enviarVendedorAMostrar(FXMLLoader loader, Vendedor vendedor) {
        MuroVendedorController muroVendedorController = loader.getController();
        muroVendedorController.establecerVendedorAMostrar(vendedor);
    }
}