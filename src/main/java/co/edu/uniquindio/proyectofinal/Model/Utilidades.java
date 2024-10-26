package co.edu.uniquindio.proyectofinal.Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ResourceBundle;

public class Utilidades {
    private static Utilidades instancia;

    private Utilidades(){}

    public static Utilidades getInstancia(){
        if(instancia == null){
            instancia = new Utilidades();
        }

        return instancia;
    }

    public void serializarDat(Serializable serializable, String nombreArchivo) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("src/main/resources/Persistencia/" + nombreArchivo + ".dat"));
        output.writeObject(serializable);
        output.flush();
        output.close();
    }

    public void serializarXML(Serializable serializable, String nombreArchivo) throws IOException {
        XMLEncoder output = new XMLEncoder(new FileOutputStream("src/main/resources/Persistencia/" + nombreArchivo + ".xml"));
        output.writeObject(serializable);
        output.flush();
        output.close();
    }

    public Serializable deserializarDat(String nombreArchivo) throws Exception {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("src/main/resources/Persistencia/" + nombreArchivo + ".dat"));
        Serializable serializable = (Serializable) input.readObject();
        input.close();
        return serializable;
    }


    public Serializable deserializarXML(String nombreArchivo) throws Exception {
        XMLDecoder input = new XMLDecoder(new FileInputStream("src/main/resources/Persistencia/" + nombreArchivo + ".xml"));
        Serializable serializable = (Serializable) input.readObject();
        input.close();
        return serializable;
    }

    public String getRuta(String key) {
        return ResourceBundle.getBundle("Properties.rutas").getString(key);
    }
}
