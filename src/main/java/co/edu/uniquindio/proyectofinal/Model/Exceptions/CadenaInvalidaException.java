package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class CadenaInvalidaException extends RuntimeException {
    public CadenaInvalidaException() {
        super("La cadena proporcionada es nula o está vacía");
    }
}
