package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class InicioGUI extends javax.swing.JFrame {

    private static final String IMG_RES = "/gui/img/inicio.png";
    private static final int    IMG_W   = 1280;
    private static final int    IMG_H   = 740;
    // Coords del botón START dentro de la imagen
    private static final Rectangle BTN_BOUNDS = new Rectangle(388, 410, 460, 72);

    private boolean pressed = false;
    private final Runnable onStart;

    public InicioGUI(Runnable onStart) {
        this.onStart = onStart;
        initComponents();
        setupContent();
        pack();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HAWKINS LAB — SISTEMA DE MONITOREO");
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, IMG_W, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, IMG_H, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void setupContent() {
        BufferedImage bg = cargarImagen();
        InicioPanel panel = new InicioPanel(bg);
        panel.setPreferredSize(new Dimension(IMG_W, IMG_H));
        setContentPane(panel);
    }

    // Primero pruebo desde el classpath, si falla busco en disco. Último recurso: rectángulo verde
    private BufferedImage cargarImagen() {
        try (InputStream in = getClass().getResourceAsStream(IMG_RES)) {
            if (in != null) return ImageIO.read(in);
        } catch (IOException ignored) {}
        try {
            return ImageIO.read(new java.io.File("src/gui/img/inicio.png"));
        } catch (IOException ex) {
            BufferedImage ph = new BufferedImage(IMG_W, IMG_H, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = ph.createGraphics();
            g.setColor(new Color(8, 18, 10));
            g.fillRect(0, 0, IMG_W, IMG_H);
            g.dispose();
            return ph;
        }
    }

    private class InicioPanel extends JPanel {
        private final BufferedImage bg;
        private boolean hover = false;

        InicioPanel(BufferedImage bg) {
            this.bg = bg;
            setPreferredSize(new Dimension(bg.getWidth(), bg.getHeight()));
            setLayout(null);

            addMouseMotionListener(new MouseAdapter() {
                @Override public void mouseMoved(MouseEvent e) {
                    boolean h = BTN_BOUNDS.contains(e.getPoint());
                    if (h != hover) { hover = h; repaint(); }
                    setCursor(h ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                                : Cursor.getDefaultCursor());
                }
            });
            addMouseListener(new MouseAdapter() {
                @Override public void mousePressed(MouseEvent e) {
                    if (BTN_BOUNDS.contains(e.getPoint())) { pressed = true; repaint(); }
                }
                @Override public void mouseReleased(MouseEvent e) {
                    if (pressed && BTN_BOUNDS.contains(e.getPoint())) {
                        pressed = false; repaint();
                        // Pequeño delay para que se vea la animación de pulsado
                        Timer t = new Timer(180, ev -> {
                            ((Timer) ev.getSource()).stop();
                            dispose();
                            if (onStart != null) onStart.run();
                        });
                        t.setRepeats(false); t.start();
                    } else { pressed = false; repaint(); }
                }
                @Override public void mouseExited(MouseEvent e) {
                    if (hover) { hover = false; repaint(); }
                }
            });

            // Atajo: ENTER también arranca el sistema
            KeyStroke ks = KeyStroke.getKeyStroke("ENTER");
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(ks, "start");
            getActionMap().put("start", new AbstractAction() {
                @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                    pressed = true; repaint();
                    Timer t = new Timer(220, ev -> {
                        ((Timer) ev.getSource()).stop();
                        pressed = false;
                        dispose();
                        if (onStart != null) onStart.run();
                    });
                    t.setRepeats(false); t.start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

            // Overlay según estado del botón: pulsado oscurece, hover ilumina
            if (pressed) {
                g2.setColor(new Color(0, 0, 0, 110));
                g2.fillRect(BTN_BOUNDS.x + 2, BTN_BOUNDS.y + 3,
                            BTN_BOUNDS.width, BTN_BOUNDS.height);
                g2.setColor(new Color(255, 255, 255, 35));
                g2.fillRect(BTN_BOUNDS.x, BTN_BOUNDS.y,
                            BTN_BOUNDS.width, BTN_BOUNDS.height);
            } else if (hover) {
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillRect(BTN_BOUNDS.x, BTN_BOUNDS.y,
                            BTN_BOUNDS.width, BTN_BOUNDS.height);
                g2.setColor(new Color(80, 255, 120, 180));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRect(BTN_BOUNDS.x - 1, BTN_BOUNDS.y - 1,
                            BTN_BOUNDS.width + 2, BTN_BOUNDS.height + 2);
            }
            g2.dispose();
        }
    }
}
