package co.edu.uniquindio.proyectofinal.Model;

public class HiloPersistencia implements Runnable {
    private static HiloPersistencia instancia;
    private static final Utilidades utilidades = Utilidades.getInstancia();
    private final GestionMarketPlace marketPlace;

    private HiloPersistencia(GestionMarketPlace marketplace){
        this.marketPlace = marketplace;
        utilidades.log("Hilo de persistencia inicializado.");
    }

    public static HiloPersistencia getInstancia(GestionMarketPlace marketPlace) {
        if(instancia == null){
            instancia = new HiloPersistencia(marketPlace);
        }

        return instancia;
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                utilidades.log("Hilo de persistencia: inicializado serialización.");
                marketPlace.serializarDatos();
                utilidades.log("Hilo de persistencia: serialización completada.");

                Thread.sleep(5000);

                utilidades.log("Hilo de persistencia: inicializado deserialización.");
                marketPlace.inicializarPersistencia();
                utilidades.log("Hilo de persistencia: deserialización completada.");

                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
    
}
