package co.edu.uniquindio.proyectofinal.Model;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Servidor implements Runnable {
    private final LinkedList<Socket> clientes;
    private static final int puerto = 1234;

    private static final Utilidades utilidades = Utilidades.getInstancia();

    public Servidor() {
        clientes = new LinkedList<>();
    }

    @Override
    public void run() {
        try {
            utilidades.log("Abriendo ServerSocket en el puerto " + puerto);
            ServerSocket serverSocket = new ServerSocket(puerto);

            while (true) {
                clientes.add(serverSocket.accept());
                DataInputStream dis = new DataInputStream(clientes.getLast().getInputStream());
                utilidades.log("Servidor: " + dis.readUTF() + ". Recibido");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPuerto() {
        return puerto;
    }
}
