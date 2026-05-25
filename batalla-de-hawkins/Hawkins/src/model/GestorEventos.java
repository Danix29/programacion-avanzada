package model;

import log.SistemaLog;
import state.EstadoGlobal;
import state.TipoEvento;
import zones.Colmena;
import zones.ZonaUpsideDown;

import java.util.List;
import java.util.Random;

public class GestorEventos extends Thread {

    private static final Random RNG = new Random();

    private final EstadoGlobal         estado;
    private final Colmena              colmena;
    private final List<ZonaUpsideDown> zonas;

    public GestorEventos(EstadoGlobal estado, Colmena colmena,
                         List<ZonaUpsideDown> zonas) {
        super("Hilo-GestorEventos");
        this.estado  = estado;
        this.colmena = colmena;
        this.zonas   = zonas;
        // Daemon: muere solo cuando los hilos no-daemon terminen
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                // Espera 30-60s entre eventos
                Thread.sleep(30_000 + (long)(RNG.nextDouble() * 30_000));

                TipoEvento tipo     = elegirEvento();
                long       duracion = 5_000 + (long)(RNG.nextDouble() * 5_000);

                iniciarEvento(tipo, duracion);
                Thread.sleep(duracion);
                terminarEvento(tipo);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void iniciarEvento(TipoEvento tipo, long duracion) {
        // writeLock: nadie ve los flags a medio actualizar
        estado.lockEventoWrite();
        try {
            estado.activarEvento(tipo, duracion);
            aplicarEfectosInicio(tipo);
            SistemaLog.getInstance().logEventoGlobal(tipo.name() + " INICIADO");
        } finally {
            // Suelto YA el lock: el sleep de duración no debe retenerlo
            estado.unlockEventoWrite();
        }
    }

    private void aplicarEfectosInicio(TipoEvento tipo) {
        switch (tipo) {
            case APAGON_LABORATORIO -> {
                estado.setPortalBloqueado(true);
            }
            case TORMENTA_UPSIDE_DOWN -> {
                estado.setMultiplicadorSangre(2);
            }
            case INTERVENCION_ELEVEN -> {
                estado.setDemogorgonesParalizados(true);
                // Eleven libera tantos niños como sangre haya
                int sangre    = estado.getSangreTotal();
                int liberados = colmena.liberarNinos(sangre);
                estado.consumirSangre(liberados);
                SistemaLog.getInstance().log(
                    "Eleven libera " + liberados + " niños " +
                    "(sangre disponible: " + sangre + ")");
            }
            case RED_MENTAL -> {
                ZonaUpsideDown objetivo = zonaConMasNinos();
                estado.setZonaRedMental(objetivo);
                SistemaLog.getInstance().log(
                    "Red Mental: todos los demogorgons se dirigen a " + objetivo);
            }
        }
    }

    private void terminarEvento(TipoEvento tipo) {
        estado.lockEventoWrite();
        try {
            restaurarEfectos(tipo);
            estado.desactivarEvento();
            SistemaLog.getInstance().logEventoGlobal(tipo.name() + " FINALIZADO");
        } finally {
            estado.unlockEventoWrite();
        }
    }

    private void restaurarEfectos(TipoEvento tipo) {
        switch (tipo) {
            case APAGON_LABORATORIO   -> estado.setPortalBloqueado(false);
            case TORMENTA_UPSIDE_DOWN -> estado.setMultiplicadorSangre(1);
            case INTERVENCION_ELEVEN  -> estado.setDemogorgonesParalizados(false);
            case RED_MENTAL           -> estado.setZonaRedMental(null);
        }
    }

    private TipoEvento elegirEvento() {
        TipoEvento[] valores = TipoEvento.values();
        return valores[RNG.nextInt(valores.length)];
    }

    private ZonaUpsideDown zonaConMasNinos() {
        return zonas.stream()
                .max(java.util.Comparator.comparingInt(ZonaUpsideDown::contarNinos))
                .orElse(zonas.get(0));
    }
}
