package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(){
        super("El producto no fue encontrado");
    }
}
