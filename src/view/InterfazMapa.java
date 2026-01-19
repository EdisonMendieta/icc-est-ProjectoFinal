package view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import models.PanelControles;
import models.PanelMapa;

public class InterfazMapa extends JFrame {

    private PanelMapa panelMapa;
    private PanelControles panelControl;
    private JLayeredPane capas; // Usamos esto para manejar profundidades (Z-order)

    public InterfazMapa() {
        setTitle("Proyecto Mapa de Nodos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // 1. Inicializar el manejador de capas
        capas = new JLayeredPane();
        setContentPane(capas);

        // 2. Crear los paneles
        panelMapa = new PanelMapa();
        panelControl = new PanelControles();

        // 3. Añadir a las capas
        // DEFAULT_LAYER (Fondo): El mapa
        // PALETTE_LAYER (Arriba): Los controles
        capas.add(panelMapa, JLayeredPane.DEFAULT_LAYER);
        capas.add(panelControl, JLayeredPane.PALETTE_LAYER);

        // 4. Listener para redimensionar el mapa cuando cambie el tamaño de la ventana
        capas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // El mapa ocupa todo el fondo
                panelMapa.setBounds(0, 0, getWidth(), getHeight());
                
                // Los controles flotan arriba a la izquierda (x=20, y=20, ancho=180, alto=200)
                panelControl.setBounds(20, 20, 180, 200);
            }
        });

        // 5. Configurar acciones de botones
        configurarEventos();

        cargarMapaInicial();
    }

    private void configurarEventos() {
        panelControl.getBtnCrear().addActionListener(e -> {
            panelMapa.setModoCrear(true);
            // Feedback visual: Podrías cambiar el color del borde del panel, etc.
        });

        panelControl.getBtnEliminar().addActionListener(e -> {
            panelMapa.setModoEliminar(true);
        });

        panelControl.getBtnConectar().addActionListener(e -> {
            panelMapa.setModoConectar(true);
        });
    }

    private void cargarMapaInicial() {
        try {
            // Asegúrate de tener la imagen en src/recursos/mapa.png o ajusta la ruta
            ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/mapa.png"));
            panelMapa.setImagen(icono.getImage());
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }
}