package models;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelMapa extends JPanel {

    private Image imagen;
    // Mapa que guarda: Nodo -> Lista de Nodos a los que está conectado
    private Map<Node, List<Node>> grafo; 
    
    // Estados
    private boolean modoCrear = false;
    private boolean modoEliminar = false;
    private boolean modoConectar = false;

    // Auxiliar para guardar el primer nodo seleccionado al conectar
    private Node nodoSeleccionado = null; 

    public PanelMapa() {
        setLayout(null); 
        grafo = new HashMap<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });
    }

    private void manejarClic(int x, int y) {
        Node nodoCercano = buscarNodoCercano(x, y);

        // --- CREAR ---
        if (modoCrear && nodoCercano == null) {
            grafo.put(new Node(x, y), new ArrayList<>());
            refrescarVentana();
            return;
        }

        // --- ELIMINAR ---
        if (modoEliminar && nodoCercano != null) {
            grafo.remove(nodoCercano);
            // También borrar referencias en otros nodos
            for (List<Node> conexiones : grafo.values()) {
                conexiones.remove(nodoCercano);
            }
            nodoSeleccionado = null;
            refrescarVentana();
            return;
        }

        // --- CONECTAR ---
        if (modoConectar && nodoCercano != null) {
            if (nodoSeleccionado == null) {
                // Primer clic: Seleccionar origen
                nodoSeleccionado = nodoCercano;
            } else {
                // Segundo clic: Conectar destino
                if (!nodoSeleccionado.equals(nodoCercano)) {
                    // Evitar duplicados
                    if (!grafo.get(nodoSeleccionado).contains(nodoCercano)) {
                        grafo.get(nodoSeleccionado).add(nodoCercano);
                        // Si quieres conexión doble, descomenta:
                        // grafo.get(nodoCercano).add(nodoSeleccionado);
                    }
                }
                nodoSeleccionado = null; // Reiniciar para la siguiente conexión
            }
            refrescarVentana();
        }
    }

    // Este metodo fuerza a repintar TODA la ventana para evitar bugs visuales
    private void refrescarVentana() {
        if (SwingUtilities.getWindowAncestor(this) != null) {
            SwingUtilities.getWindowAncestor(this).repaint();
        } else {
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 1. Imagen de fondo
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 2. Dibujar LINEAS (Conexiones)
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2)); // Grosor de línea

        for (Map.Entry<Node, List<Node>> entrada : grafo.entrySet()) {
            Node origen = entrada.getKey();
            for (Node destino : entrada.getValue()) {
                g2.drawLine(origen.getEjeX(), origen.getEjeY(), destino.getEjeX(), destino.getEjeY());
            }
        }

        // 3. Dibujar NODOS
        int radio = 20; 
        
        for (Node n : grafo.keySet()) {
            // Cambiar color si es el seleccionado actualmente
            if (n.equals(nodoSeleccionado)) {
                g2.setColor(Color.CYAN); // Seleccionado
            } else {
                g2.setColor(Color.RED); // Normal
            }

            // Dibujar circulo centrado en las coordenadas
            g2.fillOval(n.getEjeX() - (radio/2), n.getEjeY() - (radio/2), radio, radio);
            
            // Borde blanco al nodo
            g2.setColor(Color.WHITE);
            g2.drawOval(n.getEjeX() - (radio/2), n.getEjeY() - (radio/2), radio, radio);
        }
    }

    private Node buscarNodoCercano(int x, int y) {
        int radioDeteccion = 15;
        for (Node n : grafo.keySet()) {
            double distancia = Math.sqrt(Math.pow(x - n.getEjeX(), 2) + Math.pow(y - n.getEjeY(), 2));
            if (distancia <= radioDeteccion) {
                return n;
            }
        }
        return null;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }

    // Setters que aseguran que solo haya un modo activo a la vez
    public void setModoCrear(boolean v) { 
        this.modoCrear = v; 
        if(v) { modoEliminar = false; modoConectar = false; nodoSeleccionado = null; }
    }
    public void setModoEliminar(boolean v) { 
        this.modoEliminar = v; 
        if(v) { modoCrear = false; modoConectar = false; nodoSeleccionado = null; }
    }
    public void setModoConectar(boolean v) { 
        this.modoConectar = v; 
        if(v) { modoCrear = false; modoEliminar = false; nodoSeleccionado = null; }
    }
}