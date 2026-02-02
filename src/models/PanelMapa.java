package models;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List; // Importar explícitamente para evitar conflictos
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelMapa extends JPanel {

    private Image imagen;
    private Map<Node, List<Node>> grafo; 
    
    // Nodos especiales para la búsqueda
    private Node nodoInicio = null;
    private Node nodoFin = null;
    
    // Para guardar la ruta encontrada y pintarla
    private List<Node> rutaResultado = new ArrayList<>();

    // Estados de la interfaz
    private int modoActual = 0; 
    // 0: Nada, 1: Crear, 2: Conectar, 3: Set Inicio, 4: Set Fin

    private Node nodoSeleccionado = null; // Auxiliar para conectar

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

        switch (modoActual) {
            case 1: // CREAR
                if (nodoCercano == null) {
                    grafo.put(new Node(x, y), new ArrayList<>());
                    repaint();
                }
                break;
                
            case 2: // CONECTAR
                if (nodoCercano != null) {
                    if (nodoSeleccionado == null) {
                        nodoSeleccionado = nodoCercano;
                    } else {
                        if (!nodoSeleccionado.equals(nodoCercano)) {
                            // Crear conexión bidireccional (calle de doble sentido)
                            if (!grafo.get(nodoSeleccionado).contains(nodoCercano)) {
                                grafo.get(nodoSeleccionado).add(nodoCercano);
                                grafo.get(nodoCercano).add(nodoSeleccionado);
                            }
                        }
                        nodoSeleccionado = null;
                    }
                    repaint();
                }
                break;

            case 3: // DEFINIR INICIO (A)
                if (nodoCercano != null) {
                    nodoInicio = nodoCercano;
                    rutaResultado.clear(); // Limpiar ruta anterior si cambia el inicio
                    repaint();
                }
                break;

            case 4: // DEFINIR FIN (B)
                if (nodoCercano != null) {
                    nodoFin = nodoCercano;
                    rutaResultado.clear();
                    repaint();
                }
                break;
        }
    }

    public void ejecutarBFS() {
        if (nodoInicio == null || nodoFin == null) {
            JOptionPane.showMessageDialog(this, "Define inicio y fin primero.");
            return;
        }

        // Estructuras para BFS
        Queue<Node> cola = new LinkedList<>();
        Map<Node, Node> padres = new HashMap<>(); // Para reconstruir el camino
        Set<Node> visitados = new HashSet<>();

        cola.add(nodoInicio);
        visitados.add(nodoInicio);
        padres.put(nodoInicio, null);

        boolean encontrado = false;

        while (!cola.isEmpty()) {
            Node actual = cola.poll();

            if (actual.equals(nodoFin)) {
                encontrado = true;
                break;
            }

            // Explorar vecinos
            for (Node vecino : grafo.get(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padres.put(vecino, actual); // Guardamos de dónde venimos
                    cola.add(vecino);
                }
            }
        }

        if (encontrado) {
            reconstruirRuta(padres);
        } else {
            JOptionPane.showMessageDialog(this, "No existe un camino entre estos nodos.");
        }
        repaint();
    }

    // Backtracking desde el destino hasta el inicio usando el mapa de padres
    private void reconstruirRuta(Map<Node, Node> padres) {
        rutaResultado.clear();
        Node actual = nodoFin;
        while (actual != null) {
            rutaResultado.add(0, actual); // Insertar al inicio
            actual = padres.get(actual);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));

        // DIBUJAR CONEXIONES (ARISTAS)
        g2.setColor(new Color(255, 255, 255, 150));
        for (Map.Entry<Node, List<Node>> entry : grafo.entrySet()) {
            Node origen = entry.getKey();
            for (Node destino : entry.getValue()) {
                g2.drawLine(origen.getEjeX(), origen.getEjeY(), destino.getEjeX(), destino.getEjeY());
            }
        }

        // DIBUJAR RUTA ENCONTRADA (Si existe)
        if (!rutaResultado.isEmpty()) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(4)); // Línea más gruesa
            for (int i = 0; i < rutaResultado.size() - 1; i++) {
                Node n1 = rutaResultado.get(i);
                Node n2 = rutaResultado.get(i + 1);
                g2.drawLine(n1.getEjeX(), n1.getEjeY(), n2.getEjeX(), n2.getEjeY());
            }
        }

        // DIBUJAR NODOS
        int radio = 16;
        for (Node n : grafo.keySet()) {
            // Colores según estado
            if (n.equals(nodoInicio)) g2.setColor(Color.GREEN);       // Inicio
            else if (n.equals(nodoFin)) g2.setColor(Color.MAGENTA);   // Fin
            else if (n.equals(nodoSeleccionado)) g2.setColor(Color.CYAN);
            else g2.setColor(Color.RED);

            g2.fillOval(n.getEjeX() - radio/2, n.getEjeY() - radio/2, radio, radio);
            g2.setColor(Color.WHITE);
            g2.drawOval(n.getEjeX() - radio/2, n.getEjeY() - radio/2, radio, radio);
        }
    }

    private Node buscarNodoCercano(int x, int y) {
        for (Node n : grafo.keySet()) {
            double dist = Math.sqrt(Math.pow(x - n.getEjeX(), 2) + Math.pow(y - n.getEjeY(), 2));
            if (dist <= 15) return n;
        }
        return null;
    }

    public void setImagen(Image imagen) { this.imagen = imagen; repaint(); }
    
    // Setters simples para cambiar modos
    public void setModoCrear() { this.modoActual = 1; limpiarSeleccion(); }
    public void setModoConectar() { this.modoActual = 2; limpiarSeleccion(); }
    public void setModoInicio() { this.modoActual = 3; limpiarSeleccion(); }
    public void setModoFin() { this.modoActual = 4; limpiarSeleccion(); }
    
    private void limpiarSeleccion() { this.nodoSeleccionado = null; }
}