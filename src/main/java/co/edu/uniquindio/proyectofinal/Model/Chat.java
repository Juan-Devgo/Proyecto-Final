package co.edu.uniquindio.proyectofinal.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.DatoNuloException;

public class Chat implements Serializable {
    private Vendedor emisor;
    private LinkedList<String> mensajes;

    @Serial
    private static final long serialVersionUID = 1L;

    //Constructor
    public Chat(Vendedor emisor) {
        if(emisor == null){
            throw new DatoNuloException();
        }

        this.emisor = emisor;
        this.mensajes = new LinkedList<>();
    }

    public Chat(){
        this.mensajes = new LinkedList<>();
    }

    //Getters
    public Vendedor getEmisor() {
        return emisor;
    }
    public LinkedList<String> getMensajes() {
        return mensajes;
    }

    //Setters
    public void setEmisor(Vendedor emisor) {
        if(emisor == null){
            throw new DatoNuloException();
        }
        this.emisor = emisor;
    }
    public void setMensajes(LinkedList<String> mensajes) {
        if(mensajes == null){
            throw new DatoNuloException();
        }
        this.mensajes = mensajes;
    }
}
