package models;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelControles extends JPanel {

    private JButton btnModo1;
    private JButton btnModo2;
    private JButton btnCrear;
    private JButton btnEliminar;
    private JButton btnConectar;
    private JButton btnDesconectar;

    public PanelControles() {

        //hacemos el panel transparente
        setOpaque(false);

        //los botones se alinea a la izquierda
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //construimos los botones
        btnModo1 = new JButton("Modo 1");
        btnModo2 = new JButton("Modo 2");
        btnConectar = new JButton("Conectar Nodos");
        btnCrear = new JButton("Crear Nodo");
        btnDesconectar = new JButton("Desconectar Nodo");
        btnEliminar = new JButton("Eliminar Nodo");

        mostrarBotones(false);

        btnModo1.addActionListener(e ->{
            mostrarBotones(true);
        });

        //los agregamos botones a el panel de controles
        add(btnModo1);
        add(btnModo2);
        add(btnCrear);
        add(btnEliminar);
        add(btnConectar);
        add(btnDesconectar);
    }

        
    public void mostrarBotones(boolean v){
        btnConectar.setVisible(v);
        btnCrear.setVisible(v);
        btnDesconectar.setVisible(v);
        btnEliminar.setVisible(v);

        revalidate(); 
        repaint();

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

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public void setBtnCrear(JButton btnCrear) {
        this.btnCrear = btnCrear;
    }

    public JButton getBtneliminar() {
        return btnEliminar;
    }

    public void setBtneliminar(JButton btneliminar) {
        this.btnEliminar = btneliminar;
    }

    public JButton getBtnConectar() {
        return btnConectar;
    }

    public void setBtnConectar(JButton btnConectar) {
        this.btnConectar = btnConectar;
    }

    public JButton getBtnDesconectar() {
        return btnDesconectar;
    }

    public void setBtnDesconectar(JButton btnDesconectar) {
        this.btnDesconectar = btnDesconectar;
    }
    
}
