public class Coordenada {
    private int x;
    private int y;
    
    
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[x=" + String.format("%4s", x) + ", y=" + String.format("%4s", y) + "]";
    }
    
}
