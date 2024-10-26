package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class ProductoExistenteException extends RuntimeException {
    public ProductoExistenteException(){
        super("El producto que se quiere agregar ya existe");
    }
}
