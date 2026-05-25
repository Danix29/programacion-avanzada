package zones;

import model.Nino;
import model.Demogorgon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaUpsideDown {

    public enum Nombre {
        BOSQUE, LABORATORIO, CENTRO_COMERCIAL, ALCANTARILLADO
    }

    private final Nombre        nombre;
    private final ReentrantLock lock        = new ReentrantLock(true);
    private final List<Nino>    ninos       = new ArrayList<>();
    private final List<Demogorgon> demogorgons = new ArrayList<>();
    private final Random        rng         = new Random();

    public ZonaUpsideDown(Nombre nombre) {
        this.nombre = nombre;
    }

    public void entrarNino(Nino nino) {
        lock.lock();
        try { ninos.add(nino); }
        finally { lock.unlock(); }
    }

    public void salirNino(Nino nino) {
        lock.lock();
        try { ninos.remove(nino); }
        finally { lock.unlock(); }
    }

    // Comprobar y elegir el mismo lock para evitar carreras niño/demo
    public Optional<Nino> seleccionarNinoAleatorio() {
        lock.lock();
        try {
            if (ninos.isEmpty()) return Optional.empty();
            return Optional.of(ninos.get(rng.nextInt(ninos.size())));
        } finally {
            lock.unlock();
        }
    }

    public void entrarDemogorgon(Demogorgon d) {
        lock.lock();
        try { demogorgons.add(d); }
        finally { lock.unlock(); }
    }

    public void salirDemogorgon(Demogorgon d) {
        lock.lock();
        try { demogorgons.remove(d); }
        finally { lock.unlock(); }
    }

    public int contarNinos() {
        lock.lock();
        try { return ninos.size(); }
        finally { lock.unlock(); }
    }

    public int contarDemogorgons() {
        lock.lock();
        try { return demogorgons.size(); }
        finally { lock.unlock(); }
    }

    public List<String> getNinoIds() {
        lock.lock();
        try {
            List<String> ids = new ArrayList<>(ninos.size());
            ninos.forEach(n -> ids.add(n.getNombre()));
            return Collections.unmodifiableList(ids);
        } finally { lock.unlock(); }
    }

    public List<String> getDemogorgonIds() {
        lock.lock();
        try {
            List<String> ids = new ArrayList<>(demogorgons.size());
            demogorgons.forEach(d -> ids.add(d.getNombre()));
            return Collections.unmodifiableList(ids);
        } finally { lock.unlock(); }
    }

    public Nombre getNombre() { return nombre; }

    @Override
    public String toString() { return nombre.name(); }
}
