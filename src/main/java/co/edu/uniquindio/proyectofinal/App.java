package co.edu.uniquindio.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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

        Vendedor angie = marketPlace.loginVendedor("1092");
        Vendedor amyi = marketPlace.loginVendedor("2901");
        Administrador juan = marketPlace.loginAdministrador("1137");

        System.out.println(marketPlace.buscarAdministradorPorCedula("1137").isPresent());
        System.out.println(marketPlace.buscarVendedorPorNombre("Angie").isPresent());
        System.out.println(marketPlace.buscarUsuarioPorCedula("2901").isPresent());
        System.out.println(marketPlace.buscarVendedorPorCedula("1092").isPresent());
        System.out.println(marketPlace.buscarProductoPorCodigo("00001").isPresent());
        System.out.println(marketPlace.buscarProductoPorCodigo("00002").isPresent());
    
        // boolean bool1 = marketPlace.solicitarVinculoVendedor(angie, amyi);

        // if(bool1){

        //     SolicitudVinculo sol = amyi.getSolicitudes().getLast();

        //     try {
        //         Utilidades.getInstancia().serializarXML(sol, "Productos", "solicitud1.xml");
        //     } catch (Exception e) {
        //         e.fillInStackTrace();
        //     }

        //     marketPlace.aceptarVinculoVendedor(amyi, sol);
        // }

        // angie.agregarProducto(marketPlace.buscarProductoPorCodigo("00001").get());

        // Chat chat = angie.getChats().getFirst();
        
        // try {
        //     Utilidades.getInstancia().serializarXML(chat, "Productos", "chat1.xml");
        // } catch (Exception e) {
        //     e.fillInStackTrace();
        // }

        // angie.recibirResenia(juan, "Excelente servicio 2000/10 y God");

        // Comentario resenia1 = angie.getResenias().getFirst();

        // try {
        //     Utilidades.getInstancia().serializarXML(resenia1, "Productos", "resenia1.xml");
        // } catch (Exception e) {
        //     e.fillInStackTrace();
        // }
        
        System.out.println(angie.getAliados());
        System.out.println(angie.getProductos());
        System.out.println(angie.getChats());
        System.out.println(angie.getResenias());

        HiloPersistencia runnablePersistencia = HiloPersistencia.getInstancia(marketPlace);
        new Thread(runnablePersistencia).start();

    }
}