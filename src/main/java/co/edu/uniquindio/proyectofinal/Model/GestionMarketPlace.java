package co.edu.uniquindio.proyectofinal.Model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

public interface GestionMarketPlace {
    //Inicialización
    void inicializarPersistencia() throws Exception;
    //Serializar Datos
    void serializarDatos() throws IOException;
    //generar reportes
    void generarReportes() throws IOException;
    //Métodos crear y agregar.
    void crearAdministrador(String nombre, String apellido, String cedula);
    void crearVendedor(String nombre, String apellido, String cedula, String direccion);
    void agregarUsuario(Usuario usuario);
    void agregarProducto(Producto producto);
    //Métodos eliminar.
    void eliminarVendedor(Vendedor vendedor);
    void eliminarAliado(Vendedor emisor, Vendedor receptor);
    void eliminarProducto(Vendedor vendedor, Producto producto);
    void eliminarAdministrador(Administrador administrador);
    //Métodos getters
    LinkedList<Usuario> getUsuarios();
    LinkedList<Producto> getProductos();
    //Requisito RF-003
    boolean solicitarVinculoVendedor(Vendedor emisor, Vendedor receptor);
    void aceptarVinculoVendedor(Vendedor receptor, SolicitudVinculo solicitud);
    //Requisito RF-006
    void obtenerEstadisticas();
    //Requisito RF-007
    void enviarMensaje(Vendedor emisor, Vendedor receptor, String mensaje);
    //Métodos de buscar.
    Optional<Producto> buscarProductoPorNombre(String nombre);
    Optional<Producto> buscarProductoPorCodigo(String codigo);
    LinkedList<Producto> buscarProductosPorCategoria(String categoria);
    Optional<Vendedor> buscarVendedorPorNombre(String nombre);
    Optional<Vendedor> buscarVendedorPorCedula(String cedula);
    Optional<Administrador> buscarAdministradorPorCedula(String cedula);
    Optional<Usuario> buscarUsuarioPorCedula(String cedula);
    //Métodos de Login.
    Vendedor loginVendedor(String cedula);
    Administrador loginAdministrador(String cedula);
}
