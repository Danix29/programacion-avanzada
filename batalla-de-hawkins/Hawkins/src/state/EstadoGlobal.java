package state;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class EstadoGlobal {

    private static final EstadoGlobal INSTANCE = new EstadoGlobal();
    private EstadoGlobal() {}
    public static EstadoGlobal getInstance() { return INSTANCE; }

    // RW-Lock: muchos hilos leen el estado del evento, solo el gestor escrib
    private final ReentrantReadWriteLock eventoRWLock =
            new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock.WriteLock writeLock =
            eventoRWLock.writeLock();
    private final ReentrantReadWriteLock.ReadLock  readLock  =
            eventoRWLock.readLock();

    public void lockEventoWrite()   { writeLock.lock();   }
    public void unlockEventoWrite() { writeLock.unlock(); }

    public void checkEvento() throws InterruptedException {
        readLock.lockInterruptibly();
        readLock.unlock();
    }

    private volatile TipoEvento eventoActivo     = null;
    private volatile long       eventoInicioMs   = 0;
    private volatile long       eventoDuracionMs = 0;

    public void activarEvento(TipoEvento tipo, long duracionMs) {
        this.eventoActivo     = tipo;
        this.eventoInicioMs   = System.currentTimeMillis();
        this.eventoDuracionMs = duracionMs;
    }

    public void desactivarEvento() { this.eventoActivo = null; }

    public TipoEvento getEventoActivo()  { return eventoActivo; }

    public long getTiempoRestanteMs() {
        if (eventoActivo == null) return 0L;
        return Math.max(0L,
            eventoDuracionMs - (System.currentTimeMillis() - eventoInicioMs));
    }

    // Volatile porque los hilos los miran en cada iteración sin lock
    private volatile boolean             portalBloqueado        = false;
    private volatile int                 multiplicadorSangre    = 1;
    private volatile boolean             demogorgonesParalizados= false;
    private volatile zones.ZonaUpsideDown zonaRedMental         = null;

    public boolean isPortalBloqueado()             { return portalBloqueado; }
    public int     getMultiplicadorSangre()        { return multiplicadorSangre; }
    public boolean isDemogorgonesParalizados()     { return demogorgonesParalizados; }
    public zones.ZonaUpsideDown getZonaRedMental() { return zonaRedMental; }

    public void setPortalBloqueado(boolean v)             { portalBloqueado         = v; }
    public void setMultiplicadorSangre(int v)             { multiplicadorSangre     = v; }
    public void setDemogorgonesParalizados(boolean v)     { demogorgonesParalizados = v; }
    public void setZonaRedMental(zones.ZonaUpsideDown z)  { zonaRedMental           = z; }

    // Pausa pedida desde  RMI
    private final ReentrantLock pauseLock      = new ReentrantLock();
    private final Condition     pauseCondition = pauseLock.newCondition();
    private volatile boolean    pausado        = false;

    public void pausar() {
        pauseLock.lock();
        try { pausado = true; }
        finally { pauseLock.unlock(); }
    }

    public void reanudar() {
        pauseLock.lock();
        try { pausado = false; pauseCondition.signalAll(); }
        finally { pauseLock.unlock(); }
    }

    public void checkPausa() throws InterruptedException {
        if (!pausado) return;
        pauseLock.lock();
        try { while (pausado) pauseCondition.await(); }
        finally { pauseLock.unlock(); }
    }

    // Lo llaman los hilos en sitios seguros para chequear evento y pausa
    public void checkPunto() throws InterruptedException {
        checkEvento();
        checkPausa();
    }

    public boolean isPausado() { return pausado; }

    private final AtomicInteger sangreTotal = new AtomicInteger(0);

    public int incrementarSangre() { return sangreTotal.incrementAndGet(); }
    public int getSangreTotal()    { return sangreTotal.get(); }

    // CAS para que dos hilos no resten a la vez y se pase por debajo de 0
    public int consumirSangre(int n) {
        int actual, nuevo;
        do {
            actual = sangreTotal.get();
            nuevo  = Math.max(0, actual - n);
        } while (!sangreTotal.compareAndSet(actual, nuevo));
        return actual - nuevo;
    }

    private static volatile List<zones.ZonaUpsideDown> zonasUpsideDown;

    public static void setZonasUpsideDown(List<zones.ZonaUpsideDown> z) {
        zonasUpsideDown = z;
    }
    public static List<zones.ZonaUpsideDown> getZonasUpsideDown() {
        return zonasUpsideDown;
    }
}
