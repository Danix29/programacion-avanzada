package zones;

import model.Nino;
import model.Demogorgon;
import state.EstadoGlobal;
import log.SistemaLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Colmena {

    private static final int CAPTURAS_POR_SPAWN = 8;

    private final List<Nino>    capturados    = new ArrayList<>();
    private final AtomicInteger totalCapturas = new AtomicInteger(0);
    private final ZonaHawkins   hawkins;

    // Callback lo usa el ServidorRemoto para meter el nuevo demo en el ranking
    private Consumer<Demogorgon> onSpawnDemogorgon = d -> {};

    public Colmena(ZonaHawkins hawkins) {
        this.hawkins = hawkins;
    }

    public void setOnSpawnDemogorgon(Consumer<Demogorgon> callback) {
        this.onSpawnDemogorgon = callback;
    }

    public synchronized void depositarNino(Nino nino) {
        capturados.add(nino);
        SistemaLog.getInstance().logCaptura(nino.getNombre());

        // Cada 8 capturas Vecna escupe otro demogorgon
        if (totalCapturas.incrementAndGet() % CAPTURAS_POR_SPAWN == 0) {
            String idNuevo = String.format("D%04d", Demogorgon.siguienteId());
            SistemaLog.getInstance().logSpawnDemogorgon(idNuevo);

            Demogorgon nuevo = new Demogorgon(
                    idNuevo, this, EstadoGlobal.getInstance());
            nuevo.setDaemon(false);
            onSpawnDemogorgon.accept(nuevo);
            nuevo.start();
        }
    }

    // Lo llama Eleven en su evento: libera 'cantidad' niños y los manda a Calle Principal
    public synchronized int liberarNinos(int cantidad) {
        int liberados = Math.min(cantidad, capturados.size());
        for (int i = liberados - 1; i >= 0; i--) {
            Nino nino = capturados.remove(i);
            SistemaLog.getInstance().logLibreradoPorEleven(nino.getNombre());
            nino.liberarDeColmena(hawkins);
        }
        return liberados;
    }

    public int getTotalCapturas() { return totalCapturas.get(); }

    public synchronized int getNinosCapturados() { return capturados.size(); }

    public synchronized List<String> getCapturadosIds() {
        List<String> ids = new ArrayList<>(capturados.size());
        capturados.forEach(n -> ids.add(n.getNombre()));
        return Collections.unmodifiableList(ids);
    }
}
