package model;

import log.SistemaLog;
import state.EstadoGlobal;
import zones.Colmena;
import zones.Portal;
import zones.ZonaHawkins;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Nino extends Thread {

    private static final Random RNG = new Random();

    private final String       id;
    private final EstadoGlobal estado;
    private final ZonaHawkins  hawkins;
    private final Portal[]     portales;

    // El demo escribe estos flags y avisa, el niño solo espera (al revés sería deadlock)
    private final ReentrantLock attackLock   = new ReentrantLock();
    private final Condition     attackFin    = attackLock.newCondition();
    private volatile boolean    bajoAtaque   = false;

    // Para dormir al niño mientras está en la Colmena
    private final ReentrantLock capturaLock      = new ReentrantLock();
    private final Condition     capturaCondition = capturaLock.newCondition();
    private volatile boolean    enColmena        = false;

    private Portal portalElegido;

    public Nino(String id, ZonaHawkins hawkins, Portal[] portales,
                Colmena colmena, EstadoGlobal estado) {
        super("Hilo-" + id);
        this.id       = id;
        this.hawkins  = hawkins;
        this.portales = portales;
        this.estado   = estado;
    }

    @Override
    public void run() {
        try {
            hawkins.entrarCallePrincipal(this);
            SistemaLog.getInstance().log(
                id + " inicia en Calle Principal de Hawkins");

            // El enunciado pide que el ciclo no acabe nunca
            while (!Thread.currentThread().isInterrupted()) {
                estado.checkPunto();
                ciclo();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void ciclo() throws InterruptedException {

        hawkins.salirCallePrincipal(this);
        hawkins.entrarSotano(this);
        SistemaLog.getInstance().log(id + " llega al Sótano Byers, preparándose");
        dormir(1000, 2000);
        estado.checkPunto();

        portalElegido = portales[RNG.nextInt(portales.length)];
        SistemaLog.getInstance().log(
            id + " elige portal " + portalElegido.getDestino() + " y espera grupo");
        hawkins.salirSotano(this);
        portalElegido.cruzarHaciaUpsideDown(this);
        estado.checkPunto();

        if (!enColmena) {
            explorarUpsideDown();
            // Si el demo me atacó al final de la exploración, espero a que resuelva
            esperarFinAtaque();
        }

        if (enColmena) {
            SistemaLog.getInstance().log(
                id + " está capturado en la Colmena, esperando a Eleven");
            esperarLiberacion();
            return;
        }

        int total = estado.incrementarSangre();
        SistemaLog.getInstance().log(
            id + " recoge sangre de Vecna (total acumulado: " + total + ")");
        portalElegido.cruzarHaciaHawkins(this);
        estado.checkPunto();

        hawkins.entrarRadio(this);
        SistemaLog.getInstance().log(id + " llega a Radio WSQK");
        dormir(2000, 4000);
        hawkins.salirRadio(this);
        estado.checkPunto();

        hawkins.entrarCallePrincipal(this);
        SistemaLog.getInstance().log(id + " deambula por Calle Principal");
        dormir(3000, 5000);
    }

    private void explorarUpsideDown() throws InterruptedException {
        // Multiplicador 1 normal, 2 si hay TORMENTA
        long base = 3000L + (long)(RNG.nextDouble() * 2000L);
        long ms   = base * estado.getMultiplicadorSangre();
        SistemaLog.getInstance().log(
            id + " explora el Upside Down (" + ms / 1000 + "s"
            + (estado.getMultiplicadorSangre() > 1 ? ", TORMENTA activa" : "") + ")");
        Thread.sleep(ms);
    }

    // Lo llama el demo. Devuelve false si ya hay un ataque o ya está capturado
    public boolean iniciarAtaque(String idDemo) {
        attackLock.lock();
        try {
            if (bajoAtaque || enColmena) return false;
            bajoAtaque = true;
            SistemaLog.getInstance().log(id + " bajo ataque de " + idDemo);
            return true;
        } finally {
            attackLock.unlock();
        }
    }

    // Lo llama el demo al acabar el ataque, con el resultada
    public void resolverAtaque(boolean capturar) {
        attackLock.lock();
        try {
            // Pongo enColmena ANTES de signal para que el niño vea el valor correcto al desperta
            if (capturar) enColmena = true;
            bajoAtaque = false;
            attackFin.signalAll();
        } finally {
            attackLock.unlock();
        }
    }

    private void esperarFinAtaque() throws InterruptedException {
        attackLock.lock();
        try {
            while (bajoAtaque) attackFin.await();
        } finally {
            attackLock.unlock();
        }
    }

    private void esperarLiberacion() throws InterruptedException {
        capturaLock.lock();
        try {
            while (enColmena) capturaCondition.await();
        } finally {
            capturaLock.unlock();
        }
    }

    // Lo llama Colmena.liberarNinos() desde GestorEventos
    public void liberarDeColmena(ZonaHawkins destino) {
        capturaLock.lock();
        try {
            enColmena = false;
            destino.entrarCallePrincipal(this);
            capturaCondition.signalAll();
        } finally {
            capturaLock.unlock();
        }
    }

    private void dormir(long minMs, long maxMs) throws InterruptedException {
        Thread.sleep(minMs + (long)(RNG.nextDouble() * (maxMs - minMs)));
    }

    public String  getNombre()    { return id; }
    public boolean isEnColmena()  { return enColmena; }
    public boolean isBajoAtaque() { return bajoAtaque; }
}
