package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class LikeException extends RuntimeException{
    public LikeException(){
        super("No se puede dar like o quitar un like existente porque la accion ya se ha registrado");
    }
}