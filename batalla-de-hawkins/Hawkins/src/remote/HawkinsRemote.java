package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Interfaz Remote
public interface HawkinsRemote extends Remote {

    EstadoDTO getEstado() throws RemoteException;

    void setPausado(boolean pausar) throws RemoteException;
}
