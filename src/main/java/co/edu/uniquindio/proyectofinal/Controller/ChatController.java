package co.edu.uniquindio.proyectofinal.Controller;

import co.edu.uniquindio.proyectofinal.Model.*;
import co.edu.uniquindio.proyectofinal.AppServer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ChatController implements Observer {

    @FXML
    private Button enviarMensaje_bt;

    @FXML
    private TextField mensaje_tf;

    @FXML
    private ListView<Chat> nombreChats_lv;

    @FXML
    private VBox nombreChats_vb;

    @FXML
    private Label mensajes_lb;

    @FXML
    private Label nombre_lb;

    @FXML
    private Button volverPrincipal_bt;

    private Vendedor vendedorEnLinea;

    private Chat chat;

    private GestionMarketPlace marketPlace = MarketPlace.getInstance("Market Place 1.0");

    private Utilidades utilidades = Utilidades.getInstancia();
    
    @FXML
    void initialize(){
        mensajes_lb.setWrapText(true);
    }

    @FXML
    void cambiarPrincipal(ActionEvent event)throws IOException {
        if(vendedorEnLinea != null) {
            utilidades.log("Cambiando a ventana principal...");

            FXMLLoader loader = AppServer.getFXMLLoader("principal");
            Parent root = loader.load();
            PrincipalController principalController = loader.getController();
            principalController.establecerVendedorEnLinea(vendedorEnLinea);

            AppServer.setRoot(root, 1115, 599, vendedorEnLinea.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en chat.");
        }
    }

    @FXML
    void enviarMensaje(ActionEvent event) {
        if(vendedorEnLinea != null){
            utilidades.log("Enviando mensaje...");
            String mensaje = mensaje_tf.getText();
            String nombre = vendedorEnLinea.getNombre();
            String apellido = vendedorEnLinea.getApellido();
            String fecha = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDate.now());
            chat.enviarMensaje(nombre + " " + apellido + " " + fecha +  ": " + "\n" + mensaje + "\n\n");

        } else {
            JOptionPane.showMessageDialog(null, "Error intentando establecer al vendedor.", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en chat.");
        }
    }

    @FXML
    void seleccionarChat(MouseEvent event) {
        chat = nombreChats_lv.getSelectionModel().getSelectedItem();
        inicializarMensajes();
    }

    public void establecerVendedorEnLinea(Vendedor vendedor) {
        utilidades.log("Estableciendo vendedor en línea en el Chat...");
        if(vendedor != null) {

            vendedorEnLinea = vendedor;
            inicializarChats(vendedor.getChats());
              
        } else {
            JOptionPane.showMessageDialog(null, "Error ", "Error", JOptionPane.ERROR_MESSAGE);
            utilidades.log(Level.SEVERE, "Error intentando establecer al vendedor, el vendedor es nulo en aPerfil.");
        }
        utilidades.log("Vendedor en línea establecido.");
    }

    private void inicializarChats(LinkedList<Chat> chats) {
        nombreChats_lv.getItems().setAll(chats);

        nombreChats_lv.setCellFactory(new Callback<ListView<Chat>, ListCell<Chat>>() {
            @Override
            public ListCell<Chat> call(ListView<Chat> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Chat chat, boolean empty) {
                        super.updateItem(chat, empty);

                        if (empty || chat == null) {
                            setText(null);
                        } else {
                            String nombre = chat.getEmisor().getNombre();
                            String ultimoMensaje = chat.getMensajes().getLast();

                            setText(nombre + "  " + ultimoMensaje);
                        }
                    }
                };
            }
        });
    }

    @Override
    public void update() {
        inicializarMensajes();
    }

    private void inicializarMensajes() {
        if(chat != null) {
            String mensajes = "";

            for (String mensaje : chat.getMensajes()) {
                mensajes += mensaje;
            }

            mensajes_lb.setText(mensajes);
        }
    }
        
}
