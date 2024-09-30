package co.edu.uniquindio.proyectofinal.Model;

import javax.swing.*;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MarketPlace implements GestionMarketPlace{
    private String nombre;
    private static GestionMarketPlace instancia;
    private LinkedList<Usuario> usuarios;
    private LinkedList<Producto> productos;


    public MarketPlace() {
        setNombre("N/D");
        this.usuarios = new LinkedList<>();
        this.productos = new LinkedList<>();
    }

    public static GestionMarketPlace getInstance() {
        if(instancia == null) {
            instancia = new MarketPlace();
        }
        return instancia;
    }

    //Crear y agregar

    @Override
    public void crearAdministrador(String nombre, String apellido, String cedula) {
        if(buscarUsuarioPorCedula(cedula).isPresent()){
            throw new UsuarioExistenteException();
        }

        try {
            Administrador administrador = new Administrador(nombre, apellido, cedula);
            usuarios.add(administrador);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void crearVendedor(String nombre, String apellido, String cedula, String direccion) {
        if(buscarUsuarioPorCedula(cedula).isPresent()){
            throw new UsuarioExistenteException();
        }

        try {
            Vendedor vendedor = new Vendedor(nombre, apellido, cedula, direccion);
            usuarios.add(vendedor);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void agregarProducto(Producto producto) {
        if(producto == null){
            throw new DatoNuloException();
        }

        if(productos.contains(producto)) {
            throw new ProductoExistenteException();
        }

        productos.add(producto);
        ordenarProductos();
    }

    //Eliminar

    @Override
    public void eliminarAdministrador(Administrador administrador) {
        if(administrador == null){
            throw new DatoNuloException();
        }

        if(!usuarios.contains(administrador)){
            throw new UsuarioNoEncontradoException();
        }

        usuarios.remove(administrador);
        administrador = null;
    }

    @Override
    public void eliminarVendedor(Vendedor vendedor) {
        if(vendedor == null){
            throw new DatoNuloException();
        }

        if(!usuarios.contains(vendedor)){
            throw new UsuarioNoEncontradoException();
        }
        
        usuarios.remove(vendedor);
        vendedor = null;        
    }

    @Override
    public void eliminarAliado(Vendedor emisor, Vendedor receptor) {
        if(emisor == null || receptor == null){
            throw new DatoNuloException();
        }

        emisor.eliminarAliado(receptor);
        receptor.eliminarAliado(emisor);
    }

    @Override
    public void eliminarProducto(Vendedor vendedor, Producto producto) {
        if(vendedor == null || producto == null){
            throw new DatoNuloException();
        }

        if(!usuarios.contains(vendedor)){
            throw new UsuarioNoEncontradoException();
        }

        if(!productos.contains(producto)){
            throw new ProductoNoEncontradoException();
        }
        
        vendedor.getProductos().remove(producto);
        producto = null;         
    }

    //Getters

    @Override
    public LinkedList<Producto> getProductos() {
        return productos;
    }

    @Override
    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    //Requisito RF-003

    @Override
    public boolean solicitarVinculoVendedor(Vendedor emisor, Vendedor receptor) {
        return receptor.agregarSolicitudVinculo(emisor);
    }

    @Override
    public void aceptarVinculoVendedor(Vendedor receptor, SolicitudVinculo solicitud) {
        if(!receptor.getSolicitudes().contains(solicitud)){
            throw new SolicitudNoExistenteException();
        }
        receptor.aceptarSolicitudVinculo(solicitud);
    }

    //Requisito RF-006

    @Override
    public void obtenerEstadisticas() {
        //TODO sin implementar.
        throw new UnsupportedOperationException();
    }

    //Requisito RF-007

    @Override
    public void enviarMensaje(Vendedor emisor, Vendedor receptor, String mensaje) {
        if(emisor == null || receptor == null){
            throw new DatoNuloException();
        }

        if(mensaje == null || mensaje.isBlank()){
            throw new CadenaInvalidaException();
        }

        receptor.recibirMensaje(emisor, mensaje);
    }

    //Login

    @Override
    public Administrador loginAdministrador(String cedula) {
        if(cedula == null || cedula.isBlank()){
            throw new CadenaInvalidaException();
        }
        
        if(buscarAdministradorPorCedula(cedula).isEmpty()){
            throw new UsuarioNoEncontradoException();
        }

        return buscarAdministradorPorCedula(cedula).get();
    }

    @Override
    public Vendedor loginVendedor(String cedula) {
        if(cedula == null || cedula.isBlank()){
            throw new CadenaInvalidaException();
        }

        if(buscarVendedorPorCedula(cedula).isEmpty()){
            throw new UsuarioNoEncontradoException();
        }

        return buscarVendedorPorCedula(cedula).get();
    }

    //Métodos de buscar.
    @Override
    public Optional<Producto> buscarProductoPorNombre(String nombre) {
        Predicate<Producto> condicion = producto -> producto.getNombre().equals(nombre);
        return productos.stream().filter(condicion).findAny();
    }

    @Override
    public Optional<Producto> buscarProductoPorCodigo(String codigo) {
        Predicate<Producto> condicion = producto -> producto.getCodigo().equals(codigo);
        return productos.stream().filter(condicion).findAny();
    }

    @Override
    public LinkedList<Producto> buscarProductosPorCategoria(String categoria) {
        Predicate<Producto> condicion = producto -> producto.getCategoria().equals(categoria);
        return productos.stream().filter(condicion).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<Vendedor> buscarVendedorPorNombre(String nombre) {
        Predicate<Usuario> condicion = vendedor -> vendedor.getNombre().equals(nombre);
        return usuarios.stream().filter(condicion).filter(Vendedor.class::isInstance).map(Vendedor.class::cast).findAny();
    }
    
    @Override
    public Optional<Vendedor> buscarVendedorPorCedula(String cedula) {
        Predicate<Usuario> condicion = vendedor -> vendedor.getCedula().equals(cedula);
        return usuarios.stream().filter(condicion).filter(Vendedor.class::isInstance).map(Vendedor.class::cast).findAny();
    }

    @Override
    public Optional<Administrador> buscarAdministradorPorCedula(String cedula) {
        Predicate<Usuario> condicion = admin -> admin.getCedula().equals(cedula);
        return usuarios.stream().filter(condicion).filter(Administrador.class::isInstance).map(Administrador.class::cast).findAny();
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorCedula(String cedula) {
        Predicate<Usuario> condicion = usuario -> usuario.getCedula().equals(cedula);
        return usuarios.stream().filter(condicion).findAny();
    }

    //Ordenar productos por fecha y hora
    private void ordenarProductos() {
        productos.sort((p1, p2) -> {
            int i = 0;
            if(p1.getFechaYHoraPublicacion().isBefore(p2.getFechaYHoraPublicacion())){
                i = -1;
            }
            if(p1.getFechaYHoraPublicacion().isAfter(p2.getFechaYHoraPublicacion())){
                i = 1;
            }
            return i;
        });
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    //Setters
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.nombre = nombre;
    }
}
