package co.edu.uniquindio.proyectofinal.Controller;

import java.io.IOException;

import java.net.URISyntaxException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.AppServer;
import co.edu.uniquindio.proyectofinal.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class GeolocalizacionController {

    @FXML
    private WebView ubicacion_wv;

    @FXML
    private Button volver_bt;

    private Vendedor vendedorEnLinea;

    private Vendedor vendedorMostrado;

    private ServicioGeolocalizacion servicioGeolocalizacion = new ServicioGeolocalizacion();

    private Utilidades utilidades = Utilidades.getInstancia();

    public void mostrarUbicacion(String direccion){
        try {
            double[] coordenadas = servicioGeolocalizacion.obtenerCoordenadas(direccion); 
            String latitud = String.valueOf(coordenadas[0]); 
            String longitud = String.valueOf(coordenadas[1]); 

            WebEngine webEngine = ubicacion_wv.getEngine();
            webEngine.setJavaScriptEnabled(true);

            String html = "<html><head>" 
                    + "<style type='text/css'> html, body { height: 100%; margin: 0; padding: 0; } #map { height: 100%; } </style>" 
                    + "<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDnchbnb9_uuiSCd2wEhOfpzbjyeri6JZc\"></script>" 
                    + "</head><body>" 
                    + "<div id=\"map\"></div>" 
                    + "<script>" 
                    + "function initMap() {" 
                    + "  var map = new google.maps.Map(document.getElementById('map'), {" 
                    + "    center: {lat: " + latitud + ", lng: " + longitud + "}," 
                    + "    zoom: 15" 
                    + "  });" 
                    + "  var marker = new google.maps.Marker({" 
                    + "    position: {lat: " + latitud + ", lng: " + longitud + "}," 
                    + "    map: map," 
                    + "    title: 'Ubicación del Vendedor'" 
                    + "  });" 
                    + "}" 
                    + "google.maps.event.addDomListener(window, 'load', initMap);" 
                    + "</script>" 
                    + "</body></html>";
            webEngine.loadContent(html); 
        } catch (IOException | URISyntaxException e) {
                e.printStackTrace(); 
        }
    }

    @FXML
    void irAMuroVendedor(ActionEvent event) throws IOException {
        utilidades.log("Cambiando ventana a MuroVendedor.");
        if(vendedorEnLinea != null) {

            FXMLLoader loader = AppServer.getFXMLLoader("muroVendedor"); 
            Parent root = loader.load(); 
            MuroVendedorController muroVendedorController = loader.getController();
            muroVendedorController.establecerVendedorEnLinea(vendedorEnLinea);
            muroVendedorController.establecerVendedorAMostrar(vendedorMostrado);
            
            AppServer.setRoot(root, 735, 519, vendedorEnLinea.getNombre());
            
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en editar.");
        }
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en editar.");
        }
    }

    public void establecerVendedorAMostrar(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorMostrado = vendedor;
            mostrarUbicacion(vendedorMostrado.getDireccion());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor mostrado", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor mostrado es nulo en muroVendedor.");
        }
    }
}