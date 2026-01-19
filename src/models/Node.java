package models;

import java.util.Objects;

public class Node {

    private int ejeX;
    private int ejeY;

    public Node(int ejeX, int ejeY) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
    }

    public int getEjeX() {
        return ejeX;
    }

    public void setEjeX(int ejeX) {
        this.ejeX = ejeX;
    }

    public int getEjeY() {
        return ejeY;
    }

    public void setEjeY(int ejeY) {
        this.ejeY = ejeY;
    }

    // Estos metodos son vitales para que el sistema sepa identificar 
    // si un nodo es igual a otro al momento de conectar
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
}