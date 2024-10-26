package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(){
        super("No se ha encontrado el usuario");
    }
}
