package co.edu.uniquindio.proyectofinal.Model;

import java.io.IOException;
import java.io.FileWriter;
import java.util.Formatter;

public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;
    
    public Administrador(String nombre, String apellido, String cedula) {
        super(nombre, apellido, cedula);
    }

    public Administrador() {}

    @Override
    public Formatter getFormatoReporte() throws IOException {
        Formatter formato = new Formatter(new FileWriter(Utilidades.getInstancia().getRuta("rutaReportesUsuarios"), true));
        formato.format("%s %s, con cédula: %s: Administrador", nombre, apellido, cedula);
        return formato;
    }
}