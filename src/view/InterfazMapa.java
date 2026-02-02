package view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import models.PanelControles;
import models.PanelMapa;

public class InterfazMapa extends JFrame {

    private PanelMapa panelMapa;
    private PanelControles panelControl;
    private JLayeredPane capas;

    public InterfazMapa() {
        setTitle("Ruta Óptima - Proyecto Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        capas = new JLayeredPane();
        setContentPane(capas);

        panelMapa = new PanelMapa();
        panelControl = new PanelControles();

        capas.add(panelMapa, JLayeredPane.DEFAULT_LAYER);
        capas.add(panelControl, JLayeredPane.PALETTE_LAYER);

        capas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panelMapa.setBounds(0, 0, getWidth(), getHeight());
                panelControl.setBounds(20, 20, 200, 250); // Aumenté un poco el alto
            }
        });

        configurarEventos();
        cargarMapaInicial();
    }

    private void configurarEventos() {
        // Modo 1: Crear Nodos
        panelControl.getBtnCrear().addActionListener(e -> panelMapa.setModoCrear());
        
        // Modo 2: Conectar Nodos
        panelControl.getBtnConectar().addActionListener(e -> panelMapa.setModoConectar());
        
        // Modo 3: Definir Inicio
        panelControl.getBtnInicio().addActionListener(e -> panelMapa.setModoInicio());
        
        // Modo 4: Definir Fin
        panelControl.getBtnFin().addActionListener(e -> panelMapa.setModoFin());

        // Acción: Ejecutar Algoritmo BFS
        panelControl.getBtnBFS().addActionListener(e -> panelMapa.ejecutarBFS());
    }

    private void cargarMapaInicial() {
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/mapa.png"));
            panelMapa.setImagen(icono.getImage());
        } catch (Exception e) {
            System.out.println("Imagen no encontrada.");
        }
    }
}