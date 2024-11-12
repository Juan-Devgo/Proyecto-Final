package co.edu.uniquindio.proyectofinal.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Formatter;
import java.util.LinkedList;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

public class Producto implements Serializable,Escribible {
    private String nombre, descripcion, categoria;
    private String codigo;
    private Double precio;
    private String rutaImagen;
    private String fechaPublicacion;
    private String horaPublicacion;
    private LinkedList<Usuario> likes;
    private LinkedList<Comentario> comentarios;
    private EstadoProducto estado;

    @Serial
    private static  final long serialVersionUID = 1L;

    //Constructores
    public Producto(String nombre, String descripcion, String categoria, String codigo, Double precio, String rutaImagen) {
        if(nombre == null || nombre.isBlank() || descripcion == null || descripcion.isBlank() || categoria == null || categoria.isBlank() || codigo == null || codigo.isBlank() || rutaImagen == null || rutaImagen.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.rutaImagen = rutaImagen;

        if(precio.isNaN() || precio < 0){
            throw new NumeroInvalidoException();
        }
        this.precio = precio;

        this.fechaPublicacion = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.horaPublicacion = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        this.likes = new LinkedList<>();
        this.comentarios = new LinkedList<>();
        this.estado = EstadoProducto.PUBLICADO;
    }

    public Producto(){
        this.likes = new LinkedList<>();
        this.comentarios = new LinkedList<>();
    }

    //Obtener formato para escribir en el reporte
    @Override
    public Formatter getFormatoReporte() throws IOException {
        Formatter formato = new Formatter(new FileWriter(Utilidades.getInstancia().getRuta("rutaReportesProductos"), true));
        formato.format("%s de código %s:\n\nPrecio: %f\nDescripción: %s\nRuta Imagen: %s\nFecha de publicación: %s\nHora de publicación: %s\nLikes: %d (%s)\nComentarios: %d (%s)\nEstado: %s\n\n", 
                            nombre, codigo, precio, descripcion, rutaImagen, fechaPublicacion, horaPublicacion, getLikes(), getNombresUsuarios(likes), comentarios.size(), getNombresComentarios(), estado);
        return formato;
    }

    //Métodos para obtener las cadenas necesarias para el reporte

    private String getNombresComentarios() {
        String nombres = ""; 

        for(Comentario comentario : comentarios) {
            if(comentarios.getLast().equals(comentario)){
                 nombres += comentario.getEmisor().getNombre() + ".";
            } else {
                 nombres += comentario.getEmisor().getNombre() + ", ";
            }
        }

        return nombres;
    }

    //Obtener nombres de una lista de usuarios 
    private String getNombresUsuarios(LinkedList<Usuario> vendedores){
        String nombres = ""; 

        for(Usuario usuario  : vendedores) {
            if(vendedores.getLast().equals(usuario)){
                 nombres += usuario.getNombre() + ".";
            } else {
                 nombres += usuario.getNombre() + ", ";
            }
        }

        return nombres;
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
        return nombre + " " + descripcion + " " + codigo + " " + precio + " " + fechaPublicacion + " " + horaPublicacion;
    }

    //Cambiar de estado
    public void cambiarAVendido() {
        this.estado = EstadoProducto.VENDIDO;
    }

    public void cambiarAPublicado() {
        this.estado = EstadoProducto.PUBLICADO;
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
    public Double getPrecio() {
        return precio;
    }
    public String getRutaImagen() {
        return rutaImagen;
    }
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }
    public String getHoraPublicacion() {
        return horaPublicacion;
    }

    public LocalDateTime getFechaYHoraPublicacion() {
        return LocalDateTime.of(LocalDate.parse(fechaPublicacion), LocalTime.parse(horaPublicacion));
    }

    public String getFechaYHoraPublicacionGUI() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(getFechaYHoraPublicacion());
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
    public void setCodigo(String codigo) {
        if(codigo == null || codigo.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.codigo = codigo;
    }
    public void setPrecio(Double precio) {
        if(precio.isNaN() || precio < 0){
            throw new NumeroInvalidoException();
        }
        this.precio = precio;
    }
    public void setRutaImagen(String rutaImagen) {
        if(rutaImagen == null){
            throw new DatoNuloException();
        }
        this.rutaImagen = rutaImagen;
    }
    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        if(fechaPublicacion == null){
            throw new DatoNuloException();
        }
        this.fechaPublicacion = fechaPublicacion.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public void setFechaPublicacion(String fechaPublicacion) {
        if(fechaPublicacion == null){
            throw new DatoNuloException();
        }
        this.fechaPublicacion = fechaPublicacion;
    }
    public void setHoraPublicacion(LocalTime horaPublicacion) {
        if(horaPublicacion == null){
            throw new DatoNuloException();
        }
        this.horaPublicacion = horaPublicacion.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
    public void setHoraPublicacion(String horaPublicacion) {
        if(horaPublicacion == null){
            throw new DatoNuloException();
        }
        this.horaPublicacion = horaPublicacion;
    }
    public void setLikes(LinkedList<Usuario> likes) {
        if(likes == null){
            throw new DatoNuloException();
        }
        this.likes = likes;
    }
    public void setComentarios(LinkedList<Comentario> comentarios) {
        if(comentarios == null){
            throw new DatoNuloException();
        }
        this.comentarios = comentarios;
    }
    public void setEstado(EstadoProducto estado) {
        if(estado == null){
            throw new DatoNuloException();
        }
        this.estado = estado;
    }
}
