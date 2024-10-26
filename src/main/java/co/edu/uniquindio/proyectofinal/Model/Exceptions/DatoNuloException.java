package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class DatoNuloException extends RuntimeException {
    public DatoNuloException() {
        super("El dato introducido no puede ser nulo.");
    }
}
