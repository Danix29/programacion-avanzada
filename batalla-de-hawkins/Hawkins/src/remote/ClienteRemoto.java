package remote;

public class ClienteRemoto extends javax.swing.JFrame {

    private static final int    INTERVALO_MS = 1000;
    private static final String SERVICIO     = "HawkinsService";
    private static final int    PUERTO       = 1099;

    private remote.HawkinsRemote stub;
    private final String host;
    private boolean pausadoLocal = false;

    public ClienteRemoto(String host) {
        this.host = host;
        initComponents();
        conectar();
        // Refresco automático: una llamada RMI por segundo
        new javax.swing.Timer(INTERVALO_MS, e -> tick()).start();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblP1 = new javax.swing.JLabel();
        lblSujetos = new javax.swing.JLabel();
        lblP4 = new javax.swing.JLabel();
        lblP3 = new javax.swing.JLabel();
        lblSupC = new javax.swing.JLabel();
        lblSupL = new javax.swing.JLabel();
        lblSupB = new javax.swing.JLabel();
        lblR3 = new javax.swing.JLabel();
        lblTempo = new javax.swing.JLabel();
        lblP2 = new javax.swing.JLabel();
        lblFooter = new javax.swing.JLabel();
        lblEvL1 = new javax.swing.JLabel();
        lblColmena = new javax.swing.JLabel();
        lblSupA = new javax.swing.JLabel();
        lblR1 = new javax.swing.JLabel();
        lblOtroA = new javax.swing.JLabel();
        lblOtroC = new javax.swing.JLabel();
        lblOtroL = new javax.swing.JLabel();
        lblOtroB = new javax.swing.JLabel();
        lblR2 = new javax.swing.JLabel();
        lblEvL2 = new javax.swing.JLabel();
        lblEvL3 = new javax.swing.JLabel();
        btnPulsador = new javax.swing.JButton();
        lblBg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 751));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblP1.setBackground(new java.awt.Color(205, 198, 152));
        lblP1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblP1.setForeground(new java.awt.Color(51, 51, 51));
        lblP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP1.setOpaque(true);
        getContentPane().add(lblP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 50, 35));

        lblSujetos.setBackground(new java.awt.Color(34, 68, 41));
        lblSujetos.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        lblSujetos.setForeground(new java.awt.Color(255, 0, 0));
        lblSujetos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSujetos.setOpaque(true);
        getContentPane().add(lblSujetos, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 80, 40));

        lblP4.setBackground(new java.awt.Color(205, 198, 152));
        lblP4.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblP4.setForeground(new java.awt.Color(51, 51, 51));
        lblP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP4.setOpaque(true);
        getContentPane().add(lblP4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 50, 35));

        lblP3.setBackground(new java.awt.Color(205, 198, 152));
        lblP3.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblP3.setForeground(new java.awt.Color(51, 51, 51));
        lblP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP3.setOpaque(true);
        getContentPane().add(lblP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 530, 50, 35));

        lblSupC.setBackground(new java.awt.Color(202, 250, 198));
        lblSupC.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblSupC.setForeground(new java.awt.Color(51, 51, 51));
        lblSupC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupC.setOpaque(true);
        getContentPane().add(lblSupC, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 50, 40));

        lblSupL.setBackground(new java.awt.Color(202, 250, 198));
        lblSupL.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblSupL.setForeground(new java.awt.Color(51, 51, 51));
        lblSupL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupL.setOpaque(true);
        getContentPane().add(lblSupL, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 320, 50, 40));

        lblSupB.setBackground(new java.awt.Color(202, 250, 198));
        lblSupB.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblSupB.setForeground(new java.awt.Color(51, 51, 51));
        lblSupB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupB.setOpaque(true);
        getContentPane().add(lblSupB, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 50, 60));

        lblR3.setBackground(new java.awt.Color(196, 198, 176));
        lblR3.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblR3.setForeground(new java.awt.Color(51, 51, 51));
        lblR3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR3.setOpaque(true);
        getContentPane().add(lblR3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 330, 55, 40));

        lblTempo.setBackground(new java.awt.Color(7, 17, 9));
        lblTempo.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblTempo.setForeground(new java.awt.Color(150, 250, 150));
        lblTempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTempo.setOpaque(true);
        getContentPane().add(lblTempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 610, 90, 30));

        lblP2.setBackground(new java.awt.Color(205, 198, 152));
        lblP2.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblP2.setForeground(new java.awt.Color(51, 51, 51));
        lblP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP2.setOpaque(true);
        getContentPane().add(lblP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 460, 50, 35));

        lblFooter.setBackground(new java.awt.Color(108, 103, 83));
        lblFooter.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblFooter.setForeground(new java.awt.Color(51, 51, 51));
        lblFooter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFooter.setOpaque(true);
        getContentPane().add(lblFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 720, 380, 30));

        lblEvL1.setBackground(new java.awt.Color(14, 28, 16));
        lblEvL1.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblEvL1.setForeground(new java.awt.Color(150, 250, 150));
        lblEvL1.setOpaque(true);
        getContentPane().add(lblEvL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 490, 140, 30));

        lblColmena.setBackground(new java.awt.Color(209, 252, 198));
        lblColmena.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblColmena.setForeground(new java.awt.Color(153, 0, 0));
        lblColmena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblColmena.setOpaque(true);
        getContentPane().add(lblColmena, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 540, 45, 35));

        lblSupA.setBackground(new java.awt.Color(202, 250, 198));
        lblSupA.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblSupA.setForeground(new java.awt.Color(51, 51, 51));
        lblSupA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupA.setOpaque(true);
        getContentPane().add(lblSupA, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 460, 50, 50));

        lblR1.setBackground(new java.awt.Color(196, 198, 176));
        lblR1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblR1.setForeground(new java.awt.Color(51, 51, 51));
        lblR1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR1.setOpaque(true);
        getContentPane().add(lblR1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 210, 55, 40));

        lblOtroA.setBackground(new java.awt.Color(174, 225, 168));
        lblOtroA.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblOtroA.setForeground(new java.awt.Color(51, 51, 51));
        lblOtroA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtroA.setOpaque(true);
        getContentPane().add(lblOtroA, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 470, 45, 30));

        lblOtroC.setBackground(new java.awt.Color(174, 225, 168));
        lblOtroC.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblOtroC.setForeground(new java.awt.Color(51, 51, 51));
        lblOtroC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtroC.setOpaque(true);
        getContentPane().add(lblOtroC, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 415, 60, 45));

        lblOtroL.setBackground(new java.awt.Color(174, 225, 168));
        lblOtroL.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblOtroL.setForeground(new java.awt.Color(51, 51, 51));
        lblOtroL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtroL.setOpaque(true);
        getContentPane().add(lblOtroL, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 310, 80, 60));

        lblOtroB.setBackground(new java.awt.Color(174, 225, 168));
        lblOtroB.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblOtroB.setForeground(new java.awt.Color(51, 51, 51));
        lblOtroB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtroB.setOpaque(true);
        getContentPane().add(lblOtroB, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 255, 50, 45));

        lblR2.setBackground(new java.awt.Color(196, 198, 176));
        lblR2.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblR2.setForeground(new java.awt.Color(51, 51, 51));
        lblR2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblR2.setOpaque(true);
        getContentPane().add(lblR2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 270, 55, 40));

        lblEvL2.setBackground(new java.awt.Color(14, 28, 16));
        lblEvL2.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblEvL2.setForeground(new java.awt.Color(150, 250, 150));
        lblEvL2.setOpaque(true);
        getContentPane().add(lblEvL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 520, 170, 30));

        lblEvL3.setBackground(new java.awt.Color(14, 28, 16));
        lblEvL3.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblEvL3.setForeground(new java.awt.Color(150, 250, 150));
        lblEvL3.setOpaque(true);
        getContentPane().add(lblEvL3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 550, 130, 30));

        btnPulsador.setToolTipText("PAUSAR / REANUDAR la simulación remota");
        btnPulsador.setBorderPainted(false);
        btnPulsador.setContentAreaFilled(false);
        btnPulsador.addActionListener(this::btnPulsadorActionPerformed);
        getContentPane().add(btnPulsador, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 610, 160, 110));

        lblBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remote/img/consola.png"))); // NOI18N
        getContentPane().add(lblBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Pausa en el servidor
    private void btnPulsadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPulsadorActionPerformed
        if (stub == null) return;
        try {
            pausadoLocal = !pausadoLocal;
            stub.setPausado(pausadoLocal);
        } catch (Exception ex) {
            setTitle("Error pulsador: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnPulsadorActionPerformed

    // Busco el servicio en el registro RMI
    private void conectar() {
        try {
            java.rmi.registry.Registry reg = java.rmi.registry.LocateRegistry.getRegistry(host, PUERTO);
            stub = (remote.HawkinsRemote) reg.lookup(SERVICIO);
            setTitle("Cliente RMI - Conectado a " + host + ":" + PUERTO);
        } catch (Exception e) {
            setTitle("Cliente RMI - Error de conexion: " + e.getMessage());
        }
    }

    // Cada segundo: pido el estado al servidor y refresco la GUI
    private void tick() {
        if (stub == null) { conectar(); return; }
        try { actualizar(stub.getEstado()); }
        catch (Exception ex) {
            setTitle("Cliente RMI - Conexion perdida: " + ex.getMessage());
            stub = null;
        }
    }

    private void actualizar(remote.EstadoDTO dto) {
        lblSujetos.setText("[" + dto.totalNinosHawkins + "]");

        String[] keys = {"BOSQUE","LABORATORIO","CENTRO_COMERCIAL","ALCANTARILLADO"};
        int p1 = dto.ninosPorPortal.getOrDefault(keys[0], 0);
        int p2 = dto.ninosPorPortal.getOrDefault(keys[1], 0);
        int p3 = dto.ninosPorPortal.getOrDefault(keys[2], 0);
        int p4 = dto.ninosPorPortal.getOrDefault(keys[3], 0);
        lblP1.setText("[" + p1 + "]");
        lblP2.setText("[" + p2 + "]");
        lblP3.setText("[" + p3 + "]");
        lblP4.setText("[" + p4 + "]");

        lblSupB.setText("(" + p1 + ")");
        lblSupL.setText("(" + p2 + ")");
        lblSupC.setText("(" + p3 + ")");
        lblSupA.setText("(" + p4 + ")");

        lblOtroB.setText("(" + dto.ninosPorZonaUD.getOrDefault(keys[0], 0) + ")");
        lblOtroL.setText("(" + dto.ninosPorZonaUD.getOrDefault(keys[1], 0) + ")");
        lblOtroC.setText("(" + dto.ninosPorZonaUD.getOrDefault(keys[2], 0) + ")");
        lblOtroA.setText("(" + dto.ninosPorZonaUD.getOrDefault(keys[3], 0) + ")");

        lblColmena.setText("[" + dto.ninosCapturadosColmena + "]");

        setRank(lblR1, dto, 0);
        setRank(lblR2, dto, 1);
        setRank(lblR3, dto, 2);

        if (dto.eventoActivo == null) {
            lblEvL1.setText("Sin evento"); lblEvL2.setText("activo"); lblEvL3.setText("");
            lblTempo.setText("--:--");
        } else {
            String[] d = describir(dto.eventoActivo);
            lblEvL1.setText(d[0]); lblEvL2.setText(d[1]); lblEvL3.setText(d[2]);
            lblTempo.setText(String.format("00:%02ds", dto.tiempoRestanteSeg));
        }
        lblFooter.setText(" " + java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            + "  -  CLIENTE REMOTO ");
        pausadoLocal = dto.pausado;
    }

    private void setRank(javax.swing.JLabel l, remote.EstadoDTO dto, int idx) {
        l.setText(idx >= dto.rankingDemogorgons.size() ? "(--)"
                : "(" + dto.rankingDemogorgons.get(idx)[1] + ")");
    }

    // Texto legible para cada tipo de evento
    private static String[] describir(String tipo) {
        switch (tipo) {
            case "APAGON_LABORATORIO":   return new String[]{"TIPO: Interrupcion","Total de Energia","(APAGON LAB)"};
            case "TORMENTA_UPSIDE_DOWN": return new String[]{"TIPO: Tormenta","del Upside Down",""};
            case "INTERVENCION_ELEVEN":  return new String[]{"TIPO: Intervencion","de Eleven",""};
            case "RED_MENTAL":           return new String[]{"TIPO: La Red","Mental (Mindflayer)",""};
            default:                     return new String[]{tipo,"",""};
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPulsador;
    private javax.swing.JLabel lblBg;
    private javax.swing.JLabel lblColmena;
    private javax.swing.JLabel lblEvL1;
    private javax.swing.JLabel lblEvL2;
    private javax.swing.JLabel lblEvL3;
    private javax.swing.JLabel lblFooter;
    private javax.swing.JLabel lblOtroA;
    private javax.swing.JLabel lblOtroB;
    private javax.swing.JLabel lblOtroC;
    private javax.swing.JLabel lblOtroL;
    private javax.swing.JLabel lblP1;
    private javax.swing.JLabel lblP2;
    private javax.swing.JLabel lblP3;
    private javax.swing.JLabel lblP4;
    private javax.swing.JLabel lblR1;
    private javax.swing.JLabel lblR2;
    private javax.swing.JLabel lblR3;
    private javax.swing.JLabel lblSujetos;
    private javax.swing.JLabel lblSupA;
    private javax.swing.JLabel lblSupB;
    private javax.swing.JLabel lblSupC;
    private javax.swing.JLabel lblSupL;
    private javax.swing.JLabel lblTempo;
    // End of variables declaration//GEN-END:variables
}
