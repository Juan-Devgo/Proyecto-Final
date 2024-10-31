package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class SolicitudExistenteException extends RuntimeException {
    public SolicitudExistenteException() {
        super("Se ha intentado crear una solicitud existente");
    }
}
