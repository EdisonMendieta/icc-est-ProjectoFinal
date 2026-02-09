package models;

import java.io.Serializable;
import java.util.Objects;

public class Node implements Serializable {
    
    // necesario para guardar el archivo sin errores
    private static final long serialVersionUID = 1L; 
    
    private int ejeX;
    private int ejeY;
    private String nombre; 

    public Node(int ejeX, int ejeY) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        // le ponemos un nombre automatico para identificarlo facil
        this.nombre = "N" + System.currentTimeMillis() % 1000; 
    }

    public int getEjeX() { return ejeX; }
    public void setEjeX(int ejeX) { this.ejeX = ejeX; }

    public int getEjeY() { return ejeY; }
    public void setEjeY(int ejeY) { this.ejeY = ejeY; }

    public String getNombre() { return nombre; }

    // metodos para comparar nodos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return ejeX == node.ejeX && ejeY == node.ejeY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ejeX, ejeY);
    }
    
    @Override
    public String toString() { return nombre; }
}