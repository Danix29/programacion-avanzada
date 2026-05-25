package zones;

import model.Nino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaHawkins {

    // Un solo lock para las 3 litas: simplifica las capturas para la GUI
    private final ReentrantLock lock = new ReentrantLock(true);

    private final List<Nino> callePrincipal = new ArrayList<>();
    private final List<Nino> sotanoByers    = new ArrayList<>();
    private final List<Nino> radioWsqk      = new ArrayList<>();

    public void entrarCallePrincipal(Nino n) {
        lock.lock();
        try { callePrincipal.add(n); }
        finally { lock.unlock(); }
    }

    public void salirCallePrincipal(Nino n) {
        lock.lock();
        try { callePrincipal.remove(n); }
        finally { lock.unlock(); }
    }

    public void entrarSotano(Nino n) {
        lock.lock();
        try { sotanoByers.add(n); }
        finally { lock.unlock(); }
    }

    public void salirSotano(Nino n) {
        lock.lock();
        try { sotanoByers.remove(n); }
        finally { lock.unlock(); }
    }

    public void entrarRadio(Nino n) {
        lock.lock();
        try { radioWsqk.add(n); }
        finally { lock.unlock(); }
    }

    public void salirRadio(Nino n) {
        lock.lock();
        try { radioWsqk.remove(n); }
        finally { lock.unlock(); }
    }

    public int getTotalNinos() {
        lock.lock();
        try {
            return callePrincipal.size() + sotanoByers.size() + radioWsqk.size();
        } finally { lock.unlock(); }
    }

    public List<String> getCallePrincipalIds() { return snapshot(callePrincipal); }
    public List<String> getSotanoIds()         { return snapshot(sotanoByers);    }
    public List<String> getRadioIds()          { return snapshot(radioWsqk);      }

    // Copia  la GUI puede iterar sin miedo a modificaciones a la vez
    private List<String> snapshot(List<Nino> lista) {
        lock.lock();
        try {
            List<String> ids = new ArrayList<>(lista.size());
            lista.forEach(n -> ids.add(n.getNombre()));
            return Collections.unmodifiableList(ids);
        } finally { lock.unlock(); }
    }
}
