package zones;

import model.Nino;
import state.EstadoGlobal;
import log.SistemaLog;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Portal {

    public enum Destino {
        BOSQUE(2), LABORATORIO(3), CENTRO_COMERCIAL(4), ALCANTARILLADO(2);

        public final int groupSize;
        Destino(int g) { this.groupSize = g; }
    }

    private final Destino         destino;
    private final ZonaUpsideDown  zona;
    private final EstadoGlobal    estado = EstadoGlobal.getInstance();

    // CyclicBarrier para juntar el grupo del tamaño exacto que pide cada portal
    private CyclicBarrier       barrier;
    private final ReentrantLock barrierResetLock = new ReentrantLock();
    // Semáforo para que solo cruce 1 niño cada vez (en cualquier sentido)
    private final Semaphore     cruceSem         = new Semaphore(1, true);
    // Lock + condition para dar prioridad a los retornos sobre las idas
    private final ReentrantLock prioLock         = new ReentrantLock(true);
    private final Condition     puedeEntrar      = prioLock.newCondition();
    private final AtomicInteger retornando       = new AtomicInteger(0);
    private final AtomicInteger esperandoEntrada = new AtomicInteger(0);
    private final AtomicInteger esperandoSalida  = new AtomicInteger(0);

    public Portal(Destino destino, ZonaUpsideDown zona) {
        this.destino = destino;
        this.zona    = zona;
        this.barrier = new CyclicBarrier(destino.groupSize);
    }

    public void cruzarHaciaUpsideDown(Nino nino) throws InterruptedException {
        esperandoEntrada.incrementAndGet();
        try {
            esperarGrupo(nino);

            cruceSem.acquire();
            try {
                esperarSiApagon(nino);

                // Si hay alguien volviendo, cedo el turno
                prioLock.lock();
                try {
                    while (retornando.get() > 0) puedeEntrar.await();
                } finally {
                    prioLock.unlock();
                }

                SistemaLog.getInstance().log(
                    nino.getNombre() + " cruza portal " + destino + " → Upside Down");
                Thread.sleep(1000);
                zona.entrarNino(nino);
            } finally {
                cruceSem.release();
            }
        } finally {
            esperandoEntrada.decrementAndGet();
        }
    }

    private void esperarGrupo(Nino nino) throws InterruptedException {
        while (true) {
            esperarSiApagon(nino);
            try {
                barrier.await();
                return;
            } catch (BrokenBarrierException e) {
                // Si la barrera se rompe, la reseteo y vuelvo a intentar
                if (barrierResetLock.tryLock()) {
                    try {
                        if (barrier.isBroken()) barrier.reset();
                    } finally {
                        barrierResetLock.unlock();
                    }
                }
                Thread.sleep(50);
            }
        }
    }

    private void esperarSiApagon(Nino nino) throws InterruptedException {
        while (estado.isPortalBloqueado()) {
            SistemaLog.getInstance().log(
                nino.getNombre() + " espera en portal " + destino + " (apagón)");
            Thread.sleep(300);
        }
    }

    public void cruzarHaciaHawkins(Nino nino) throws InterruptedException {
        esperandoSalida.incrementAndGet();
        retornando.incrementAndGet();
        try {
            cruceSem.acquire();
            try {
                zona.salirNino(nino);
                SistemaLog.getInstance().log(
                    nino.getNombre() + " cruza portal " + destino + " → Hawkins");
                Thread.sleep(1000);
            } finally {
                cruceSem.release();
            }
        } finally {
            esperandoSalida.decrementAndGet();
            // Cuando ya no quedan retorno, despierto a las idas
            if (retornando.decrementAndGet() == 0) {
                prioLock.lock();
                try { puedeEntrar.signalAll(); }
                finally { prioLock.unlock(); }
            }
        }
    }

    public Destino        getDestino()          { return destino; }
    public ZonaUpsideDown getZona()             { return zona; }
    public int            getEsperandoEntrada() { return esperandoEntrada.get(); }
    public int            getEsperandoSalida()  { return esperandoSalida.get(); }
    public int            getTotalEsperando()   {
        return esperandoEntrada.get() + esperandoSalida.get();
    }
}
