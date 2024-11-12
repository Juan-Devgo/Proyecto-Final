package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.Controller.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uniquindio.proyectofinal.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML
    private Button enviarMensaje_bt;

    @FXML
    private TextField mensaje_tf;

    @FXML
    private VBox mensajes_vb;

    @FXML
    private VBox nombreChats_vb;

    @FXML
    private Label nombre_lb;

    @FXML
    private Button volverPrincipal_bt;
    
    @FXML
    void initialize(){
        GestionMarketPlace marketPlace = MarketPlace.getInstance("");
        List<Vendedor> vendedores = marketPlace.getUsuarios().stream().filter(usuario -> usuario instanceof Vendedor).map(usuario -> (Vendedor)usuario).collect(Collectors.toList());
        for (Vendedor vendedor : vendedores){
            //List<String> nombresChats = vendedor.getChats().getEmisor().getNombre();
        }
    }

    @FXML
    void cambiarPrincipal(ActionEvent event)throws IOException {
        App.setRoot("principal");
    }

    @FXML
    void enviarMensaje(ActionEvent event) {

    }

    
}
