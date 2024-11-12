package co.edu.uniquindio.proyectofinal.Model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class Cliente implements Runnable {
    private final String codigo;
    private static final int puerto = 1234;

    private static final Utilidades utilidades = Utilidades.getInstancia();

    public Cliente(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public void run() {
        try{
            Socket socket = new Socket("localhost", 1234);

            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("Cnexión para el cliente " + codigo);


        } catch (IOException e) {
            utilidades.log(Level.SEVERE, "Error fatal intendando establecer conexión con el servidor. Código del Cliente: " + codigo);
        }
    }

    public String getCodigo() {
        return codigo;
    }
}
