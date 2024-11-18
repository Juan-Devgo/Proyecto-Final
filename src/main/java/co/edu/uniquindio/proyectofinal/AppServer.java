package co.edu.uniquindio.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import co.edu.uniquindio.proyectofinal.Model.*;

public class AppServer extends Application {
    private static Scene scene;
    private static Stage stage;
    private static final Utilidades utilidades = Utilidades.getInstancia();
    private Servidor server = new Servidor();

    @Override
    public void start(Stage stage) throws IOException {
        utilidades.log("Iniciando aplicación");
        scene = new Scene(loadFXML("login"), 513, 407);
        AppServer.stage = stage;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        utilidades.log("Aplicación iniciada.");
    }

    public static void setRoot(String fxml, int width, int height, String title) throws IOException {
        utilidades.log("Iniciando ventana: " + title);
        stage.close();
        scene = new Scene(loadFXML(fxml), width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
        utilidades.log("Ventana iniciada.");
    }

    public static void setRoot(Parent root, int width, int height, String title) throws IOException {
        utilidades.log("Iniciando ventana: " + title);
        stage.close();
        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
        utilidades.log("Ventana iniciada.");
    }

    public static Parent loadFXML(String fxml) throws IOException {
        utilidades.log("cargando: " + fxml + "...");
        FXMLLoader fxmlLoader = new FXMLLoader(AppServer.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
        utilidades.log("Obteniendo FXMLLoader de: " + fxml);
        return new FXMLLoader(AppServer.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();

        /**

        GestionMarketPlace marketPlace = MarketPlace.getInstance("Otro MarketPlace");
        if(marketPlace.buscarProductoPorCodigo("00001").isPresent()) {
            Producto producto1 = marketPlace.buscarProductoPorCodigo("00001").get();
            producto1.setRutaImagen("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final/src/main/resources/imagenes/camara.jpg");
            System.out.println("bien");
        }
        if(marketPlace.buscarProductoPorCodigo("00002").isPresent()) {
            Producto producto2 = marketPlace.buscarProductoPorCodigo("00002").get();
            producto2.setRutaImagen("file:/C:/Users/juand/OneDrive/Escritorio/Uniquindío/Semestre 4/Programación III/Proyecto Final/src/main/resources/imagenes/mt15.png");
            System.out.println("bien, también");
        }        
        
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
        */
    }
}