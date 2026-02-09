package models;

import java.awt.*;
import javax.swing.*;

public class PanelControles extends JPanel {

    private JButton btnCrear, btnBiDir, btnUniDir, btnInicio, btnFin, btnEliminar;
    private JButton btnBFS, btnDFS;
    private JButton btnGuardar, btnCargar, btnLimpiar;
    private JCheckBox chkExploracion;

    public PanelControles() {
        setOpaque(false);
        setLayout(new GridLayout(0, 1, 8, 8)); 
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // seccion de edicion
        add(crearEtiqueta("EDICION"));
        btnCrear = crearBoton("Crear Nodo");
        btnBiDir = crearBoton("Conectar Doble");
        btnUniDir = crearBoton("Conectar Uno");
        btnEliminar = crearBoton("Eliminar");
        
        add(btnCrear);
        add(btnBiDir);
        add(btnUniDir);
        add(btnEliminar);

        // seccion para definir ruta
        add(createSeparador());
        add(crearEtiqueta("DEFINIR RUTA"));
        btnInicio = crearBoton("Inicio (A)");
        btnFin = crearBoton("Destino (B)");
        add(btnInicio);
        add(btnFin);

        // botones de algoritmos
        add(createSeparador());
        add(crearEtiqueta("ALGORITMOS"));
        
        JPanel panelAlgo = new JPanel(new GridLayout(1, 2, 5, 0));
        panelAlgo.setOpaque(false);
        btnBFS = new JButton("BFS");
        btnDFS = new JButton("DFS");
        estilizarAccion(btnBFS);
        estilizarAccion(btnDFS);
        panelAlgo.add(btnBFS);
        panelAlgo.add(btnDFS);
        add(panelAlgo);

        chkExploracion = new JCheckBox("Ver Exploracion");
        chkExploracion.setForeground(Color.WHITE);
        chkExploracion.setOpaque(false);
        add(chkExploracion);

        // seccion de archivo
        add(createSeparador());
        add(crearEtiqueta("ARCHIVO"));
        btnGuardar = crearBoton("Guardar");
        btnCargar = crearBoton("Cargar");
        btnLimpiar = crearBoton("Limpiar Todo");
        
        add(btnGuardar);
        add(btnCargar);
        add(btnLimpiar);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(new Color(200, 200, 200));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        return lbl;
    }
    
    private JSeparator createSeparador() {
        JSeparator s = new JSeparator();
        s.setForeground(new Color(100,100,100));
        return s;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 240, 240));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void estilizarAccion(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(30, 30, 30, 220)); 
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }

    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnBiDir() { return btnBiDir; }
    public JButton getBtnUniDir() { return btnUniDir; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnInicio() { return btnInicio; }
    public JButton getBtnFin() { return btnFin; }
    public JButton getBtnBFS() { return btnBFS; }
    public JButton getBtnDFS() { return btnDFS; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCargar() { return btnCargar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JCheckBox getChkExploracion() { return chkExploracion; }
}