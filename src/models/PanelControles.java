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
    private JButton btnConectar;
    private JButton btnInicio; // Nuevo
    private JButton btnFin;    // Nuevo
    private JButton btnBFS;    // Nuevo: Ejecutar Algoritmo

    public PanelControles() {
        setOpaque(false);
        setLayout(new GridLayout(0, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnCrear = new JButton("Crear Nodo");
        btnConectar = new JButton("Conectar Nodos");
        btnInicio = new JButton("Definir Inicio (A)");
        btnFin = new JButton("Definir Destino (B)");
        btnBFS = new JButton("▶ Ejecutar BFS");

        estilizarBoton(btnCrear);
        estilizarBoton(btnConectar);
        estilizarBoton(btnInicio);
        estilizarBoton(btnFin);
        estilizarBoton(btnBFS);
        
        // Destacamos el botón de ejecutar
        btnBFS.setBackground(new Color(100, 200, 100));
        btnBFS.setForeground(Color.WHITE);

        add(btnCrear);
        add(btnConectar);
        add(btnInicio);
        add(btnFin);
        add(btnBFS);
    }

    private void estilizarBoton(JButton btn) {
        btn.setFocusPainted(false);
        if (!btn.getText().contains("BFS")) {
            btn.setBackground(new Color(245, 245, 245));
            btn.setForeground(Color.BLACK);
        }
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(30, 30, 30, 200));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        super.paintComponent(g);
    }

    // Getters
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnConectar() { return btnConectar; }
    public JButton getBtnInicio() { return btnInicio; }
    public JButton getBtnFin() { return btnFin; }
    public JButton getBtnBFS() { return btnBFS; }
}