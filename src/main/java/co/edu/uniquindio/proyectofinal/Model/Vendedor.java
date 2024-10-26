package co.edu.uniquindio.proyectofinal.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

public class Vendedor extends Usuario implements Serializable {
    private String direccion;
    private LinkedList<Producto> productos;
    private LinkedList<Vendedor> aliados;
    private LinkedList<Vendedor> sugerencias;
    private LinkedList<SolicitudVinculo> solicitudes;
    private LinkedList<Comentario> resenias;
    private LinkedList<Usuario> likes;
    private LinkedList<Chat> chats;

    @Serial
    private static final long serialVersionUID = 1L;

    //Constructores
    public Vendedor(String nombre, String apellido, String cedula, String direccion) {
        super(nombre, apellido, cedula);
        if (direccion == null || direccion.isBlank()) {
            throw new CadenaInvalidaException();
        }
        this.direccion = direccion;

        this.productos = new LinkedList<>();
        this.aliados = new LinkedList<>();
        this.sugerencias = new LinkedList<>();
        this.solicitudes = new LinkedList<>();
        this.resenias = new LinkedList<>();
        this.likes = new LinkedList<>();
        this.chats = new LinkedList<>();
    }

    public Vendedor() {
        super();

        this.productos = new LinkedList<>();
        this.aliados = new LinkedList<>();
        this.sugerencias = new LinkedList<>();
        this.solicitudes = new LinkedList<>();
        this.resenias = new LinkedList<>();
        this.likes = new LinkedList<>();
        this.chats = new LinkedList<>();
    }

    //Agregar nuevo producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    //Agregar nuevo aliado
    public void agregarAliado(Vendedor vendedor) {
        if(vendedor == null){
            throw new DatoNuloException();
        }

        if(aliados.size() >= 10){
            throw new VendedorMaxAliadosException();
        }

        aliados.add(vendedor);
        actualizarSugerencias();
        aliados.forEach(Vendedor::actualizarSugerencias);
        chats.add(new Chat(vendedor));
    }

    //Eliminar aliado
    public void eliminarAliado(Vendedor vendedor) {
        if(vendedor == null){
            throw new DatoNuloException();
        }

        aliados.remove(vendedor);
        actualizarSugerencias();
        aliados.forEach(Vendedor::actualizarSugerencias);

        if(buscarChat(vendedor).isEmpty()){
            throw new ChatNoEncontradoException();
        }
        chats.remove(buscarChat(vendedor).get());
    }

    public Optional<Chat> buscarChat(Vendedor vendedor) {
        return chats.stream().filter(c -> c.getEmisor().equals(vendedor)).findAny();
    }

    //Recibir un mensaje de un aliado
    public void recibirMensaje(Vendedor emisor, String mensaje){
        if(emisor == null){
            throw new DatoNuloException();
        }

        if(buscarChat(emisor).isEmpty()){
            throw new ChatNoEncontradoException();
        }

        buscarChat(emisor).get().getMensajes().add(mensaje);
    }

    //Actualizar las sugerencias
    public void actualizarSugerencias() {
        Predicate<Vendedor> condicion1 = vendedor1 -> !vendedor1.getCedula().equals(cedula);
        Predicate<Vendedor> condicion2 = vendedor2 -> !aliados.contains(vendedor2);

        LinkedList<Vendedor> aliadosDeAliados = obtenerAliadosDeAliados();
        this.sugerencias = aliadosDeAliados.stream().filter(condicion1).filter(condicion2).collect(Collectors.toCollection(LinkedList::new));
    }

    //Obtener aliados de aliados
    private LinkedList<Vendedor> obtenerAliadosDeAliados() {
        LinkedList<Vendedor> aliadosDeAliados = new LinkedList<>();
        for (Vendedor vendedor : aliados) {
            aliadosDeAliados.addAll(vendedor.getAliados());
        }
        return aliadosDeAliados.stream().distinct().collect(Collectors.toCollection(LinkedList::new));
    }

    //Recibir nueva reseña 
    public void recibirResenia(Usuario emisor, String mensaje) {
        if(emisor == null){
            throw new DatoNuloException();
        }

        if(mensaje == null || mensaje.isBlank()){
            throw new CadenaInvalidaException();
        }

        resenias.add(new Comentario(emisor, mensaje)); 
        ordenarResenias();
    }

