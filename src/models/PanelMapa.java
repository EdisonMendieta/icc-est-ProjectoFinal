package models;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class PanelMapa extends JPanel {

    private Image imagen;

    // Se instancia con un layout que permite superposición de paneles
    public PanelMapa() {
        setLayout(new OverlayLayout(this)); 
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
