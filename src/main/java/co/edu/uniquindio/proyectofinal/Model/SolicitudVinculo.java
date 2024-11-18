package co.edu.uniquindio.proyectofinal.Model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

public class SolicitudVinculo implements Serializable {
    private Vendedor emisor;
    private String fecha;
    private String hora;

    @Serial
    private static final long serialVersionUID = 1L;

    //Constructores
    public SolicitudVinculo(Vendedor emisor){
        if(emisor == null){
            throw new DatoNuloException();
        }
        this.emisor = emisor;
        this.fecha = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.hora = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public SolicitudVinculo(){}

    @Override
    public boolean equals(Object obj) {
        boolean equal = true;
        if(this != obj){
            if(obj == null || getClass() != obj.getClass()){
                equal = false;
            } else {
                SolicitudVinculo solicitud = (SolicitudVinculo) obj;
                equal = emisor.getCedula() != null && emisor.getCedula().equals(solicitud.getEmisor().getCedula());
            }
        }

        return equal;
    }

    //Getters
    public Vendedor getEmisor() {
        return emisor;
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
    public void setEmisor(Vendedor emisor) {
        if(emisor == null){
            throw new DatoNuloException();
        }
        this.emisor = emisor;
    }
    public void setFecha(String fecha) {
        if(fecha == null){
            throw new DatoNuloException();
        }
        this.fecha = fecha;
    }
    public void setHora(String hora) {
        if(hora == null){
            throw new DatoNuloException();
        }
        this.hora = hora;
    }

    public String toString(){
        return emisor + " " + fecha + " " + hora;
    }
}