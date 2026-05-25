<img src="https://capsule-render.vercel.app/api?type=waving&color=ED8B00&height=160&section=header&text=programacion-avanzada&fontSize=28&fontColor=FFFFFF&fontAlignY=40&desc=Programacion%20Avanzada%20%7C%20UAH%202025-26&descAlignY=60&descColor=FFECB3" width="100%"/>

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![RMI](https://img.shields.io/badge/Java%20RMI-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Threads](https://img.shields.io/badge/Multithreading-F05032?style=for-the-badge)
![UAH](https://img.shields.io/badge/UAH-GII-085041?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-1D9E75?style=for-the-badge)

</div>

---

## About

**Asignatura:** Programacion Avanzada &middot; UAH GII &middot; Curso 2025-26

Advanced Java programming with a focus on **concurrent and distributed systems**. Thread lifecycle, synchronization primitives, deadlock prevention and Java RMI for inter-process communication across the network.

---

## Topics covered

| Topic | Content |
|-------|---------|
| Java concurrency | Thread, Runnable, synchronized, volatile |
| Synchronization | ReentrantLock, ReentrantReadWriteLock, CyclicBarrier, Semaphore, Condition |
| Deadlock | Detection, prevention, avoidance strategies |
| Java RMI | Remote interfaces, stubs, registry, server/client architecture |
| Patterns | Singleton (Holder), Observer-via-callback, DTO snapshots |
| Swing GUI | AbsoluteLayout, javax.swing.Timer, MouseListener |

---

## Practices

| # | Name | Description | Stack |
|---|------|-------------|-------|
| PECL | [batalla-de-hawkins](./batalla-de-hawkins/) | Concurrent + distributed simulation: 1500 ninos and N demogorgons as threads, 4 portals with group barriers and return-priority, 4 random global events, RMI client with live state monitor and pause/resume | Java, RMI, Swing, Threads |

---

## Project structure

```
programacion-avanzada/
└── batalla-de-hawkins/
    ├── README.md
    └── Hawkins/                  # NetBeans project
        ├── build.xml
        ├── manifest.mf
        ├── lib/
        │   └── AbsoluteLayout.jar
        ├── nbproject/
        └── src/
            ├── Main.java
            ├── model/            # Nino, Demogorgon, GestorEventos
            ├── zones/            # ZonaHawkins, ZonaUpsideDown, Portal, Colmena
            ├── state/            # EstadoGlobal, TipoEvento
            ├── log/              # SistemaLog
            ├── remote/           # HawkinsRemote, EstadoDTO, ServidorRemoto, ClienteRemoto
            └── gui/              # InicioGUI, HawkinsGUI
```

---

## Run

```bash
# Opcion A: NetBeans
# Abrir batalla-de-hawkins/Hawkins/ y pulsar Run (F6)

# Opcion B: consola
cd batalla-de-hawkins/Hawkins
ant compile
java -cp build/classes:lib/AbsoluteLayout.jar Main
```

---

<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&color=ED8B00&height=100&section=footer" width="100%"/>

*Programacion Avanzada &middot; UAH GII &middot; 2025-26*
</div>
