<img src="https://capsule-render.vercel.app/api?type=waving&color=ED8B00&height=180&section=header&text=batalla-de-hawkins&fontSize=32&fontColor=FFFFFF&fontAlignY=38&desc=La%20invasion%20del%20mundo%20del%20reves%20%7C%20PECL%202025-26&descAlignY=60&descColor=FFECB3" width="100%"/>

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![RMI](https://img.shields.io/badge/Java%20RMI-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-F05032?style=for-the-badge)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![Threads](https://img.shields.io/badge/1500%20Threads-F05032?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completado-1D9E75?style=for-the-badge)

</div>

---

## Sobre la practica

**PECL Programacion Avanzada · UAH GII · Convocatoria Ordinaria Mayo 2026**

Simulacion concurrente y distribuida de la invasion de Hawkins por el Upside Down (Stranger Things). El sistema gestiona **1500 ninos** y **demogorgons** como hilos independientes que cooperan y compiten por recursos compartidos: portales, zonas, sangre de Vecna y vidas humanas. Se compone de dos partes:

- **Parte 1 (concurrente)** &mdash; sincronizacion fina entre hilos: portales con grupos, prioridad de retorno, 4 eventos globales que afectan todo el sistema.
- **Parte 2 (distribuida)** &mdash; modulo remoto via Java RMI: cliente externo que consulta el estado en tiempo real y pausa/reanuda el servidor.

---

## Reglas del sistema

| Elemento | Detalle |
|---|---|
| Ninos | 1500, creados escalonadamente cada 0,5&ndash;2 s, identificados como `NXXXX` |
| Demogorgons | 1 inicial (`D0000` Alpha), +1 nuevo cada 8 capturas, identificados como `DXXXX` |
| Hawkins (zona segura) | Calle Principal, Sotano Byers, Radio WSQK |
| Upside Down | 4 zonas inseguras (Bosque, Laboratorio, Centro Comercial, Alcantarillado) + Colmena |
| Portales | 4 portales, 1 nino a la vez, prioridad al que vuelve a Hawkins |
| Tamano de grupo por portal | Bosque=2, Laboratorio=3, Centro Comercial=4, Alcantarillado=2 |
| Resistencia al ataque | Probabilidad 2/3 de sobrevivir |

---

## Eventos globales

Cada 30&ndash;60 s ocurre uno aleatorio (5&ndash;10 s de duracion):

| Evento | Efecto |
|---|---|
| **Apagon del Laboratorio** | Portales inutilizables (los en transito terminan); demogorgons no cambian de zona pero siguen atacando |
| **Tormenta del Upside Down** | Tiempo de recoleccion x2; demogorgons atacan al doble de velocidad |
| **Intervencion de Eleven** | Libera tantos ninos de la Colmena como sangre recolectada; demogorgons paralizados |
| **La Red Mental** | Todos los demogorgons se dirigen a la zona con mas ninos |

---

## Arquitectura (paquetes con dependencias aciclicas)

```
src/
├── Main.java
├── model/          # Hilos
│   ├── Nino.java               # ciclo: Calle -> Sotano -> Portal -> UpsideDown -> regreso -> Radio
│   ├── Demogorgon.java         # patrulla, ataca, mueve victimas a Colmena
│   └── GestorEventos.java      # daemon que dispara eventos globales
├── zones/          # Recursos compartidos
│   ├── ZonaHawkins.java        # 3 sub-zonas protegidas por un unico ReentrantLock fair
│   ├── ZonaUpsideDown.java     # seleccion atomica de victima
│   ├── Portal.java             # CyclicBarrier + Semaphore + ReentrantLock/Condition
│   └── Colmena.java            # depositarNino synchronized + callback de spawn
├── state/          # Singleton global
│   ├── EstadoGlobal.java       # ReentrantReadWriteLock, evento activo, pausa
│   └── TipoEvento.java         # enum
├── log/
│   └── SistemaLog.java         # singleton thread-safe, escribe hawkins.txt
├── remote/         # Modulo distribuido RMI
│   ├── HawkinsRemote.java      # interfaz Remote
│   ├── EstadoDTO.java          # snapshot Serializable
│   ├── ServidorRemoto.java     # UnicastRemoteObject, puerto 1099
│   └── ClienteRemoto.java      # GUI Swing autonoma, refresco cada 1 s
└── gui/            # Interfaz local
    ├── InicioGUI.java          # portada (inicio.png)
    └── HawkinsGUI.java         # consola del servidor (hawkins_server.png), refresco 500 ms
```

`model` y `zones` no conocen `gui` ni `remote`. `gui` y `remote` dependen del modelo solo en lectura. `state` y `log` son transversales.

---

## Mecanismos de sincronizacion clave

| Mecanismo | Donde | Por que |
|---|---|---|
| `CyclicBarrier(groupSize)` | `Portal` | Formacion de grupo exclusivo antes de cruzar |
| `Semaphore(1, fair)` | `Portal` | Un unico transeunte por portal a la vez |
| `ReentrantLock + Condition` | `Portal` | Prioridad de retorno: las idas esperan en `puedeEntrar.await()` |
| `ReentrantReadWriteLock(true)` | `EstadoGlobal` | Eventos en exclusion, consultas concurrentes sin competir |
| `synchronized` + callback | `Colmena` | Spawn de demogorgon cada 8 capturas desacopla `remote` |
| `AtomicInteger` | `Demogorgon.capturas` | Contador sin lock |
| Holder pattern + `ReentrantLock` | `SistemaLog` | Singleton lazy thread-safe sobre `PrintWriter` |

---

## Modulo distribuido (RMI)

**Interfaz remota** `HawkinsRemote`:

```java
EstadoDTO getEstado()           throws RemoteException;
void      setPausado(boolean p) throws RemoteException;
```

`EstadoDTO` lleva copias inmutables (`Map.copyOf`, `List.copyOf`) para un snapshot consistente. El cliente refresca con `javax.swing.Timer` cada 1 s &mdash; sin que el usuario pulse nada. El boton rojo **PULSADOR DE EMERGENCIA** alterna `setPausado()`. La pausa se implementa con `ReentrantLock + Condition` en `EstadoGlobal`; los hilos invocan `checkPunto()` en puntos seguros.

---

## Ejecutar

**Opcion A &mdash; NetBeans**

1. Abrir el proyecto `Hawkins/` con NetBeans
2. Run (F6) &mdash; `Main` arranca la portada, RMI y todo el sistema

**Opcion B &mdash; consola**

```bash
cd Hawkins
ant compile                     # o: javac -d build/classes -cp lib/AbsoluteLayout.jar src/**/*.java
rmiregistry 1099 &              # opcional, ServidorRemoto crea el registro si no existe
java -cp build/classes:lib/AbsoluteLayout.jar Main
```

El log `hawkins.txt` se genera en el directorio de trabajo con formato:

```
[2026-05-15 18:04:21] El demogorgon D0002 ataca al nino N1234 (capturas: 2)
[2026-05-15 18:04:23] El nino N4567 ha sido capturado
[2026-05-15 18:05:11] EVENTO GLOBAL: Apagon del Laboratorio iniciado
```

---

## Memoria

[DelNogalBuchanan_Daniel.pdf](./DelNogalBuchanan_Daniel.pdf) &mdash; 15 paginas con analisis de alto nivel, diseno, descripcion de clases, diagrama de clases, diagrama de secuencia y anexo con codigo fuente.

---

<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&color=ED8B00&height=100&section=footer" width="100%"/>

*Programacion Avanzada &middot; PECL Convocatoria Ordinaria &middot; UAH GII &middot; 2025-26*
</div>
