package co.edu.uniquindio.proyectofinal.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;

import co.edu.uniquindio.proyectofinal.Model.Exceptions.DatoNuloException;

public class HiloChat extends Thread implements Observable{
    private Chat chat;
    private ServerSocket serverSocket;
    private LinkedList<Observer> observers;
    private Boolean bandera = true;

    private static final Utilidades utilidades = Utilidades.getInstancia();

    public HiloChat(Chat chat, ServerSocket serverSocket, Observer observer){

        if(chat == null || serverSocket == null){
            throw new DatoNuloException();
        }

        this.chat = chat;
        this.serverSocket = serverSocket;
        addObserver(observer);
    }


    @Override
    public void addObserver(Observer observer){
        if(observer == null){
            throw new DatoNuloException();
        }
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer){
        if(observer == null){
            throw new DatoNuloException();
        }
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public synchronized void run(){
        try {
            Socket socket = serverSocket.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            utilidades.log("Servidor HiloChat recibiendo: " + dis.readUTF());
            dos.writeUTF("Servidor HiloChat conectado y en línea.");

            while (bandera) {
                String mensaje = dis.readUTF();
                dos.writeUTF("Mensaje recibido.");
                chat.agregarmensaje(mensaje);
                notifyObservers();
            }
        } catch (IOException e) {
            e.fillInStackTrace();
            utilidades.log(Level.SEVERE, "Ha ocurrido en un error inesperado en HiloChat.");
        }    
    }
}