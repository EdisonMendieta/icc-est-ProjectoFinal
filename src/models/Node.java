package models;

public class Node {

    //este nodo tendra las cordenadas X y Y

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

    
    
}
