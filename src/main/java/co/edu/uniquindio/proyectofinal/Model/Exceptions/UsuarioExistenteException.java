package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(){
        super("El usuario que se quiere crear ya existe");
    }
}
