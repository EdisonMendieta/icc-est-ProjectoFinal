package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelMapa extends JPanel {

    private Image imagen;
    private Map<Node, List<Integer>> nodos;
    private boolean crearNodo;
    private boolean EliminarNodo;

    // Se instancia con un layout que permite superposición de paneles
    public PanelMapa() {
        setLayout(new OverlayLayout(this)); 
        nodos = new HashMap<>();


        //Cuando haga click y esta activado el modo 1 se creara un nodo 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //nos manda el nodo que este a <=8 de donde clickeamos
                Node nodoClickeado = distanciaNodo(e.getX(), e.getY());

                //si crearNodo esta activo y no encuentra nodos cercanos
                if (crearNodo && nodoClickeado == null) {
                    nodos.put(new Node(e.getX(), e.getY()), new ArrayList<>());
                    repaint();
                }

                //Si eliminar nodos esta activo y encuentra un nodo cerca elimina este nodo cercano
                if (EliminarNodo && nodoClickeado != null) {
                    nodos.remove(nodoClickeado);
                    repaint();
                }
            }
        });
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

        //esto dibuja los nodos tomando en cuenta la posicion del raton
        g.setColor(Color.RED);
        for (Node n : nodos.keySet()) {
            g.fillOval(n.getEjeX() - 5, n.getEjeY() - 5, 15, 15);
        }

    }

    public void setModoCrearNodo(boolean activo) {
        this.crearNodo = activo;
    }

    

    //metodo para calcular distacia entre nodos 
    private Node distanciaNodo(int x, int y) {
    for (Node n : nodos.keySet()) {
        int dx = x - n.getEjeX();
        int dy = y - n.getEjeY();

        //si la distancia cumple con nuestros terminos enviamos el nodo cercano
        double distancia = Math.sqrt(dx*dx + dy*dy);
        if ( distancia <= 8) {
            return n;
        }
    }
    return null;
    }

    public void setEliminarNodo(boolean eliminarNodo) {
        this.EliminarNodo = eliminarNodo;
    }

}
