package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class AliadoExistenteException extends RuntimeException {
    public AliadoExistenteException() {
        super("El vendedor al que se le ha hecho solicitud ya es aliado.");
    }
}
