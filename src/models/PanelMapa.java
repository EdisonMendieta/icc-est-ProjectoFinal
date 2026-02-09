package models;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelMapa extends JPanel {

    private Image imagen;
    private GrafoBackend backend;

    // modos para saber que hace el click
    // 0:nada, 1:crear, 2:doble via, 3:una via, 4:inicio, 5:fin, 6:eliminar
    private int modoInteraccion = 0; 

    // opcion para ver la animacion
    private boolean modoExploracion = false; 

    private Node nodoSeleccionado = null;
    
    // variables para animar el recorrido
    private Timer timerAnimacion;
    private int pasoAnimacion = 0;

    public PanelMapa() {
        setLayout(null);
        backend = new GrafoBackend();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });
    }

    private void manejarClic(int x, int y) {
        // si se esta moviendo la animacion no dejamos hacer click
        if (timerAnimacion != null && timerAnimacion.isRunning()) return; 

        Node nodoCercano = backend.buscarNodoCercano(x, y);

        switch (modoInteraccion) {
            case 1: // crear nodo
                if (nodoCercano == null) {
                    backend.agregarNodo(x, y);
                    repaint();
                }
                break;
            case 2: // conectar doble
            case 3: // conectar simple
                if (nodoCercano != null) {
                    if (nodoSeleccionado == null) {
                        nodoSeleccionado = nodoCercano;
                    } else {
                        boolean bidireccional = (modoInteraccion == 2);
                        backend.conectarNodos(nodoSeleccionado, nodoCercano, bidireccional);
                        nodoSeleccionado = null;
                    }
                    repaint();
                }
                break;
            case 4: // poner inicio
                if (nodoCercano != null) { backend.setNodoInicio(nodoCercano); repaint(); }
                break;
            case 5: // poner fin
                if (nodoCercano != null) { backend.setNodoFin(nodoCercano); repaint(); }
                break;
            case 6: // eliminar
                if (nodoCercano != null) { backend.eliminarNodo(nodoCercano); nodoSeleccionado=null; repaint(); }
                break;
        }
    }

    public void ejecutarAlgoritmo(boolean esBFS) {
        boolean encontrado;
        if (esBFS) encontrado = backend.ejecutarBFS();
        else encontrado = backend.ejecutarDFS();

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "No hay camino entre A y B");
            repaint();
            return;
        }

        if (modoExploracion) {
            iniciarAnimacion();
        } else {
            repaint();
        }
    }

    private void iniciarAnimacion() {
        pasoAnimacion = 0;
        if (timerAnimacion != null) timerAnimacion.stop();
        
        // timer para ir pintando poco a poco
        timerAnimacion = new Timer(100, e -> {
            pasoAnimacion++;
            if (pasoAnimacion >= backend.getNodosVisitados().size() + backend.getRutaResultado().size()) {
                ((Timer)e.getSource()).stop();
            }
            repaint();
        });
        timerAnimacion.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        // activamos antialias para que se vea hd
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (imagen != null) {
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }

        Map<Node, List<Node>> grafo = backend.getGrafo();
        List<Node> ruta = backend.getRutaResultado();
        List<Node> visitados = backend.getNodosVisitados();

        // 1. dibujamos las conexiones con flechas
        g2.setStroke(new BasicStroke(2));
        g2.setColor(new Color(255, 255, 255, 180));
        
        for (Map.Entry<Node, List<Node>> entry : grafo.entrySet()) {
            Node origen = entry.getKey();
            for (Node destino : entry.getValue()) {
                dibujarFlecha(g2, origen, destino);
            }
        }

        // 2. dibujamos la animacion de exploracion (puntos azules)
        if (modoExploracion && timerAnimacion != null && timerAnimacion.isRunning()) {
            g2.setColor(new Color(100, 100, 255, 150)); 
            for (int i = 0; i < Math.min(pasoAnimacion, visitados.size()); i++) {
                Node n = visitados.get(i);
                g2.fillOval(n.getEjeX()-12, n.getEjeY()-12, 24, 24);
            }
        }

        // 3. dibujamos la ruta final (amarilla)
        boolean dibujarRuta = !modoExploracion || (modoExploracion && pasoAnimacion >= visitados.size());
        
        if (dibujarRuta && !ruta.isEmpty()) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(4));
            for (int i = 0; i < ruta.size() - 1; i++) {
                Node n1 = ruta.get(i);
                Node n2 = ruta.get(i + 1);
                g2.drawLine(n1.getEjeX(), n1.getEjeY(), n2.getEjeX(), n2.getEjeY());
            }
        }

        // 4. dibujamos los nodos (circulos)
        int radio = 16;
        for (Node n : grafo.keySet()) {
            if (n.equals(backend.getNodoInicio())) g2.setColor(Color.GREEN);
            else if (n.equals(backend.getNodoFin())) g2.setColor(Color.MAGENTA);
            else if (n.equals(nodoSeleccionado)) g2.setColor(Color.CYAN);
            else g2.setColor(Color.RED);

            g2.fillOval(n.getEjeX() - radio/2, n.getEjeY() - radio/2, radio, radio);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1));
            g2.drawOval(n.getEjeX() - radio/2, n.getEjeY() - radio/2, radio, radio);
        }
    }

    private void dibujarFlecha(Graphics2D g2, Node n1, Node n2) {
        int x1 = n1.getEjeX();
        int y1 = n1.getEjeY();
        int x2 = n2.getEjeX();
        int y2 = n2.getEjeY();
        
        g2.drawLine(x1, y1, x2, y2);
        
        // calculo matematico para rotar la flecha
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 6;
        
        int offsetX = (int) (x2 - 15 * Math.cos(angle));
        int offsetY = (int) (y2 - 15 * Math.sin(angle));

        AffineTransform tx = g2.getTransform();
        g2.translate(offsetX, offsetY);
        g2.rotate(angle - Math.PI / 2.0);
        g2.fillPolygon(new int[]{0, -arrowSize, arrowSize}, new int[]{0, -arrowSize, -arrowSize}, 3);
        g2.setTransform(tx);
    }

    public void setImagen(Image imagen) { this.imagen = imagen; repaint(); }
    public GrafoBackend getBackend() { return backend; }
    public void setModo(int modo) { this.modoInteraccion = modo; limpiarSeleccion(); }
    public void setModoExploracion(boolean activo) { this.modoExploracion = activo; }
    private void limpiarSeleccion() { this.nodoSeleccionado = null; }
}