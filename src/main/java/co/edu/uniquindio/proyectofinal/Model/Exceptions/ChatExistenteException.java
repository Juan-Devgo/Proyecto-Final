package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class ChatExistenteException extends RuntimeException{
    public ChatExistenteException(){
        super("El chat que se quiere agregar, ya existe.");
    }
}