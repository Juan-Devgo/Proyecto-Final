package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class VendedorMaxAliadosException extends RuntimeException {
    public VendedorMaxAliadosException() {
        super("El vendedor ya tiene 10 aliados en su lista.");
    }
}
