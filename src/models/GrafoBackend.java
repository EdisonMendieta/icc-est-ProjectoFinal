package models;

import java.io.*;
import java.util.*;

public class GrafoBackend {

    // aqui guardamos todo el grafo
    private Map<Node, List<Node>> grafo;
    
    private Node nodoInicio = null;
    private Node nodoFin = null;
    
    // listas para guardar el resultado del camino
    private List<Node> rutaResultado;
    private List<Node> nodosVisitadosOrden; 

    public GrafoBackend() {
        this.grafo = new HashMap<>();
        this.rutaResultado = new ArrayList<>();
        this.nodosVisitadosOrden = new ArrayList<>();
    }

    // metodos para manejar nodos
    public void agregarNodo(int x, int y) {
        grafo.put(new Node(x, y), new ArrayList<>());
    }

    public void conectarNodos(Node origen, Node destino, boolean bidireccional) {
        if (origen == null || destino == null || origen.equals(destino)) return;
        
        // conectamos origen -> destino
        if (!grafo.get(origen).contains(destino)) {
            grafo.get(origen).add(destino);
        }
        
        // si es doble via, tambien destino -> origen
        if (bidireccional) {
            if (!grafo.get(destino).contains(origen)) {
                grafo.get(destino).add(origen);
            }
        }
    }

    public void eliminarNodo(Node n) {
        if (n == null) return;
        grafo.remove(n);
        // borramos las conexiones que apuntaban a este nodo
        for (List<Node> vecinos : grafo.values()) {
            vecinos.remove(n);
        }
        if (n.equals(nodoInicio)) nodoInicio = null;
        if (n.equals(nodoFin)) nodoFin = null;
        limpiarResultados();
    }

    public Node buscarNodoCercano(int x, int y) {
        int radio = 15;
        // buscamos si hicimos click cerca de algun nodo existente
        for (Node n : grafo.keySet()) {
            double dist = Math.sqrt(Math.pow(x - n.getEjeX(), 2) + Math.pow(y - n.getEjeY(), 2));
            if (dist <= radio) return n;
        }
        return null;
    }

    // algoritmos de busqueda
    public boolean ejecutarBFS() {
        limpiarResultados();
        if (nodoInicio == null || nodoFin == null) return false;

        Queue<Node> cola = new LinkedList<>();
        Map<Node, Node> padres = new HashMap<>();
        Set<Node> visitados = new HashSet<>();

        cola.add(nodoInicio);
        visitados.add(nodoInicio);
        padres.put(nodoInicio, null);
        nodosVisitadosOrden.add(nodoInicio);

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
                    nodosVisitadosOrden.add(vecino); 
                    padres.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        if (encontrado) reconstruirRuta(padres);
        return encontrado;
    }

    public boolean ejecutarDFS() {
        limpiarResultados();
        if (nodoInicio == null || nodoFin == null) return false;

        Stack<Node> pila = new Stack<>();
        Map<Node, Node> padres = new HashMap<>();
        Set<Node> visitados = new HashSet<>();

        pila.push(nodoInicio);
        
        boolean encontrado = false;

        while (!pila.isEmpty()) {
            Node actual = pila.pop();

            if (!visitados.contains(actual)) {
                visitados.add(actual);
                nodosVisitadosOrden.add(actual); 

                if (actual.equals(nodoFin)) {
                    encontrado = true;
                    break;
                }

                for (Node vecino : grafo.get(actual)) {
                    if (!visitados.contains(vecino)) {
                        padres.putIfAbsent(vecino, actual); 
                        pila.push(vecino);
                    }
                }
            }
        }

        if (encontrado) reconstruirRuta(padres);
        return encontrado;
    }

    private void reconstruirRuta(Map<Node, Node> padres) {
        Node actual = nodoFin;
        while (actual != null) {
            rutaResultado.add(0, actual);
            actual = padres.get(actual);
        }
    }

    public void limpiarResultados() {
        rutaResultado.clear();
        nodosVisitadosOrden.clear();
    }

    // guardar y cargar archivos
    public void guardarGrafo(File archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(grafo);
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarGrafo(File archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            this.grafo = (Map<Node, List<Node>>) ois.readObject();
            limpiarResultados();
            nodoInicio = null;
            nodoFin = null;
        }
    }
    
    public void limpiarTodo() {
        grafo.clear();
        limpiarResultados();
        nodoInicio = null;
        nodoFin = null;
    }

    // getters y setters necesarios
    public void setNodoInicio(Node n) { this.nodoInicio = n; limpiarResultados(); }
    public void setNodoFin(Node n) { this.nodoFin = n; limpiarResultados(); }
    public Node getNodoInicio() { return nodoInicio; }
    public Node getNodoFin() { return nodoFin; }
    public Map<Node, List<Node>> getGrafo() { return grafo; }
    public List<Node> getRutaResultado() { return rutaResultado; }
    public List<Node> getNodosVisitados() { return nodosVisitadosOrden; }
}
