package gui;

public class HawkinsGUI extends javax.swing.JFrame {

    private final zones.ZonaHawkins hawkins;
    private final zones.Portal[] portales;
    private final java.util.List<zones.ZonaUpsideDown> zonasUD;
    private final zones.Colmena colmena;
    private final state.EstadoGlobal estado;

    public HawkinsGUI(zones.ZonaHawkins hawkins, zones.Portal[] portales,
                      java.util.List<zones.ZonaUpsideDown> zonasUD,
                      zones.Colmena colmena, state.EstadoGlobal estado) {
        this.hawkins = hawkins;
        this.portales = portales;
        this.zonasUD = zonasUD;
        this.colmena = colmena;
        this.estado = estado;
        initComponents();
        // Refresco cada 500ms desde el EDT
        new javax.swing.Timer(500, e -> refresh()).start();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSotano = new javax.swing.JLabel();
        lblCalleP = new javax.swing.JLabel();
        lblSangre = new javax.swing.JLabel();
        lblP4B = new javax.swing.JLabel();
        lblP4A = new javax.swing.JLabel();
        lblP3B = new javax.swing.JLabel();
        lblP3A = new javax.swing.JLabel();
        lblP2B = new javax.swing.JLabel();
        lblP2A = new javax.swing.JLabel();
        lblP1B = new javax.swing.JLabel();
        lblP1A = new javax.swing.JLabel();
        lblRadio = new javax.swing.JLabel();
        lblFooter = new javax.swing.JLabel();
        lblEvento = new javax.swing.JLabel();
        lblColmena = new javax.swing.JLabel();
        lblUDAD = new javax.swing.JLabel();
        lblUDAN = new javax.swing.JLabel();
        lblUDCD = new javax.swing.JLabel();
        lblUDCN = new javax.swing.JLabel();
        lblUDLD = new javax.swing.JLabel();
        lblUDLN = new javax.swing.JLabel();
        lblUDBD = new javax.swing.JLabel();
        lblUDBN = new javax.swing.JLabel();
        lblBg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1280, 751));
        setMinimumSize(new java.awt.Dimension(1280, 751));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSotano.setBackground(new java.awt.Color(34, 68, 41));
        lblSotano.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblSotano.setForeground(new java.awt.Color(150, 250, 150));
        lblSotano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSotano.setOpaque(true);
        getContentPane().add(lblSotano, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 280, 80));

        lblCalleP.setBackground(new java.awt.Color(34, 68, 41));
        lblCalleP.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblCalleP.setForeground(new java.awt.Color(150, 250, 150));
        lblCalleP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCalleP.setOpaque(true);
        getContentPane().add(lblCalleP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 280, 90));

        lblSangre.setBackground(new java.awt.Color(56, 34, 21));
        lblSangre.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblSangre.setForeground(new java.awt.Color(153, 0, 0));
        lblSangre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSangre.setOpaque(true);
        getContentPane().add(lblSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 590, 50, 30));

        lblP4B.setBackground(new java.awt.Color(25, 58, 29));
        lblP4B.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP4B.setForeground(new java.awt.Color(150, 250, 150));
        lblP4B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP4B.setOpaque(true);
        getContentPane().add(lblP4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 560, 220, 60));

        lblP4A.setBackground(new java.awt.Color(34, 68, 41));
        lblP4A.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP4A.setForeground(new java.awt.Color(150, 250, 150));
        lblP4A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP4A.setOpaque(true);
        getContentPane().add(lblP4A, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 220, 50));

        lblP3B.setBackground(new java.awt.Color(25, 58, 29));
        lblP3B.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP3B.setForeground(new java.awt.Color(150, 250, 150));
        lblP3B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP3B.setOpaque(true);
        getContentPane().add(lblP3B, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 440, 220, 50));

        lblP3A.setBackground(new java.awt.Color(34, 68, 41));
        lblP3A.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP3A.setForeground(new java.awt.Color(150, 250, 150));
        lblP3A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP3A.setOpaque(true);
        getContentPane().add(lblP3A, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 390, 220, 50));

        lblP2B.setBackground(new java.awt.Color(25, 58, 29));
        lblP2B.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP2B.setForeground(new java.awt.Color(150, 250, 150));
        lblP2B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP2B.setOpaque(true);
        getContentPane().add(lblP2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 320, 220, 60));

        lblP2A.setBackground(new java.awt.Color(34, 68, 41));
        lblP2A.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP2A.setForeground(new java.awt.Color(150, 250, 150));
        lblP2A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP2A.setOpaque(true);
        getContentPane().add(lblP2A, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, 220, 60));

        lblP1B.setBackground(new java.awt.Color(25, 58, 29));
        lblP1B.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP1B.setForeground(new java.awt.Color(150, 250, 150));
        lblP1B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP1B.setOpaque(true);
        getContentPane().add(lblP1B, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, 220, 60));

        lblP1A.setBackground(new java.awt.Color(34, 68, 41));
        lblP1A.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblP1A.setForeground(new java.awt.Color(150, 250, 150));
        lblP1A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblP1A.setOpaque(true);
        getContentPane().add(lblP1A, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 220, 60));

        lblRadio.setBackground(new java.awt.Color(34, 68, 41));
        lblRadio.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblRadio.setForeground(new java.awt.Color(150, 250, 150));
        lblRadio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRadio.setOpaque(true);
        getContentPane().add(lblRadio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 560, 80, 60));

        lblFooter.setBackground(new java.awt.Color(108, 103, 83));
        lblFooter.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblFooter.setForeground(new java.awt.Color(51, 51, 51));
        lblFooter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFooter.setOpaque(true);
        getContentPane().add(lblFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 660, 420, 30));

        lblEvento.setBackground(new java.awt.Color(14, 28, 16));
        lblEvento.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblEvento.setForeground(new java.awt.Color(150, 250, 150));
        lblEvento.setOpaque(true);
        getContentPane().add(lblEvento, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 550, 30));

        lblColmena.setBackground(new java.awt.Color(56, 34, 21));
        lblColmena.setFont(new java.awt.Font("Monospaced", 1, 36)); // NOI18N
        lblColmena.setForeground(new java.awt.Color(153, 0, 0));
        lblColmena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblColmena.setOpaque(true);
        getContentPane().add(lblColmena, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 210, 90, 60));

        lblUDAD.setBackground(new java.awt.Color(25, 58, 29));
        lblUDAD.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDAD.setForeground(new java.awt.Color(150, 250, 150));
        lblUDAD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDAD.setOpaque(true);
        getContentPane().add(lblUDAD, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 550, 180, 50));

        lblUDAN.setBackground(new java.awt.Color(34, 68, 41));
        lblUDAN.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDAN.setForeground(new java.awt.Color(150, 250, 150));
        lblUDAN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDAN.setOpaque(true);
        getContentPane().add(lblUDAN, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 500, 180, 50));

        lblUDCD.setBackground(new java.awt.Color(25, 58, 29));
        lblUDCD.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDCD.setForeground(new java.awt.Color(150, 250, 150));
        lblUDCD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDCD.setOpaque(true);
        getContentPane().add(lblUDCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 440, 180, 50));

        lblUDCN.setBackground(new java.awt.Color(34, 68, 41));
        lblUDCN.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDCN.setForeground(new java.awt.Color(150, 250, 150));
        lblUDCN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDCN.setOpaque(true);
        getContentPane().add(lblUDCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 380, 180, 60));

        lblUDLD.setBackground(new java.awt.Color(25, 58, 29));
        lblUDLD.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDLD.setForeground(new java.awt.Color(150, 250, 150));
        lblUDLD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDLD.setOpaque(true);
        getContentPane().add(lblUDLD, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 310, 190, 50));

        lblUDLN.setBackground(new java.awt.Color(34, 68, 41));
        lblUDLN.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDLN.setForeground(new java.awt.Color(150, 250, 150));
        lblUDLN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDLN.setOpaque(true);
        getContentPane().add(lblUDLN, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 260, 190, 50));

        lblUDBD.setBackground(new java.awt.Color(25, 58, 29));
        lblUDBD.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDBD.setForeground(new java.awt.Color(150, 250, 150));
        lblUDBD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDBD.setOpaque(true);
        getContentPane().add(lblUDBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 190, 190, 60));

        lblUDBN.setBackground(new java.awt.Color(34, 68, 41));
        lblUDBN.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        lblUDBN.setForeground(new java.awt.Color(150, 250, 150));
        lblUDBN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDBN.setOpaque(true);
        getContentPane().add(lblUDBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 190, 60));

        lblBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/img/hawkins_server.png"))); // NOI18N
        getContentPane().add(lblBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Lo dispara el Timer 2 veces por segundo: vuelca el modelo a los labels
    private void refresh() {
        lblCalleP.setText(join(hawkins.getCallePrincipalIds()));
        lblSotano.setText(join(hawkins.getSotanoIds()));
        lblRadio .setText(join(hawkins.getRadioIds()));
        lblSangre.setText(String.valueOf(estado.getSangreTotal()));

        javax.swing.JLabel[] pa = {lblP1A, lblP2A, lblP3A, lblP4A};
        javax.swing.JLabel[] pb = {lblP1B, lblP2B, lblP3B, lblP4B};
        for (int i = 0; i < 4; i++) {
            int n = portales[i].getTotalEsperando();
            pa[i].setText(portales[i].getDestino().name() + " [" + n + "]");
            pb[i].setText(n > 0 ? "esperando" : "-");
        }
        javax.swing.JLabel[] un = {lblUDBN, lblUDLN, lblUDCN, lblUDAN};
        javax.swing.JLabel[] ud = {lblUDBD, lblUDLD, lblUDCD, lblUDAD};
        for (int i = 0; i < 4; i++) {
            un[i].setText(join(zonasUD.get(i).getNinoIds()));
            ud[i].setText(join(zonasUD.get(i).getDemogorgonIds()));
        }
        lblColmena.setText(String.valueOf(colmena.getNinosCapturados()));

        state.TipoEvento ev = estado.getEventoActivo();
        lblEvento.setText(ev == null ? " EVENTO: - "
            : " EVENTO: " + ev.name() + " (" + (estado.getTiempoRestanteMs()/1000) + "s) ");
        lblFooter.setText(" " + java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            + "  -  OPERADOR H.E. ");
    }

    // Si hay más de 3 IDs, trunco con "+N" para no desbordar el label
    private static String join(java.util.List<String> l) {
        if (l.isEmpty()) return "-";
        return l.size() <= 3 ? String.join(", ", l)
                             : String.join(", ", l.subList(0, 3)) + " +" + (l.size()-3);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBg;
    private javax.swing.JLabel lblCalleP;
    private javax.swing.JLabel lblColmena;
    private javax.swing.JLabel lblEvento;
    private javax.swing.JLabel lblFooter;
    private javax.swing.JLabel lblP1A;
    private javax.swing.JLabel lblP1B;
    private javax.swing.JLabel lblP2A;
    private javax.swing.JLabel lblP2B;
    private javax.swing.JLabel lblP3A;
    private javax.swing.JLabel lblP3B;
    private javax.swing.JLabel lblP4A;
    private javax.swing.JLabel lblP4B;
    private javax.swing.JLabel lblRadio;
    private javax.swing.JLabel lblSangre;
    private javax.swing.JLabel lblSotano;
    private javax.swing.JLabel lblUDAD;
    private javax.swing.JLabel lblUDAN;
    private javax.swing.JLabel lblUDBD;
    private javax.swing.JLabel lblUDBN;
    private javax.swing.JLabel lblUDCD;
    private javax.swing.JLabel lblUDCN;
    private javax.swing.JLabel lblUDLD;
    private javax.swing.JLabel lblUDLN;
    // End of variables declaration//GEN-END:variables
}
