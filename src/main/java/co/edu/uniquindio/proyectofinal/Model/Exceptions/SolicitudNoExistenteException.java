package co.edu.uniquindio.proyectofinal.Model.Exceptions;

public class SolicitudNoExistenteException extends RuntimeException {
    public SolicitudNoExistenteException(){
        super("Se ha intentado aceptar una solicitud de vínculo no existente o no enviada correctamente");
    }
}
