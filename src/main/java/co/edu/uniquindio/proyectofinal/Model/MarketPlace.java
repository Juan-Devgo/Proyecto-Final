package co.edu.uniquindio.proyectofinal.Model;

import javax.swing.*;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MarketPlace implements GestionMarketPlace {
    private String nombre;
    private static GestionMarketPlace instancia;
    private final LinkedList<Usuario> usuarios;
    private final LinkedList<Producto> productos;

    private static final Utilidades utilidades = Utilidades.getInstancia();

    private final Servidor servidor;

    public MarketPlace(String nombre) {

        utilidades.log(Level.INFO, "Inicializando MarketPlace...");
        if(nombre == null || nombre.isBlank()) {
            this.nombre = "N/D";
        }

        this.nombre = nombre;
        this.usuarios = new LinkedList<>();
        this.productos = new LinkedList<>();

        this.servidor = new Servidor();
        new Thread(servidor).start();

        try {
            inicializarPersistencia();
        } catch (Exception e) {
            utilidades.log(Level.SEVERE, "Ha ocurrido un error inesperado.");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        utilidades.log("MarketPlace inicializado.");
    }

    @Override
    public void inicializarPersistencia() throws Exception {
        utilidades.log("Inicializando Persistencia de XML");

        File dir = new File(utilidades.getRuta("rutaPersistencia"));

        for (File subdir : Objects.requireNonNull(dir.listFiles(File::isDirectory))) {

            for (File file : Objects.requireNonNull(subdir.listFiles(File::isFile))){

                if(subdir.getName().equals("Usuarios")){

                    if(file.getName().contains(".xml")) {
                        Usuario usuarioNuevo = (Usuario) utilidades.deserializarXML(subdir.getName(), file.getName());

                        if(buscarUsuarioPorCedula(usuarioNuevo.getCedula()).isEmpty()){
                            agregarUsuario(usuarioNuevo);
                        } else {
                            utilidades.log("El usuario no ha sido agregado, porque ya lo estaba.");
                        }
                    }
                }

                if(subdir.getName().equals("Productos")){

                    if(file.getName().contains(".xml")) {
                        Producto productoNuevo = (Producto) utilidades.deserializarXML(subdir.getName(), file.getName());
                        
                        if(buscarProductoPorCodigo(productoNuevo.getCodigo()).isEmpty()){
                            agregarProducto(productoNuevo);
                        } else {
                            utilidades.log("El producto no ha sido agregado, porque ya lo estaba.");
                        }
                    }
                }
            }
        }

        utilidades.log("Datos de persistencia cargados exitosamente");
    }


    public static GestionMarketPlace getInstance(String nombre) {
        if(instancia == null) {
            instancia = new MarketPlace(nombre);
        }
        return instancia;
    }

    //Serializar Datos

    @Override
    public void serializarDatos() throws IOException {
        utilidades.log(Level.INFO, "Iniciando serializacion de datos...");
        for (Usuario usuario : usuarios) {
            utilidades.serializarDat(usuario, "Usuarios", usuario.getNombre() + usuario.getCedula() + ".dat");
            utilidades.log("Usuarios serializados a dat...");
            utilidades.serializarXML(usuario, "Usuarios", usuario.getNombre() + usuario.getCedula() + ".xml");
            utilidades.log("Usuarios serializados a XML..."); 
        }

        for (Producto producto : productos) {
            utilidades.serializarDat(producto, "Productos", producto.getNombre() + producto.getCodigo() + ".dat");
            utilidades.log("Productos serializados a dat...");
            utilidades.serializarXML(producto, "Productos", producto.getNombre() + producto.getCodigo() + ".xml");
            utilidades.log("Productos serializados a XML..."); 
        }
        utilidades.log("Serializacion completada...");
    }

    //Generar reportes

    @Override
    public void generarReportes() throws IOException{
        utilidades.log("Generando reportes...");
        for (Producto producto : productos) {
            utilidades.escribirArchivoReporte(producto);
        }

        for (Usuario usuario : usuarios) {
            utilidades.escribirArchivoReporte(usuario);
        }
        utilidades.log("Reportes generados.");
    }

    private void marcarUsuarioReportado() {
        throw new UnsupportedOperationException();
    }

    private void desmarcarUsuarioReportado() {
        throw new UnsupportedOperationException();
    }

    private void marcarProductoReportado() {
        throw new UnsupportedOperationException();
    }

    private void desmarcarProductoReportado() {
        throw new UnsupportedOperationException();
    }

    //Crear y agregar

    @Override
    public void crearAdministrador(String nombre, String apellido, String cedula) {
        utilidades.log("Creando administrador...");
        if(buscarUsuarioPorCedula(cedula).isPresent()){
            throw new UsuarioExistenteException();
        }
        try {
            Administrador administrador = new Administrador(nombre, apellido, cedula);
            usuarios.add(administrador);
        } catch (RuntimeException e) {
            utilidades.log(Level.SEVERE, "Ha ocurrido un error inesperado.");
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         utilidades.log("Administrador creado...");
    }

    @Override
    public void crearVendedor(String nombre, String apellido, String cedula, String direccion) {
        utilidades.log("Creando vendedor...");
        if(buscarUsuarioPorCedula(cedula).isPresent()){
            throw new UsuarioExistenteException();
        }

        try {
            Vendedor vendedor = new Vendedor(nombre, apellido, cedula, direccion);
            usuarios.add(vendedor);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            utilidades.log(Level.SEVERE, "Ha ocurrido un error ineesperado.");
        }
        utilidades.log("Vendedor creado..."); 
    }

    @Override
    public void agregarUsuario(Usuario usuario){
        utilidades.log("Agregando usuario...");
        if(usuario == null){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        if(buscarUsuarioPorCedula(usuario.getCedula()).isPresent()){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error porque el usuario ya existía.");
            throw new UsuarioExistenteException();
        }
        
        usuarios.add(usuario);
        utilidades.log("Usuario agregado.");
    }

    @Override
    public void agregarProducto(Producto producto) {
        utilidades.log("Registrando producto...");
        if(producto == null){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        if(buscarProductoPorCodigo(producto.getCodigo()).isPresent()) {
            utilidades.log(Level.SEVERE, "Ha ocurrido un error porque el producto ya existía.");
            throw new ProductoExistenteException();
        }

        productos.add(producto);
        ordenarProductos();
        utilidades.log("Producto agregado.");
    }

    //Eliminar

    @Override
    public void eliminarAdministrador(Administrador administrador) {
        if(administrador == null){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        if(!usuarios.contains(administrador)){
            utilidades.log("Ha ocurrido un error porque el usuario no se encontró.");
            throw new UsuarioNoEncontradoException();
        }

        usuarios.remove(administrador);
        administrador = null;
        utilidades.log("Administrador eliminado.");
    }

    @Override
    public void eliminarVendedor(Vendedor vendedor) {
        if(vendedor == null){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        if(!usuarios.contains(vendedor)){
            utilidades.log("Ha ocurrido un error porque el usuario no se encontró.");
            throw new UsuarioNoEncontradoException();
        }
        
        usuarios.remove(vendedor);
        vendedor = null; 
        utilidades.log("Vendedor eliminado.");       
    }

    @Override
    public void eliminarAliado(Vendedor emisor, Vendedor receptor) {
        if(emisor == null || receptor == null){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        emisor.eliminarAliado(receptor);
        receptor.eliminarAliado(emisor);
        utilidades.log("Aliado eliminado.");
    }

    @Override
    public void eliminarProducto(Vendedor vendedor, Producto producto) {
        if(vendedor == null || producto == null){
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        if(!usuarios.contains(vendedor)){
            utilidades.log("Ha ocurrido un error porque el usuario no se encontró.");
            throw new UsuarioNoEncontradoException();
        }

        if(!productos.contains(producto)){
            utilidades.log("Ha ocurrido un error porque el producto no se encontró.");
            throw new ProductoNoEncontradoException();
            
        }
        
        vendedor.getProductos().remove(producto);
        producto = null;
        utilidades.log("Producto eliminado."); 
    }

    //Requisito RF-003

    @Override
    public boolean solicitarVinculoVendedor(Vendedor emisor, Vendedor receptor) {
        utilidades.log("Solicitud de vinculo enviada.");
        return receptor.agregarSolicitudVinculo(emisor);
    }

    @Override
    public void aceptarVinculoVendedor(Vendedor receptor, SolicitudVinculo solicitud) {
        if(!receptor.getSolicitudes().contains(solicitud)){
            utilidades.log("Ha ocurrido un error porque la solicitud de vinculo no fue enconntrada.");
            throw new SolicitudNoExistenteException();
        }
        receptor.aceptarSolicitudVinculo(solicitud);
        utilidades.log("Solicitud de vinculo aceptada");
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
            utilidades.log(Level.SEVERE, "Ha ocurrido un error por un dato nulo.");
            throw new DatoNuloException();
        }

        if(mensaje == null || mensaje.isBlank()){
            utilidades.log(Level.WARNING, "Ha ocurrido un error porque la cadena es inválida.");
            throw new CadenaInvalidaException();
        }

        receptor.recibirMensaje(emisor, mensaje);
    }

    //Login

    @Override
    public Administrador loginAdministrador(String cedula) {
        if(cedula == null || cedula.isBlank()){
            utilidades.log(Level.WARNING, "Ha ocurrido un error porque la cadena es inválida.");
            throw new CadenaInvalidaException();
        }
        
        if(buscarAdministradorPorCedula(cedula).isEmpty()){
            utilidades.log(Level.SEVERE, "Error por no encontrar el usuario indicado.");
            throw new UsuarioNoEncontradoException();
        }
        utilidades.log("Administrador se ha loggeado correctamente...");
        return buscarAdministradorPorCedula(cedula).get();
    }

    
     
    @Override
    public Vendedor loginVendedor(String cedula) {
        if(cedula == null || cedula.isBlank()){
            utilidades.log(Level.WARNING, "Ha ocurrido un error porque la cadena es inválida.");
            throw new CadenaInvalidaException();
        }

        if(buscarVendedorPorCedula(cedula).isEmpty()){
            utilidades.log(Level.SEVERE, "Error por no encontrar el usuario indicado.");
            throw new UsuarioNoEncontradoException();
        }

        utilidades.log("Vendedor loggeado correctamente..."); 
        return buscarVendedorPorCedula(cedula).get();
    }

    //Métodos de buscar.
    @Override
    public Optional<Producto> buscarProductoPorNombre(String nombre) {
        utilidades.log("Buscando un producto por nombre...");
        Predicate<Producto> condicion = producto -> producto.getNombre().equals(nombre);
        return productos.stream().filter(condicion).findAny();
    }

    @Override
    public Optional<Producto> buscarProductoPorCodigo(String codigo) {
        utilidades.log("Buscando producto por código...");
        Predicate<Producto> condicion = producto -> producto.getCodigo().equals(codigo);
        return productos.stream().filter(condicion).findAny();
    }

    @Override
    public LinkedList<Producto> buscarProductosPorCategoria(String categoria) {
        utilidades.log("Buscando producto por categoría..."); 
        Predicate<Producto> condicion = producto -> producto.getCategoria().equals(categoria);
        return productos.stream().filter(condicion).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<Vendedor> buscarVendedorPorNombre(String nombre) {
        utilidades.log("Buscando vendedor por nombre...");
        Predicate<Usuario> condicion = vendedor -> vendedor.getNombre().equals(nombre);
        return usuarios.stream().filter(condicion).filter(Vendedor.class::isInstance).map(Vendedor.class::cast).findAny();
    }
    
    @Override
    public Optional<Vendedor> buscarVendedorPorCedula(String cedula) {
        utilidades.log("Buscando Vendedor por cédula...");
        Predicate<Usuario> condicion = vendedor -> vendedor.getCedula().equals(cedula);
        return usuarios.stream().filter(condicion).filter(Vendedor.class::isInstance).map(Vendedor.class::cast).findAny();
    }

    @Override
    public Optional<Administrador> buscarAdministradorPorCedula(String cedula) {
        utilidades.log("Buscando adiministrador por cédula...");
        Predicate<Usuario> condicion = admin -> admin.getCedula().equals(cedula);
        return usuarios.stream().filter(condicion).filter(Administrador.class::isInstance).map(Administrador.class::cast).findAny();
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorCedula(String cedula) {
        utilidades.log(Level.INFO, "Buscando usuario por cédula...");
        Predicate<Usuario> condicion = usuario -> usuario.getCedula().equals(cedula);
        return usuarios.stream().filter(condicion).findAny();
    }

    //Ordenar productos por fecha y hora
    private void ordenarProductos() {
        utilidades.log("Ordenando productos por fecha...");
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
        utilidades.log("Productos ordenados por fecha.");
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    @Override
    public LinkedList<Producto> getProductos() {
        return productos;
    }

    @Override
    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    //Setters
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new CadenaInvalidaException();
        }
        this.nombre = nombre;
    }
}
