package models;

import java.util.*;

public class GrafoBackend {

    // ESTRUCTURAS DE DATOS (EL MODELO)
    private Map<Node, List<Node>> grafo;
    private Node nodoInicio = null;
    private Node nodoFin = null;
    private List<Node> rutaResultado;

    public GrafoBackend() {
        this.grafo = new HashMap<>();
        this.rutaResultado = new ArrayList<>();
    }

    // --- LOGICA DE GESTION DEL GRAFO ---

    public void agregarNodo(int x, int y) {
        grafo.put(new Node(x, y), new ArrayList<>());
    }

    public void conectarNodos(Node origen, Node destino) {
        if (origen == null || destino == null) return;
        
        // Evitar duplicados y autoconexiones
        if (!origen.equals(destino)) {
            if (!grafo.get(origen).contains(destino)) {
                grafo.get(origen).add(destino);
            }
            if (!grafo.get(destino).contains(origen)) {
                grafo.get(destino).add(origen);
            }
        }
    }

    public void eliminarNodo(Node n) {
        if (n == null) return;
        grafo.remove(n);
        // Eliminar referencias en otros nodos
        for (List<Node> vecinos : grafo.values()) {
            vecinos.remove(n);
        }
        // Limpiar inicio/fin si eran este nodo
        if (n.equals(nodoInicio)) nodoInicio = null;
        if (n.equals(nodoFin)) nodoFin = null;
        limpiarRuta();
    }

    public Node buscarNodoCercano(int x, int y) {
        int radioDeteccion = 15;
        for (Node n : grafo.keySet()) {
            double dist = Math.sqrt(Math.pow(x - n.getEjeX(), 2) + Math.pow(y - n.getEjeY(), 2));
            if (dist <= radioDeteccion) {
                return n;
            }
            
        }
        return null;
    }

    // --- LOGICA DEL ALGORITMO BFS ---

    public boolean ejecutarBFS() {
        limpiarRuta();
        
        if (nodoInicio == null || nodoFin == null) return false;

        Queue<Node> cola = new LinkedList<>();
        Map<Node, Node> padres = new HashMap<>();
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

            for (Node vecino : grafo.get(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padres.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        if (encontrado) {
            reconstruirRuta(padres);
        }
        
        return encontrado;
    }

    private void reconstruirRuta(Map<Node, Node> padres) {
        Node actual = nodoFin;
        while (actual != null) {
            rutaResultado.add(0, actual);
            actual = padres.get(actual);
        }
    }

    public void limpiarRuta() {
        rutaResultado.clear();
    }

    // --- GETTERS Y SETTERS PARA LA VISTA ---

    public void setNodoInicio(Node n) { 
        this.nodoInicio = n; 
        limpiarRuta(); 
    }
    
    public void setNodoFin(Node n) { 
        this.nodoFin = n; 
        limpiarRuta(); 
    }

    public Node getNodoInicio() { return nodoInicio; }
    public Node getNodoFin() { return nodoFin; }
    public Map<Node, List<Node>> getGrafo() { return grafo; }
    public List<Node> getRutaResultado() { return rutaResultado; }
}
