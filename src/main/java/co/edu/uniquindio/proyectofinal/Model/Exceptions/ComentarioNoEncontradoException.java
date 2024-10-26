package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class ComentarioNoEncontradoException extends RuntimeException {
    public ComentarioNoEncontradoException(){
        super("El comentario no fue encontrado");
    }
}
