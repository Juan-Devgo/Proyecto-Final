package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class ChatNoEncontradoException extends RuntimeException {
    public ChatNoEncontradoException(){
        super("El chat no fue encontrado");
    }
}
