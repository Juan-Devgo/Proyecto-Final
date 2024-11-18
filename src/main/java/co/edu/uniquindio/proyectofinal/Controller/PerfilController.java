package co.edu.uniquindio.proyectofinal.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;

import co.edu.uniquindio.proyectofinal.AppServer;
import co.edu.uniquindio.proyectofinal.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;

public class PerfilController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Actualizar_bt;

    @FXML
    private Button cargarImagen_bt;

    @FXML
    private ChoiceBox<String> categoria_cb;

    @FXML
    private TextField codigo_tf;

    @FXML
    private TextArea descripcion_ta;

    @FXML
    private Label direccion_lb;

    @FXML
    private Button editar_bt;

    @FXML
    private Label identificacion_lb;

    @FXML
    private ImageView imagenVendedor_iv;

    @FXML
    private ImageView like_iv;

    @FXML
    private Label likes_lb;

    @FXML
    private Label listaAliados_lb;

    @FXML
    private ListView<String> listaProductos_lb;

    @FXML
    private Label listaResenias_lb;

    @FXML
    private Button muro_bt;

    @FXML
    private Label nombre_lb;

    @FXML
    private TextField nombre_tf;

    @FXML
    private TextField precio_tf;

    @FXML
    private Button principal_bt;

    @FXML
    private Button publicar_bt;

    @FXML
    private Button solicitudes_bt;

    private Vendedor vendedorEnLinea;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    private Image image = new Image("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final/src/main/resources/imagenes/productoPredeterminado.png");

    private static final Image imageLike = new Image("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final/src/main/resources/imagenes/amor.png");
    
    private String[] categorias = {"Tecnología", "Hogar", "Ropa", "Juguetes", "Vehículos"};

    @FXML
    void irAprincipal(ActionEvent event) throws IOException {
        utilidades.log("cambiando a ventana principal");
        FXMLLoader loader = AppServer.getFXMLLoader("principal");
        Parent root = loader.load();
        PrincipalController principalController = loader.getController();
        principalController.establecerVendedorEnLinea(vendedorEnLinea); 
    
        AppServer.setRoot(root, 1115, 599, vendedorEnLinea.getNombre());
    }

    @FXML
    void IrAEditar(ActionEvent event) throws IOException {
        utilidades.log("cambiando a ventana editar.");

        FXMLLoader loader = AppServer.getFXMLLoader("editar");
        Parent root = loader.load();
        EditarController editarController = loader.getController();
        editarController.establecerVendedorEnLinea(vendedorEnLinea); 

        AppServer.setRoot(root, 536, 365, vendedorEnLinea.getNombre());
    }

    @FXML
    void cargarImageProducto(ActionEvent event) {
        utilidades.log("Abriendo interfaz para seleccionar imágen para " + vendedorEnLinea.getNombre() + ".");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivoSeleccionado = fileChooser.showOpenDialog(cargarImagen_bt.getScene().getWindow());
        if (archivoSeleccionado != null) {
            image = new Image(archivoSeleccionado.toURI().toString());
            utilidades.log("Imagen lista para establecerse.");
        } else {
            JOptionPane.showMessageDialog(null, "Error, no se seleccionó el archivo o no se cargó.", "Error cargando archivo", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.WARNING, "El archivo no se seleccionó o no se cargó en perfil.");
        }
    }

    @FXML
    void irAActualizar(ActionEvent event) throws IOException {
        utilidades.log("cambiando a ventana actualizarPerfil.");
        FXMLLoader loader = AppServer.getFXMLLoader("actualizarPerfil");
        Parent root = loader.load();
        ActualizarPerfilController actualizarController = loader.getController();
        actualizarController.establecerVendedorEnLinea(vendedorEnLinea);

        AppServer.setRoot(root, 400, 400, vendedorEnLinea.getNombre());
    }

    @FXML
    void irASolicitudes(ActionEvent event) throws IOException {

        FXMLLoader loader = AppServer.getFXMLLoader("solicitud");
        Parent root = loader.load();
        SolicitudController solicitudController = loader.getController();
        solicitudController.establecerVendedorEnLinea(vendedorEnLinea);

        AppServer.setRoot(root, 536, 365, vendedorEnLinea.getNombre());
    }

    @FXML
    void irAmuro(ActionEvent event) throws IOException {
        if(vendedorEnLinea != null) {

            FXMLLoader loader = AppServer.getFXMLLoader("muroVendedor");
            Parent root = loader.load();
            MuroVendedorController muroVendedorController = loader.getController();
            muroVendedorController.establecerVendedorEnLinea(vendedorEnLinea);
            muroVendedorController.establecerVendedorAMostrar(vendedorEnLinea);
            AppServer.setRoot(root, 735, 519, vendedorEnLinea.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en perfil.");
        }
    }

    @FXML
    void realizarPublicacion(ActionEvent event) {
        String nombre = nombre_tf.getText();
        String codigo = codigo_tf.getText();
        String categoria = categoria_cb.getValue();
        Double precio = Double.parseDouble(precio_tf.getText());
        String descripcion = descripcion_ta.getText();

        if(vendedorEnLinea != null) {
            if(nombre != null && !nombre.isBlank() && codigo != null && !codigo.isBlank() && categoria != null && !categoria.isBlank() && !precio.isNaN() && descripcion != null && !descripcion.isBlank()){
                Producto producto = new Producto(nombre, descripcion, categoria, codigo, precio, vendedorEnLinea, image.getUrl());
                marketPlace.agregarProducto(producto);

                inicializarListaProductos(vendedorEnLinea.getProductos());

                nombre_tf.setText("");
                codigo_tf.setText("");
                precio_tf.setText("");
                descripcion_ta.setText(""); 

            } else {
                JOptionPane.showMessageDialog(null, "Todas las casillas son obligatorias.", "Ingrese los datos solicitados", JOptionPane.ERROR_MESSAGE);
                utilidades.log(Level.WARNING, "El usuario no envió correctamente los datos en perfil.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en perfil.");
        }


    }

    @FXML
    void initialize() {
        categoria_cb.getItems().setAll(categorias);
        like_iv.setImage(imageLike);
    }

    private void inicializarListaResenias(LinkedList<Comentario> resenias) {
        String reseniasStr= "";
        for (Comentario resenia: resenias){
            reseniasStr += resenia.getFechaYHoraGUI() + " " + resenia.getEmisor().getNombre() + " " + resenia.getEmisor().getApellido() + ":\n" + resenia.getMensaje() + "\n\n";
        }

        listaResenias_lb.setText(reseniasStr);
    }

    private void inicializarListaAliados(LinkedList<Vendedor> vendedores) {
        String vendedoresStr= "";
        for (Vendedor vendedor: vendedores){
            vendedoresStr += vendedor.getNombre() + " " + vendedor.getApellido() + "\n";
        }

        listaAliados_lb.setText(vendedoresStr);
    }

    private void inicializarListaProductos(LinkedList<Producto> productos) {
        String[] productosArray = new String[productos.size()];
        for (int i = 0; i < productosArray.length; i++) {
            Producto producto = productos.get(i);
            productosArray[i] = producto.getNombre() + "    " + producto.getCodigo() + "    " + producto.getEstado() + "    " + producto.getCategoria() + "    " + producto.getFechaYHoraPublicacionGUI() + "    $" + producto.getPrecio() + "    Likes: " + producto.getCantidadLikes() + "    Comentarios: " + producto.getCantidadComentarios();
        }
        listaProductos_lb.getItems().setAll(productosArray);
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            imagenVendedor_iv.setImage(new Image(vendedorEnLinea.getRutaImagen()));
            likes_lb.setText(String.valueOf(vendedorEnLinea.getCantidadLikes()));
            nombre_lb.setText(vendedorEnLinea.getNombre());
            identificacion_lb.setText(vendedorEnLinea.getCedula());
            direccion_lb.setText(vendedorEnLinea.getDireccion());
            inicializarListaResenias(vendedorEnLinea.getResenias());
            inicializarListaAliados(vendedor.getAliados());
            inicializarListaProductos(vendedorEnLinea.getProductos());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en perfil.");
        }
    }
}