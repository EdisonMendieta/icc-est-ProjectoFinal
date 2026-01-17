package view;

import javax.swing.*;
import models.PanelControles;
import models.PanelMapa;

public class InterfazMapa extends JFrame {

    private PanelMapa panelMapa;
    private PanelControles panelControl;

    public InterfazMapa() {

        setTitle("Mapa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        //Creamos los paneles
        panelMapa = new PanelMapa();
        panelControl = new PanelControles();

        // Accion de boton crear
        panelControl.getBtnCrear().addActionListener(e -> {
            panelMapa.setModoCrearNodo(true);
        });
        
        //Accion de boton eliminar
        panelControl.getBtneliminar().addActionListener(e ->{
            panelMapa.setEliminarNodo(true);
        });

        // Acción Modo 2
        panelControl.getBtnModo2().addActionListener(e -> {
            panelMapa.setModoCrearNodo(false);
        });
                
        panelMapa.add(panelControl);
        add(panelMapa);


        cargarMapaInicial();
    }

    private void cargarMapaInicial() {
        ImageIcon icono = new ImageIcon(
            getClass().getResource("/recursos/mapa.png")
        );
        panelMapa.setImagen(icono.getImage());
    }

}

