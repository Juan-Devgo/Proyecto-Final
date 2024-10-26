package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class NumeroInvalidoException extends RuntimeException {
    public NumeroInvalidoException() {
        super("El número introducido no es valido");
    }
}
