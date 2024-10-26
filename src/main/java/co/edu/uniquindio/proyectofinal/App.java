package co.edu.uniquindio.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.NoSuchElementException;


import co.edu.uniquindio.proyectofinal.Model.*;

public class App extends Application {

    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
        GestionMarketPlace marketPlace = MarketPlace.getInstance("Otro MarketPlace");
        marketPlace.crearAdministrador("Juan", "Admin", "1313");
        marketPlace.crearVendedor("Angie", "Grajales", "1092", "Pueblo Tapado Norte");
        marketPlace.crearVendedor("Angie", "Katerine", "2901", "Por el terminal");
        Vendedor usuarioEnLinea = marketPlace.loginVendedor("1092");
        System.out.println(marketPlace.buscarAdministradorPorCedula("1313").isPresent());
        System.out.println(marketPlace.buscarVendedorPorNombre("Angie").isPresent());
        System.out.println(marketPlace.buscarUsuarioPorCedula("2901").isPresent());
        System.out.println(marketPlace.buscarVendedorPorCedula("1092").isPresent());
        Producto producto1 = new Producto("Cámara", "Cámara muy buena, en muy buen estado", "Electrodomesticos", "00001", 120000.0, "file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre%204/Programación%20III/Proyecto%20Final/src/main/resources/imagenes/camara.jpg");
        usuarioEnLinea.agregarProducto(producto1);
        marketPlace.agregarProducto(producto1);
        Producto producto2 = new Producto("Mt15", "Una carcacha", "Vehículos", "00002", 0.5, "file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre%204/Programación%20III/Proyecto%20Final/src/main/resources/imagenes/mt15.png");
        usuarioEnLinea.agregarProducto(producto2);

        try {
            boolean boolean1 = marketPlace.solicitarVinculoVendedor(marketPlace.buscarVendedorPorCedula("2901").get(), usuarioEnLinea);
            SolicitudVinculo solicitud = usuarioEnLinea.getSolicitudes().getFirst();
            System.out.println(solicitud.getEmisor() + " " + solicitud.getFechaYHoraGUI());
            if(boolean1) {
                marketPlace.aceptarVinculoVendedor(usuarioEnLinea, solicitud);
            }
            System.out.println(usuarioEnLinea.getAliados().getFirst());
            System.out.println(marketPlace.buscarVendedorPorCedula("2901").get().getAliados().getFirst());
            marketPlace.enviarMensaje(marketPlace.buscarVendedorPorCedula("2901").get(), usuarioEnLinea, "Pero si es el ei");
            System.out.println(usuarioEnLinea.buscarChat(marketPlace.buscarVendedorPorCedula("2901").get()).get().getMensajes());

            Utilidades.getInstancia().serializarXML(usuarioEnLinea, "usuarioEnLinea");
            usuarioEnLinea = (Vendedor) Utilidades.getInstancia().deserializarXML("usuarioEnLinea");
            System.out.println(usuarioEnLinea);
        } catch (NoSuchElementException | IOException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}