    //Perder reseña
    public void perderResenia(Usuario emisor, Comentario resenia) {
        if(emisor == null || resenia == null){
            throw new DatoNuloException();
        }

        if(!resenias.contains(resenia)){
            throw new ComentarioNoEncontradoException();
        }

        resenias.remove(resenia);
        resenia = null;
        ordenarResenias();
    }

    //Ordenar reseñas por fecha y hora
    private void ordenarResenias() {
        resenias.sort((r1, r2) -> {
            int i = 0;
            if(r1.getFechaYHora().isBefore(r2.getFechaYHora())){
                i = -1;
            }
            if(r1.getFechaYHora().isAfter(r2.getFechaYHora())){
                i = 1;
            }
            return i;
        });
    }

    //Recibir Like
    public void recibirLike(Usuario emisor) {
        if(likes.contains(emisor)){
            throw new LikeException();
        }
        likes.add(emisor);
    }

    //Perder like
    public void perderLike(Usuario emisor) {
        if(!likes.contains(emisor)){
            throw new LikeException();
        }
        likes.remove(emisor);
    }

    //Agregar nueva solicitud de vínculo
    public boolean agregarSolicitudVinculo(Vendedor emisor) {
        boolean disponible = false;

        if(emisor == null){
            throw new DatoNuloException();
        }

        if(aliados.size() < 10){
            disponible = true;
            solicitudes.add(new SolicitudVinculo(emisor));
        }

        return disponible;
    }

    //Aceptar Solicitud de vínculo
    public void aceptarSolicitudVinculo(SolicitudVinculo solicitud) {
        try {
            this.agregarAliado(solicitud.getEmisor());
            solicitud.getEmisor().agregarAliado(this);    
        } catch (VendedorMaxAliadosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        solicitudes.remove(solicitud);
        solicitud = null;
    }

    //Obtener información general sobre el vendedor
    @Override
    public String toString() {
        return nombre + " " + apellido + " " + cedula + " " + direccion;
    }

    //Getters
    public String getDireccion() {
        return direccion;
    }
    public LinkedList<Vendedor> getAliados() {
        return aliados;
    }
    public LinkedList<Producto> getProductos() {
        return productos;
    }
    public LinkedList<SolicitudVinculo> getSolicitudes() {
        return solicitudes;
    }
    public LinkedList<Vendedor> getSugerencias() {
        return sugerencias;
    }
    public LinkedList<Comentario> getResenias() {
        return resenias;
    }
    public int getLikes() {
        return likes.size();
    }
    public LinkedList<Usuario> getUsuariosLikes() {
        return likes;
    }
    public LinkedList<Chat> getChats() {
        return chats;
    }

    //Setters
    public void setDireccion(String direccion) {
        if(direccion == null || direccion.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.direccion = direccion;
    }

    public void setAliados(LinkedList<Vendedor> aliados) {
        if(aliados == null){
            throw new DatoNuloException();
        }
        this.aliados = aliados;
    }

    public void setProductos(LinkedList<Producto> productos) {
        if(productos == null){
            throw new DatoNuloException();
        }
        this.productos = productos;
    }

    public void setSugerencias(LinkedList<Vendedor> sugerencias) {
        if(sugerencias == null){
            throw new DatoNuloException();
        }
        this.sugerencias = sugerencias;
    }

    public void setChats(LinkedList<Chat> chats) {
        if(chats == null){
            throw new DatoNuloException();
        }
        this.chats = chats;
    }

    public void setLikes(LinkedList<Usuario> likes) {
        if(likes == null){
            throw new DatoNuloException();
        }
        this.likes = likes;
    }

    public void setSolicitudes(LinkedList<SolicitudVinculo> solicitudes) {
        if(solicitudes == null){
            throw new DatoNuloException();
        }
        this.solicitudes = solicitudes;
    }

    public void setResenias(LinkedList<Comentario> resenias) {
        if(resenias == null){
            throw new DatoNuloException();
        }
        this.resenias = resenias;
    }
}