package view;

import javax.swing.*;

import models.PanelControles;
import models.PanelMapa;

import java.awt.*;

public class InterfazMapa extends JFrame {

    private PanelMapa panelMapa;
    private PanelControles panelControl;

    public InterfazMapa() {

        setTitle("Mapa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        //Creamos los paneles
        panelMapa = new PanelMapa();
        panelControl = new PanelControles();
        
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

