package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelControles extends JPanel {

    private JButton btnCrear;
    private JButton btnEliminar;
    private JButton btnConectar;

    public PanelControles() {
        // Configuración del panel
        setOpaque(false); // Hacemos false para pintar nuestro propio fondo redondeado
        setLayout(new GridLayout(0, 1, 10, 10)); // Botones en columna
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes internos

        // Inicializar botones
        btnCrear = new JButton("Crear Nodo");
        btnConectar = new JButton("Conectar Nodos");
        btnEliminar = new JButton("Eliminar Nodo");

        // Estilizar botones (Opcional, para que se vean mejor)
        estilizarBoton(btnCrear);
        estilizarBoton(btnConectar);
        estilizarBoton(btnEliminar);

        add(btnCrear);
        add(btnConectar);
        add(btnEliminar);
    }

    private void estilizarBoton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(245, 245, 245));
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }

    // Dibujamos el fondo redondeado y semitransparente
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo negro con transparencia (Alfa 180)
        g2.setColor(new Color(30, 30, 30, 180));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        
        super.paintComponent(g);
    }

    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnConectar() { return btnConectar; }
}