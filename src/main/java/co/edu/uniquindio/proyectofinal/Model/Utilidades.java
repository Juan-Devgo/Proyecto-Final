package co.edu.uniquindio.proyectofinal.Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilidades {
    private static Utilidades instancia;
    private final static Logger LOGGER = Logger.getLogger(Utilidades.class.getName());

    private Utilidades(){
        try {
            LOGGER.addHandler(new FileHandler("src/main/resources/Log/Logs.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log("Utilidades inicializado.");
    }

    public static Utilidades getInstancia(){
        if(instancia == null){
            instancia = new Utilidades();
        }

        return instancia;
    }

    public void escribirArchivoReporte(Escribible escribible) throws IOException{
        log("Intentando generar reporte...");

        if(escribible == null){
            throw new NullPointerException("Escribible es nulo"); 
        }

        log("Obteniendo formato...");
        Formatter formato = escribible.getFormatoReporte(); 
        formato.flush(); 
        formato.close();
        log("Reporte generado con éxito.");
    }

    public void serializarDat(Serializable serializable, String clase, String nombreArchivo) throws IOException {
        log("Intentando serializar: " + nombreArchivo);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(getRuta("rutaPersistencia" + clase) + nombreArchivo));
        output.writeObject(serializable);
        output.close();
        log("Objeto serializado con éxito a Dat...");
    }

    public void serializarXML(Serializable serializable, String clase, String nombreArchivo) throws IOException {
        log("Intentando serializar: " + nombreArchivo);
        XMLEncoder output = new XMLEncoder(new FileOutputStream(getRuta("rutaPersistencia" + clase) + nombreArchivo));
        output.writeObject(serializable);
        output.close();
        log("Objeto serializado con éxito a XML");
    }

    public Serializable deserializarDat(String clase, String nombreArchivo) throws Exception {
        log("Intentando deserializar:  " + nombreArchivo);
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(getRuta("rutaPersistencia" + clase) + nombreArchivo));
        Serializable serializable = (Serializable) input.readObject();
        input.close();
        log("Objeto deserializado con éxito de Dat");
        return serializable;
    }

    public Serializable deserializarXML(String clase, String nombreArchivo) throws Exception {
        log("Intentando deserializar:  " + nombreArchivo);
        XMLDecoder input = new XMLDecoder(new FileInputStream(getRuta("rutaPersistencia" + clase) + nombreArchivo));
        Serializable serializable = (Serializable) input.readObject();
        input.close();
        log("Objeto deserializado con éxito de XML");
        return serializable;
    }

    public String getRuta(String key) {
        log("Accediendo a: " + ResourceBundle.getBundle("Properties.rutas").getString(key));
        return ResourceBundle.getBundle("Properties.rutas").getString(key);
    }

    public void log(String mensaje) {
        LOGGER.log(Level.INFO, mensaje);
    }

    public void log(Level level, String mensaje) {
        LOGGER.log(level, mensaje);
    }
}