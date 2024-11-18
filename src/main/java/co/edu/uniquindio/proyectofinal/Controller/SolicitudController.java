package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.AppServer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class SolicitudController {

   @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aceptar_bt;

    @FXML
    private Button eliminar_bt;

    @FXML
    private ListView<SolicitudVinculo> listaSolicitudes_lv;
    
    @FXML
    private Button volver_bt;

    private Vendedor vendedorEnLinea;

    private SolicitudVinculo solicitudSeleccionada;

    private Utilidades utilidades = Utilidades.getInstancia();

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    @FXML
    void aceptarSolicitud(ActionEvent event) {
        if(vendedorEnLinea != null){            
            if(solicitudSeleccionada == null){
                
                marketPlace.aceptarVinculoVendedor(vendedorEnLinea, solicitudSeleccionada);
                inicializarListaSolicitudes(vendedorEnLinea.getSolicitudes());
                utilidades.log("Solicitud de vinculo aceptada.");

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una solicitud.", "Solicitides", JOptionPane.INFORMATION_MESSAGE);
                utilidades.log(Level.WARNING, "El usuario no ha seleccionado la solicitud.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en solicitud.");
        }
    }

    @FXML
    void eliminarSolicitud(ActionEvent event) {
        if(vendedorEnLinea != null){
            if(solicitudSeleccionada == null){

                marketPlace.eliminarSolicitud(vendedorEnLinea, solicitudSeleccionada);
                inicializarListaSolicitudes(vendedorEnLinea.getSolicitudes());
                utilidades.log("Solicitud de vinculo rechazada.");

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una solicitud.", "Solicitides", JOptionPane.INFORMATION_MESSAGE);
                utilidades.log(Level.WARNING, "El usuario no ha seleccionado la solicitud.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en solicitud.");
        }
    }

    @FXML
    void cambiarPerfil(ActionEvent event)throws IOException {
        utilidades.log("Cambiando a ventana perfil."); 
        
        FXMLLoader loader = AppServer.getFXMLLoader("perfil");
        Parent root = loader.load();
        PerfilController perfilController = loader.getController();
        perfilController.establecerVendedorEnLinea(vendedorEnLinea);
        
        AppServer.setRoot(root, 709, 539, vendedorEnLinea.getNombre());
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        if(vendedor != null) {
            vendedorEnLinea = vendedor;
            inicializarListaSolicitudes(vendedorEnLinea.getSolicitudes());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer el vendedor en línea", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor en línea es nulo en solicitud.");
        }
    }

    private void inicializarListaSolicitudes(LinkedList<SolicitudVinculo> solicitudes) {
        listaSolicitudes_lv.getItems().setAll(solicitudes);
        listaSolicitudes_lv.setOnMouseClicked(new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent event) { 
                if (event.getClickCount() == 2) { 
                    solicitudSeleccionada = listaSolicitudes_lv.getSelectionModel().getSelectedItem();

                }
            }
        });
    }
}
