package co.edu.uniquindio.proyectofinal.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.CadenaInvalidaException;
import co.edu.uniquindio.proyectofinal.Model.Exceptions.DatoNuloException;

public class Chat implements Serializable {
    private Vendedor emisor;
    private LinkedList<String> mensajes;
    private int puertoSocket;
    private int puertoServerSocket;
    private String direccionIP;

    private transient Socket socket;
    private transient DataInputStream dis;
    private transient DataOutputStream dos;

    private transient static final Utilidades utilidades = Utilidades.getInstancia();

    @Serial
    private static final long serialVersionUID = 1L;

    //Constructor
    public Chat(Vendedor emisor, int puertoSocket, int puertoServerSocket) {
        if(emisor == null){
            throw new DatoNuloException();
        }

        this.emisor = emisor;
        this.puertoSocket = puertoSocket;
        this.puertoServerSocket = puertoServerSocket;

        this.mensajes = new LinkedList<>();
    }

    public Chat(Vendedor emisor) {
        if(emisor == null){
            throw new DatoNuloException();
        }

        //IP: "192.168.1.14"
        this.direccionIP = "localhost";
        this.puertoServerSocket = 65001;
        this.puertoSocket = 65002;
        
        this.emisor = emisor;
        this.mensajes = new LinkedList<>();
    }

    public Chat(){
        this.mensajes = new LinkedList<>();
    }

    public void empezarHiloChat(Observer observer) {
        try {
            ServerSocket serverSocket = new ServerSocket(puertoServerSocket);
            new HiloChat(this, serverSocket, observer).start();
            
        } catch (Exception e) {
            e.fillInStackTrace();
            utilidades.log(Level.SEVERE, "Ha ocurrido un error inesperado creando el servidor HiloChat.");   
        }
    }

    public void enviarMensaje(String mensaje) {
        if(socket == null) {
            try{
                socket = new Socket(direccionIP, puertoSocket);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                dos.writeUTF("Intentando establecer conexión con el server socket HiloChat.");
                utilidades.log(dis.readUTF());

            } catch (IOException e) {
                e.fillInStackTrace();
                utilidades.log(Level.SEVERE, "Ha ocurrido un error inesperado creando un socket.");
            }
        }

        try{
            dos.writeUTF(mensaje);
            utilidades.log("Socket chat: " + dis.readUTF());
            agregarmensaje(mensaje);
        } catch (IOException e) {
            e.fillInStackTrace();
            utilidades.log(Level.SEVERE, "Ha ocurrido un error inesperado enviado un mensaje por el socket de Chat.");
        }

    }

    public void agregarmensaje(String mensaje) {
        if(mensaje == null || mensaje.isBlank()){
            throw new CadenaInvalidaException();
        }

        mensajes.add(mensaje);
    }

    //toString
    @Override
    public String toString() {
        return  emisor.getNombre() + " " + mensajes.toString();
    }

    //Getters
    public Vendedor getEmisor() {
        return emisor;
    }
    public LinkedList<String> getMensajes() {
        return mensajes;
    }
    public int getPuertoSocket() {
        return puertoSocket;
    }
    public int getPuertoServerSocket() {
        return puertoServerSocket;
    }
    public String getDireccionIP() {
        return direccionIP;
    }

    //Setters
    public void setEmisor(Vendedor emisor) {
        if(emisor == null){
            throw new DatoNuloException();
        }
        this.emisor = emisor;
    }
    public void setMensajes(LinkedList<String> mensajes) {
        if(mensajes == null){
            throw new DatoNuloException();
        }
        this.mensajes = mensajes;
    }
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }
    public void setPuertoServerSocket(int puertoServerSocket) {
        this.puertoServerSocket = puertoServerSocket;
    }
    public void setPuertoSocket(int puertoSocket) {
        this.puertoSocket = puertoSocket;
    }
}
