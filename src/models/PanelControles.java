package models;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelControles extends JPanel {

    private JButton btnModo1;
    private JButton btnModo2;

    public PanelControles() {

        //hacemos el panel transparente
        setOpaque(false);

        //los botones se alinea a la izquierda
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //construimos los botones
        btnModo1 = new JButton("Modo 1");
        btnModo2 = new JButton("Modo 2");

        //los agregamos a el panel de controles
        add(btnModo1);
        add(btnModo2);
    }

    public JButton getBtnModo1() {
        return btnModo1;
    }

    public void setBtnModo1(JButton btnModo1) {
        this.btnModo1 = btnModo1;
    }

    public JButton getBtnModo2() {
        return btnModo2;
    }

    public void setBtnModo2(JButton btnModo2) {
        this.btnModo2 = btnModo2;
    }
    
    
}
