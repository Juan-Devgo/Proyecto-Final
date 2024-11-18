package co.edu.uniquindio.proyectofinal.Model;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

import java.io.Serial;
import java.io.Serializable;

public abstract class Usuario implements Serializable, Escribible{
    protected  String nombre, apellido, cedula;

    @Serial
    private static final long serialVersionUID = 1L;

    //Constructores
    public  Usuario(String nombre, String apellido, String cedula){
        if(nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank() || cedula == null || cedula.isBlank()){
            throw  new CadenaInvalidaException();
        }
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = true;
        if(this != obj){
            if(obj == null || getClass() != obj.getClass()){
                equal = false;
            } else {
                Usuario usuario = (Usuario) obj;
                equal = cedula != null && cedula.equals(usuario.getCedula());
            }
        }

        return equal;
    }

    public Usuario() {}

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getCedula() {
        return cedula;
    }

    //Setters
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        if(apellido == null || apellido.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.apellido = apellido;
    }
    public void setCedula(String cedula) {
        if(cedula == null || cedula.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.cedula = cedula;
    }
}