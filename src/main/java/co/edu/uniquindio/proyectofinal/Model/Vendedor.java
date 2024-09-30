package co.edu.uniquindio.proyectofinal.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

public class Vendedor extends Usuario {
    private String direccion;
    private LinkedList<Producto> productos;
    private LinkedList<Vendedor> aliados;
    private LinkedList<Vendedor> sugerencias;
    private LinkedList<SolicitudVinculo> solicitudes;
    private LinkedList<Comentario> resenias;
    private LinkedList<Usuario> likes;
    private LinkedList<Chat> chats;

    //Constructor
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

        if(!buscarChat(vendedor).isPresent()){
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

        if(!buscarChat(emisor).isPresent()){
            throw new ChatNoEncontradoException();
        }

        buscarChat(emisor).get().getMensajes().add(mensaje);
    }

    //Actualizar las sugerencias
    public void actualizarSugerencias() {
        Predicate<Vendedor> condicion1 = vendedor1 -> !vendedor1.getCedula().equals(cedula);
        Predicate<Vendedor> condicion2 = vendedor2 -> !aliados.contains(vendedor2);

        LinkedList<Vendedor> aliadosDeAliados = obtenerAliadosDeAliados();
        aliadosDeAliados.stream().filter(condicion1).filter(condicion2).collect(Collectors.toCollection(LinkedList::new));
        this.sugerencias = aliadosDeAliados;
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

    //Ordenar resenias por fecha y hora
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
        Boolean disponible = false;

        if(emisor == null){
            throw new DatoNuloException();
        }

        if(aliados.size() < 10){
            disponible = true;
            solicitudes.add(new SolicitudVinculo(emisor, LocalDate.now(), LocalTime.now()));
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
        String infoVendedor = nombre + " " + apellido + " " + cedula + " " + direccion;
        return  infoVendedor;
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
}