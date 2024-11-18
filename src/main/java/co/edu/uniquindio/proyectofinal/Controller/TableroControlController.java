package co.edu.uniquindio.proyectofinal.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import co.edu.uniquindio.proyectofinal.AppServer;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class TableroControlController implements Escribible{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> ListaCantidadContactos_lv;

    @FXML
    private ListView<String> ListaCantidadProductosVendedor_lv;

    @FXML
    private Button generaraReporte_bt;

    @FXML
    private Button irAPrincipal_bt;

    @FXML
    private ListView<String> listaCantidadMensajes_lv;

    @FXML
    private ListView<String> listaCantidadProductosMes_lv;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();

    @FXML
    private GridPane productos_gp;

    private Administrador adminEnLinea;

    @FXML
    void initialize() {
    }

    public void inicializarListaCantidadMensajes(){
    
    }

    public void inicializarListaProductosVendedor(LinkedList<Vendedor> vendedores){
        List<String> infoVendedores = new ArrayList<>(); 
        for(Vendedor vendedor : vendedores){
            int cantidadProductos  = vendedor.getProductos().size();
            String info = vendedor.getNombre() + "- Productos; " + cantidadProductos; 
            infoVendedores.add(info); 
        }
        ListaCantidadProductosVendedor_lv.getItems().setAll(infoVendedores); 
    }

    public void inicializarListaCantidadContactos(LinkedList<Vendedor> vendedores){
            List<String> infoVendedores = new ArrayList<>();
            for(Vendedor vendedor:vendedores){
                
                int cantidadAliados = vendedor.getAliados().size();
                String info = vendedor.getNombre() + " - Aliados: " + cantidadAliados;
                infoVendedores.add(info);
            }

            ListaCantidadContactos_lv.getItems().setAll(infoVendedores);
        
    }

    public void inicializarListaProductosMes(){

        String[] productosArray = new String[12];

        for(int i = 0 ; i < productosArray.length; i++){
            productosArray[i] = Month.of(i + 1) + "    " +  "(" + contarProductosMes(i + 1) + ")";
        }
        listaCantidadProductosMes_lv.getItems().setAll(productosArray);
    }

    private int contarProductosMes(int mes) {
        int resultado = 0;
        for (Producto producto : marketPlace.getProductos()) {
            if(LocalDate.parse(producto.getFechaPublicacion()).getMonth().equals(Month.of(mes))) {
                resultado++;
            }
        }

        return resultado;
    }

    private void inicializarMuroProductos(int posicion, int columna, int fila) {
        try{
            
            if(posicion < 0) {
                return;
            }
            
            List<Producto> productosOrdenados = new ArrayList<>(marketPlace.getProductos());
            productosOrdenados.sort((p1, p2) -> Integer.compare(p2.getCantidadLikes(), p1.getCantidadLikes()));
            
            Producto producto = productosOrdenados.get(posicion);

            FXMLLoader loader = AppServer.getFXMLLoader("producto");
            Parent root = loader.load();
            ProductoController productoController = loader.getController();
            productoController.establecerAdminEnLinea(adminEnLinea);
            productoController.establecerProductoActual(producto);

            productos_gp.add(root, columna, fila);

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

    public void establecerAdminEnLinea(Administrador admin) {
        if(admin != null) {
            adminEnLinea = admin;
            LinkedList<Vendedor> vendedores = marketPlace.getUsuarios().stream().filter(Vendedor.class::isInstance).map(Vendedor.class::cast).collect(Collectors.toCollection(LinkedList::new));
            
            /*inicializarListaCantidadContactos();
            inicializarListaCantidadMensajes();
            inicializarListaProductosVendedor();
            inicializarMuroProductos();
            */
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando loggear", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando loggear al administrador, el administrador es nulo.");
        }
    }

    public Formatter getFormatoReporte()throws IOException {
        Formatter formato = new Formatter(new FileWriter(Utilidades.getInstancia().getRuta("rutaReportesEstadisticas"), true));
        String fecha = LocalDate.now().toString();
        String cantidadMensajes = "";
        String productosPublicadosMes = "";
        String productosMasLikes = "";
        String productosPorVendedor = "";
        String contactosPorVendedor = "";

        for (String productos : listaCantidadProductosMes_lv.getItems()){
            productosPublicadosMes += productos + "\n";
        }

        List<Producto> productosOrdenados = new ArrayList<>(marketPlace.getProductos());
        productosOrdenados.sort((p1, p2) -> Integer.compare(p2.getCantidadLikes(), p1.getCantidadLikes()));

        for (int i = 0; i < 9; i++) {
            if(i < productosOrdenados.size()){
                productosMasLikes += productosOrdenados.get(i) + "\n";
            }
        }

        for (String productos : ListaCantidadProductosVendedor_lv.getItems()){
            productosPorVendedor += productos + "\n";
        }

        for (String contactos : ListaCantidadContactos_lv.getItems()){
            contactosPorVendedor += contactos + "\n";
        }
        
        formato.format("        Reporte general \nFecha: %s \nReporte realizado por: %s\n\n        Informacion del reporte:\n\nCantidad de mensajes en chats: \n%s\nCantidad de productos publicados por mes:\nn%s\nTop 10 productos con más likes:\n%s\nCantidad de productos publicados por vendedor:\n%s\nCantidad contactos por vendedor:\n%s", 
                            fecha, adminEnLinea.getNombre(), cantidadMensajes, productosPublicadosMes, productosMasLikes, productosPorVendedor, contactosPorVendedor);        
        return formato;
    }

    @FXML
    void generarReporte(ActionEvent event) throws IOException{
        utilidades.log("Generando reporte de estadísticas...");
        utilidades.escribirArchivoReporte(this);

    }

    @FXML
    void irAAdminPrincipal(ActionEvent event) throws IOException{
        if(adminEnLinea != null){
            utilidades.log("Cambiando a ventana principal...");

            FXMLLoader loader = AppServer.getFXMLLoader("principal");
            Parent root = loader.load();
            AdminPrincipalController controller = loader.getController();
            controller.establecerAdminEnLinea(adminEnLinea);

            AppServer.setRoot(root, 1115, 599, adminEnLinea.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en muroVendedor.");
        }
    }
}
