package log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

public final class SistemaLog {

    private static final String FICHERO = "hawkins.txt";
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Holder pattern: instancia perezosa y thread-safe sin synchronized
    private static final class Holder {
        static final SistemaLog INSTANCE = new SistemaLog();
    }

    private final ReentrantLock lock = new ReentrantLock();
    private PrintWriter writer;

    private SistemaLog() {
        try {
            writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(FICHERO, true)), false);
        } catch (IOException e) {
            throw new RuntimeException("No se puede abrir: " + FICHERO, e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(this::cerrar));
    }

    public static SistemaLog getInstance() {
        return Holder.INSTANCE;
    }

    // Lock para que dos hilos no escriban líneas mezcladas
    public void log(String mensaje) {
        String linea = "[" + LocalDateTime.now().format(FORMATO) + "] " + mensaje;
        System.out.println(linea);
        lock.lock();
        try {
            if (writer != null) {
                writer.println(linea);
                writer.flush();
            }
        } finally {
            lock.unlock();
        }
    }

    public void logAtaque(String idDemo, String idNino, int capturas) {
        log("El demogorgon " + idDemo + " ataca al niño " + idNino +
            " (capturas: " + capturas + ")");
    }

    public void logCaptura(String idNino) {
        log("El niño " + idNino + " ha sido capturado");
    }

    public void logEventoGlobal(String descripcion) {
        log("EVENTO GLOBAL: " + descripcion);
    }

    public void logSpawnDemogorgon(String idDemo) {
        log("Vecna genera un nuevo demogorgon: " + idDemo);
    }

    public void logLibreradoPorEleven(String idNino) {
        log("Eleven libera al niño " + idNino + " de la Colmena");
    }

    private void cerrar() {
        lock.lock();
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
                writer = null;
            }
        } finally {
            lock.unlock();
        }
    }
}
