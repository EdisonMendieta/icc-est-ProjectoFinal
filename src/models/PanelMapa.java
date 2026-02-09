package models;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelMapa extends JPanel {

    private Image imagen;
    
    // INSTANCIA DEL BACKEND (LA LÓGICA)
    private GrafoBackend backend;

    // Estados de la interfaz (Visual)
    private int modoActual = 0; 
    // 0: Nada, 1: Crear, 2: Conectar, 3: Set Inicio, 4: Set Fin, 5: Eliminar

    private Node nodoSeleccionado = null; // Auxiliar visual para conectar

    public PanelMapa() {
        setLayout(null); 
        // Inicializamos la lógica
        backend = new GrafoBackend();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });
    }

    // Manejamos el clic, pero la lógica fuerte la hace el backend
    private void manejarClic(int x, int y) {
        Node nodoCercano = backend.buscarNodoCercano(x, y);

        switch (modoActual) {
            case 1: // CREAR
                if (nodoCercano == null) {
                    backend.agregarNodo(x, y);
                    repaint();
                }
                break;
                
            case 2: // CONECTAR
                if (nodoCercano != null) {
                    if (nodoSeleccionado == null) {
                        nodoSeleccionado = nodoCercano; // Primer clic (visual)
                    } else {
                        // Segundo clic: Delegamos la conexión al backend
                        backend.conectarNodos(nodoSeleccionado, nodoCercano);
                        nodoSeleccionado = null;
                    }
                    repaint();
                }
                break;

            case 3: // DEFINIR INICIO (A)
                if (nodoCercano != null) {
                    backend.setNodoInicio(nodoCercano);
                    repaint();
                }
                break;

            case 4: // DEFINIR FIN (B)
                if (nodoCercano != null) {
                    backend.setNodoFin(nodoCercano);
                    repaint();
                }
                break;
                
            case 5: // ELIMINAR (Opcional si tienes el botón)
                if (nodoCercano != null) {
                    backend.eliminarNodo(nodoCercano);
                    nodoSeleccionado = null;
                    repaint();
                }
                break;
        }
    }

    public void ejecutarBFS() {
        boolean exito = backend.ejecutarBFS();
        
        if (!exito) {
            if (backend.getNodoInicio() == null || backend.getNodoFin() == null) {
                JOptionPane.showMessageDialog(this, "Define inicio y fin primero.");
            } else {
                JOptionPane.showMessageDialog(this, "No existe un camino entre estos nodos.");
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));

        Map<Node, List<Node>> grafo = backend.getGrafo();
        List<Node> ruta = backend.getRutaResultado();
        Node inicio = backend.getNodoInicio();
        Node fin = backend.getNodoFin();

        g2.setColor(new Color(255, 255, 255, 150));
        for (Map.Entry<Node, List<Node>> entry : grafo.entrySet()) {
            Node origen = entry.getKey();
            for (Node destino : entry.getValue()) {
                g2.drawLine(origen.getEjeX(), origen.getEjeY(), destino.getEjeX(), destino.getEjeY());
            }
        }

        if (!ruta.isEmpty()) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(4));
            for (int i = 0; i < ruta.size() - 1; i++) {
                Node n1 = ruta.get(i);
                Node n2 = ruta.get(i + 1);
                g2.drawLine(n1.getEjeX(), n1.getEjeY(), n2.getEjeX(), n2.getEjeY());
            }
        }

        int radio = 16;
        for (Node n : grafo.keySet()) {
            if (n.equals(inicio)) g2.setColor(Color.GREEN);
            else if (n.equals(fin)) g2.setColor(Color.MAGENTA);
            else if (n.equals(nodoSeleccionado)) g2.setColor(Color.CYAN);
            else g2.setColor(Color.RED);

            g2.fillOval(n.getEjeX() - radio/2, n.getEjeY() - radio/2, radio, radio);
            g2.setColor(Color.WHITE);
            g2.drawOval(n.getEjeX() - radio/2, n.getEjeY() - radio/2, radio, radio);
        }
    }

    public void setImagen(Image imagen) { this.imagen = imagen; repaint(); }

    public void setModoCrear() { this.modoActual = 1; limpiarSeleccion(); }
    public void setModoConectar() { this.modoActual = 2; limpiarSeleccion(); }
    public void setModoInicio() { this.modoActual = 3; limpiarSeleccion(); }
    public void setModoFin() { this.modoActual = 4; limpiarSeleccion(); }
    public void setModoEliminar() { this.modoActual = 5; limpiarSeleccion(); } // Si decides agregar eliminar
    
    private void limpiarSeleccion() { this.nodoSeleccionado = null; }
}