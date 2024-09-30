package co.edu.uniquindio.proyectofinal.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

public class Producto {
    private String nombre, descripcion, categoria;
    private final String codigo;
    private double precio;
    private String rutaImagen;
    private final LocalDate fechaPublicacion;
    private final LocalTime horaPublicacion;
    private LinkedList<Usuario> likes;
    private LinkedList<Comentario> comentarios;
    private EstadoProducto estado;

    //Constructor
    public Producto(String nombre, String descripcion, String categoria, String codigo, double precio, String rutaImagen) {
        if(nombre == null || nombre.isBlank() || descripcion == null || descripcion.isBlank() || categoria == null || categoria.isBlank() || codigo == null || codigo.isBlank() || rutaImagen == null || rutaImagen.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.rutaImagen = rutaImagen;

        if(precio < 0){
            throw new NumeroInvalidoException();
        }
        this.precio = precio;

        this.fechaPublicacion = LocalDate.now();
        this.horaPublicacion = LocalTime.now();
        this.estado = EstadoProducto.BORRADOR;
    }

    //Recibir like
    public void recibirLike(Usuario emisor) {
        if(likes.contains(emisor)){
            throw new LikeException();
        }
        likes.add(emisor);
    }

    //Perder Like
    public void perderLike(Usuario emisor) {
        if(!likes.contains(emisor)){
            throw new LikeException();
        }
        likes.remove(emisor);
    }

    //Recibir comentario
    public void comentar(Usuario emisor, String mensaje) {
        if(emisor == null){
            throw new DatoNuloException();
        }

        if(mensaje == null || mensaje.isBlank()) {
            throw new CadenaInvalidaException();
        }
        comentarios.add(new Comentario(emisor, mensaje));
        ordenarComentarios();
    }

    //Perder comentario
    public void perderComentario(Usuario emisor, Comentario comentario) {
        if(emisor == null || comentario == null){
            throw new DatoNuloException();
        }

        if(!comentarios.contains(comentario)){
            throw new ComentarioNoEncontradoException();
        }

        comentarios.remove(comentario);
        comentario = null;
        ordenarComentarios();
    }

    //Ordenar comentarios según fecha y hora
    private void ordenarComentarios() {
        comentarios.sort((c1, c2) -> {
            int i = 0;
            if(c1.getFechaYHora().isBefore(c2.getFechaYHora())){
                i = -1;
            }
            if(c1.getFechaYHora().isAfter(c2.getFechaYHora())){
                i = 1;
            }
            return i;
        });
    }

    //Obtener información general sobre el producto
    @Override
    public String toString() {
        String infoProducto = nombre + " " + descripcion + " " + codigo + " " + precio + " " + fechaPublicacion + " " + horaPublicacion;
        return infoProducto;
    }

    //Cambiar de estado
    public void cambiarAVendido() {
        this.estado = EstadoProducto.VENDIDO;
    }

    public void cambiarAPublicado() {
        this.estado = EstadoProducto.PUBLICADO;
    }

    public void cambiarABorrador() {
        this.estado = EstadoProducto.BORRADOR;
    }

    public void cambiarACancelado() {
        this.estado = EstadoProducto.CANCELADO;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getCodigo() {
        return codigo;
    }
    public double getPrecio() {
        return precio;
    }
    public String getRutaImagen() {
        return rutaImagen;
    }
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }
    public LocalTime getHoraPublicacion() {
        return horaPublicacion;
    }
    public LocalDateTime getFechaYHoraPublicacion() {
        return LocalDateTime.of(fechaPublicacion, horaPublicacion);
    }
    public int getLikes() {
        return likes.size();
    }
    public LinkedList<Usuario> getUsuariosLikes() {
        return likes;
    }
    public int getCantidadComentarios() {
        return comentarios.size();
    }
    public LinkedList<Comentario> getComentarios() {
        return comentarios;
    }
    public EstadoProducto getEstado() {
        return estado;
    }

    //Setters
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        if(descripcion == null || descripcion.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.descripcion = descripcion;
    }
    public void setCategoria(String categoria) {
        if(descripcion == null || descripcion.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.categoria = categoria;
    }
    public void setImagen(String rutaImagen) {
        if(rutaImagen == null){
            throw new DatoNuloException();
        }
        this.rutaImagen = rutaImagen;
    }
    public void setPrecio(double precio) {
        if(precio >= 0){
            throw new NumeroInvalidoException();
        }
        this.precio = precio;
    }
}
