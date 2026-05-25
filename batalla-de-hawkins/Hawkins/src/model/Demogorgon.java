package model;

import log.SistemaLog;
import state.EstadoGlobal;
import state.TipoEvento;
import zones.Colmena;
import zones.ZonaUpsideDown;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Demogorgon extends Thread {

    private static final Random RNG = new Random();

    // Contador global de IDs. Solo se muve cuando Colmena  spawnea uno nuevo
    private static final AtomicInteger DEMO_COUNTER = new AtomicInteger(1);

    private final String               id;
    private final Colmena              colmena;
    private final EstadoGlobal         estado;
    private final List<ZonaUpsideDown> todasLasZonas;

    // Capturas de ESTE demo, las lee el ranking RMI sin parar
    private final AtomicInteger capturas = new AtomicInteger(0);

    private ZonaUpsideDown zonaActual;

    public Demogorgon(String id, Colmena colmena, EstadoGlobal estado,
                      List<ZonaUpsideDown> zonas) {
        super("Hilo-" + id);
        this.id            = id;
        this.colmena       = colmena;
        this.estado        = estado;
        this.todasLasZonas = zonas;
        // Empieza en una zona aleatoria
        this.zonaActual    = zonas.get(RNG.nextInt(zonas.size()));
    }

    public Demogorgon(String id, Colmena colmena, EstadoGlobal estado) {
        this(id, colmena, estado, EstadoGlobal.getZonasUpsideDown());
    }

    public static int siguienteId() {
        return DEMO_COUNTER.getAndIncrement();
    }

    @Override
    public void run() {
        zonaActual.entrarDemogorgon(this);
        SistemaLog.getInstance().log(
            id + " inicia patrullaje en " + zonaActual);

        try {
            while (!Thread.currentThread().isInterrupted()) {
                estado.checkPunto();
                esperarSiParalizado();

                Optional<Nino> objetivo = zonaActual.seleccionarNinoAleatorio();

                if (objetivo.isPresent()) {
                    atacar(objetivo.get());
                } else {
                    SistemaLog.getInstance().log(
                        id + " no encuentra niños en " + zonaActual + ", esperando");
                    dormir(4000, 5000);
                }

                estado.checkPunto();
                esperarSiParalizado();

                moverseASiguienteZona();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void atacar(Nino nino) throws InterruptedException {
        boolean iniciado = nino.iniciarAtaque(id);
        if (!iniciado) {
            // Otro demo ya lo está atacando: pequeño descanso para no quemar CPU
            Thread.sleep(200);
            return;
        }

        // Duración base 0.5-1.5s, mitad si TORMENTA
        double factor = (estado.getEventoActivo() == TipoEvento.TORMENTA_UPSIDE_DOWN)
                        ? 0.5 : 1.0;
        long duracion = (long)((500 + RNG.nextInt(1000)) * factor);

        Thread.sleep(duracion);

        // 2/3 de éxito
        boolean exito = RNG.nextInt(3) < 2;
        nino.resolverAtaque(exito);

        if (exito) {
            int totalCapturas = capturas.incrementAndGet();
            SistemaLog.getInstance().logAtaque(id, nino.getNombre(), totalCapturas);

            // Tiempo de depósito en la Colmena
            Thread.sleep(500 + RNG.nextInt(500));

            zonaActual.salirNino(nino);
            colmena.depositarNino(nino);
        } else {
            SistemaLog.getInstance().log(
                id + " ataca a " + nino.getNombre() +
                " pero el niño resiste (probabilidad 1/3)");
        }
    }

    private void moverseASiguienteZona() throws InterruptedException {
        // Durante el apagón los demos no cambian de zona
        if (estado.isPortalBloqueado()) {
            SistemaLog.getInstance().log(
                id + " permanece en " + zonaActual + " (apagón activo)");
            return;
        }
        ZonaUpsideDown destino = elegirSiguienteZona();

        if (destino == zonaActual) return;

        zonaActual.salirDemogorgon(this);
        zonaActual = destino;
        zonaActual.entrarDemogorgon(this);
        SistemaLog.getInstance().log(id + " se desplaza a " + zonaActual);
    }

    private ZonaUpsideDown elegirSiguienteZona() {
        // RED MENTAL: todos van a la zona con más niños
        ZonaUpsideDown redMental = estado.getZonaRedMental();
        if (redMental != null) {
            SistemaLog.getInstance().log(
                id + " recibe orden de Red Mental: ir a " + redMental);
            return redMental;
        }

        // Modo normal: cualquier zona menos la actual
        List<ZonaUpsideDown> otras = todasLasZonas.stream()
                .filter(z -> z != zonaActual)
                .toList();
        return otras.get(RNG.nextInt(otras.size()));
    }

    private void esperarSiParalizado() throws InterruptedException {
        if (!estado.isDemogorgonesParalizados()) return;
        SistemaLog.getInstance().log(id + " paralizado por Eleven");
        while (estado.isDemogorgonesParalizados()) {
            Thread.sleep(100);
        }
        SistemaLog.getInstance().log(id + " reanuda actividad tras Eleven");
    }

    private void dormir(long minMs, long maxMs) throws InterruptedException {
        Thread.sleep(minMs + (long)(RNG.nextDouble() * (maxMs - minMs)));
    }

    public String getNombre()   { return id; }
    public int    getCapturas() { return capturas.get(); }

    @Override
    public String toString() { return id; }
}
