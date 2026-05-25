import gui.InicioGUI;
import log.SistemaLog;
import model.Demogorgon;
import model.GestorEventos;
import model.Nino;
import state.EstadoGlobal;
import zones.Colmena;
import zones.Portal;
import zones.ZonaHawkins;
import zones.ZonaUpsideDown;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int    TOTAL_NINOS = 1500;
    private static final Random RNG         = new Random();

    public static void main(String[] args) {
        // Forzar UTF-8 para que tildes y ñ salgan bien en consola
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        javax.swing.SwingUtilities.invokeLater(() ->
            new InicioGUI(Main::arrancarSistema).setVisible(true));
    }

    private static void arrancarSistema() {
        try {
            SistemaLog.getInstance().log("=== Sistema Hawkins iniciado ===");

            ZonaUpsideDown bosque      = new ZonaUpsideDown(ZonaUpsideDown.Nombre.BOSQUE);
            ZonaUpsideDown laboratorio = new ZonaUpsideDown(ZonaUpsideDown.Nombre.LABORATORIO);
            ZonaUpsideDown centro      = new ZonaUpsideDown(ZonaUpsideDown.Nombre.CENTRO_COMERCIAL);
            ZonaUpsideDown alcant      = new ZonaUpsideDown(ZonaUpsideDown.Nombre.ALCANTARILLADO);
            List<ZonaUpsideDown> todasLasZonas = List.of(bosque, laboratorio, centro, alcant);

            ZonaHawkins hawkins = new ZonaHawkins();
            Colmena     colmena = new Colmena(hawkins);

            Portal[] portales = {
                new Portal(Portal.Destino.BOSQUE,           bosque),
                new Portal(Portal.Destino.LABORATORIO,      laboratorio),
                new Portal(Portal.Destino.CENTRO_COMERCIAL, centro),
                new Portal(Portal.Destino.ALCANTARILLADO,   alcant)
            };

            EstadoGlobal.setZonasUpsideDown(todasLasZonas);
            EstadoGlobal estado = EstadoGlobal.getInstance();

            javax.swing.SwingUtilities.invokeLater(() ->
                new gui.HawkinsGUI(hawkins, portales, todasLasZonas, colmena, estado)
                    .setVisible(true));

            // Si el RMI falla sigo sin él, no se cae todo
            try {
                remote.ServidorRemoto.iniciar(hawkins, portales, todasLasZonas, colmena, estado);
                colmena.setOnSpawnDemogorgon(remote.ServidorRemoto::registrarDemogorgon);
            } catch (Exception e) {
                SistemaLog.getInstance().log(
                    "WARN: Servidor RMI no iniciado: " + e.getMessage());
            }

            javax.swing.SwingUtilities.invokeLater(() ->
                new remote.ClienteRemoto("localhost").setVisible(true));

            new GestorEventos(estado, colmena, todasLasZonas).start();

            // Demogorgon 0
            Demogorgon alpha = new Demogorgon("D0000", colmena, estado, todasLasZonas);
            alpha.setDaemon(false);
            remote.ServidorRemoto.registrarDemogorgon(alpha);
            alpha.start();
            SistemaLog.getInstance().log("Demogorgon Alpha D0000 inicia patrullaje");

            // Spawneo escalonado
            Thread spawner = new Thread(() -> {
                SistemaLog.getInstance().log(
                    "Iniciando creación escalonada de " + TOTAL_NINOS + " niños...");
                for (int i = 1; i <= TOTAL_NINOS; i++) {
                    String id   = String.format("N%04d", i);
                    Nino   nino = new Nino(id, hawkins, portales, colmena, estado);
                     nino.setDaemon(false);
                    nino.start();
                try {
                    estado.checkPunto();           // ← AÑADE esta línea
                    Thread.sleep(500 + (long)(RNG.nextDouble() * 1500));
                    }
                     catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
                }
                SistemaLog.getInstance().log(
                    "Todos los " + TOTAL_NINOS + " niños creados. Sistema en marcha.");
            }, "Hilo-SpawnerNinos");
            spawner.setDaemon(false);
            spawner.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
