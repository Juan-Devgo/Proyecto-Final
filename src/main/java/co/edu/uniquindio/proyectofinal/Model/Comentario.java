package co.edu.uniquindio.proyectofinal.Model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

public class Comentario implements Serializable {
    private Usuario emisor;
    private String mensaje;
    private String fecha;
    private String hora;

    @Serial
    private static final long serialVersionUID = 1L;

    //Constructores
    public Comentario(Usuario emisor, String mensaje) {
        if(emisor == null){
            throw new DatoNuloException();
        }
        this.emisor = emisor;

        if(mensaje == null){
            throw new CadenaInvalidaException();
        }
        this.mensaje = mensaje;

        fecha = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        hora = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public Comentario() {}

    //to String
    @Override 
    public String toString() {
        return "Emisor:" + emisor.getNombre() + " mensaje: " + mensaje + " fecha: " + fecha + " hora: " + hora;
    }

    //Getters
    public Usuario getEmisor() {
        return emisor;
    }
    public String getMensaje() {
        return mensaje;
    }
    public String getFecha() {
        return fecha;
    }
    public String getHora() {
        return hora;
    }
    public LocalDateTime getFechaYHora() {
        return LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(hora));
    }
    public String getFechaYHoraGUI() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(getFechaYHora());
    }

    //Setters
    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
}