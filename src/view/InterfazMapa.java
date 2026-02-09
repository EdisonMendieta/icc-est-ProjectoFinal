package view;

import java.awt.MediaTracker;
import java.io.File;
import javax.swing.*;
import models.PanelControles;
import models.PanelMapa;

public class InterfazMapa extends JFrame {

    private PanelMapa panelMapa;
    private PanelControles panelControl;
    private JLayeredPane capas;

    public InterfazMapa() {
        setTitle("Sistema de Rutas Optimas - Proyecto Final");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // tamaño fijo para que no se deforme el mapa
        setSize(1280, 720); 
        setResizable(false); 
        setLocationRelativeTo(null);

        capas = new JLayeredPane();
        setContentPane(capas);

        panelMapa = new PanelMapa();
        panelControl = new PanelControles();

        capas.add(panelMapa, JLayeredPane.DEFAULT_LAYER);
        capas.add(panelControl, JLayeredPane.PALETTE_LAYER);

        // posicionamos los paneles manualmente
        panelMapa.setBounds(0, 0, 1280, 720);
        panelControl.setBounds(20, 20, 200, 480); 

        configurarEventos();
        cargarMapaInicial();
    }

    private void configurarEventos() {
        // eventos de edicion
        panelControl.getBtnCrear().addActionListener(e -> panelMapa.setModo(1));
        panelControl.getBtnBiDir().addActionListener(e -> panelMapa.setModo(2));
        panelControl.getBtnUniDir().addActionListener(e -> panelMapa.setModo(3));
        panelControl.getBtnEliminar().addActionListener(e -> panelMapa.setModo(6));

        // eventos para puntos de ruta
        panelControl.getBtnInicio().addActionListener(e -> panelMapa.setModo(4));
        panelControl.getBtnFin().addActionListener(e -> panelMapa.setModo(5));

        // eventos de algoritmos
        panelControl.getChkExploracion().addActionListener(e -> {
            panelMapa.setModoExploracion(panelControl.getChkExploracion().isSelected());
        });

        panelControl.getBtnBFS().addActionListener(e -> panelMapa.ejecutarAlgoritmo(true)); 
        panelControl.getBtnDFS().addActionListener(e -> panelMapa.ejecutarAlgoritmo(false)); 

        // eventos de archivo
        panelControl.getBtnGuardar().addActionListener(e -> guardarGrafo());
        panelControl.getBtnCargar().addActionListener(e -> cargarGrafo());
        panelControl.getBtnLimpiar().addActionListener(e -> {
            panelMapa.getBackend().limpiarTodo();
            panelMapa.repaint();
        });
    }

    private void guardarGrafo() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fc.getSelectedFile();
                // aseguramos que tenga extension .dat
                if (!archivo.getName().endsWith(".dat")) {
                    archivo = new File(archivo.getAbsolutePath() + ".dat");
                }
                panelMapa.getBackend().guardarGrafo(archivo);
                JOptionPane.showMessageDialog(this, "Mapa guardado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        }
    }

    private void cargarGrafo() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                panelMapa.getBackend().cargarGrafo(fc.getSelectedFile());
                panelMapa.repaint();
                JOptionPane.showMessageDialog(this, "Mapa cargado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage());
            }
        }
    }

    private void cargarMapaInicial() {
        try {
            // cargamos la imagen
            ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/mapa.jpeg"));
            
            // aqui verificamos si cargo bien
            if (icono.getImageLoadStatus() == MediaTracker.ERRORED) {
                System.out.println("No se pudo cargar la imagen mapa.jpeg");
            }
            panelMapa.setImagen(icono.getImage());
        } catch (Exception e) {
            System.out.println("Error cargando recurso de imagen");
        }
    }
}