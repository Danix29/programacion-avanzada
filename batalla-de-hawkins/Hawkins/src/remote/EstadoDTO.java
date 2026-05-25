package remote;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

// Serializable: necesario para  RMI
public final class EstadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public final int totalNinosHawkins;

    public final Map<String, Integer> ninosPorPortal;

    public final Map<String, Integer> ninosPorZonaUD;
    public final Map<String, Integer> demosPorZonaUD;
    public final int                  ninosCapturadosColmena;

    public final List<String[]> rankingDemogorgons;

    public final String  eventoActivo;
    public final long    tiempoRestanteSeg;
    public final boolean pausado;

    public EstadoDTO(int totalNinosHawkins,
                     Map<String, Integer> ninosPorPortal,
                     Map<String, Integer> ninosPorZonaUD,
                     Map<String, Integer> demosPorZonaUD,
                     int ninosCapturadosColmena,
                     List<String[]> rankingDemogorgons,
                     String eventoActivo,
                     long tiempoRestanteSeg,
                     boolean pausado) {
        // copyOf: copias , así el cliente ve un dato real
        this.totalNinosHawkins      = totalNinosHawkins;
        this.ninosPorPortal         = Map.copyOf(ninosPorPortal);
        this.ninosPorZonaUD         = Map.copyOf(ninosPorZonaUD);
        this.demosPorZonaUD         = Map.copyOf(demosPorZonaUD);
        this.ninosCapturadosColmena = ninosCapturadosColmena;
        this.rankingDemogorgons     = List.copyOf(rankingDemogorgons);
        this.eventoActivo           = eventoActivo;
        this.tiempoRestanteSeg      = tiempoRestanteSeg;
        this.pausado                = pausado;
    }
}
