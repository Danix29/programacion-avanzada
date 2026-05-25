package remote;

import model.Demogorgon;
import state.EstadoGlobal;
import zones.Colmena;
import zones.Portal;
import zones.ZonaHawkins;
import zones.ZonaUpsideDown;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServidorRemoto extends UnicastRemoteObject implements HawkinsRemote {

    private static final int    PUERTO = 1099;
    private static final String NOMBRE = "HawkinsService";

    private final ZonaHawkins            hawkins;
    private final Portal[]               portales;
    private final List<ZonaUpsideDown>   zonasUD;
    private final Colmena                colmena;
    private final EstadoGlobal           estado;

    // CopyOnWrite: muchas lecturas ranking cada segundo pocas escrituras spawns
    private static final CopyOnWriteArrayList<Demogorgon> demogorgonsActivos =
            new CopyOnWriteArrayList<>();

    protected ServidorRemoto(ZonaHawkins hawkins, Portal[] portales,
                              List<ZonaUpsideDown> zonasUD, Colmena colmena,
                              EstadoGlobal estado) throws RemoteException {
        this.hawkins  = hawkins;
        this.portales = portales;
        this.zonasUD  = zonasUD;
        this.colmena  = colmena;
        this.estado   = estado;
    }

    public static void registrarDemogorgon(Demogorgon d) {
        demogorgonsActivos.add(d);
    }

    @Override
    public EstadoDTO getEstado() throws RemoteException {

        int totalHawkins = hawkins.getTotalNinos();

        Map<String, Integer> porPortal = new LinkedHashMap<>();
        for (Portal p : portales) {
            porPortal.put(p.getDestino().name(), p.getTotalEsperando());
        }

        Map<String, Integer> ninosUD = new LinkedHashMap<>();
        Map<String, Integer> demosUD = new LinkedHashMap<>();
        for (ZonaUpsideDown z : zonasUD) {
            ninosUD.put(z.getNombre().name(), z.contarNinos());
            demosUD.put(z.getNombre().name(), z.contarDemogorgons());
        }

        int capturados = colmena.getNinosCapturados();

        // Top3 por capturas
        List<String[]> ranking = demogorgonsActivos.stream()
                .sorted(Comparator.comparingInt(Demogorgon::getCapturas).reversed())
                .limit(3)
                .map(d -> new String[]{ d.getNombre(), String.valueOf(d.getCapturas()) })
                .toList();

        state.TipoEvento ev = estado.getEventoActivo();
        String eventoStr    = (ev != null) ? ev.name() : null;
        long   restanteSeg  = estado.getTiempoRestanteMs() / 1000;

        return new EstadoDTO(totalHawkins, porPortal, ninosUD, demosUD,
                capturados, ranking, eventoStr, restanteSeg, estado.isPausado());
    }

    @Override
    public void setPausado(boolean pausar) throws RemoteException {
        if (pausar) {
            estado.pausar();
            log.SistemaLog.getInstance().log("Sistema PAUSADO por cliente remoto");
        } else {
            estado.reanudar();
            log.SistemaLog.getInstance().log("Sistema REANUDADO por cliente remoto");
        }
    }

    public static void iniciar(ZonaHawkins hawkins, Portal[] portales,
                                List<ZonaUpsideDown> zonasUD, Colmena colmena,
                                EstadoGlobal estado) throws Exception {
        ServidorRemoto servidor = new ServidorRemoto(
                hawkins, portales, zonasUD, colmena, estado);

        Registry registry;
        try {
            // Creo el registro yo, así no hace falta lanzar rmiregistry a mano
            registry = LocateRegistry.createRegistry(PUERTO);
        } catch (RemoteException e) {
            // Si ya existía (segunda ejecución), lo cojo en vez de crearlo
            registry = LocateRegistry.getRegistry(PUERTO);
        }

        registry.rebind(NOMBRE, servidor);
        log.SistemaLog.getInstance().log(
                "Servidor RMI escuchando en puerto " + PUERTO + " → " + NOMBRE);
    }
}
