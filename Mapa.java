import java.util.ArrayList;

public class Mapa {
    private int w;
    private int h;
    private int m;
    private ArrayList<Mina> minas;
    private ArrayList<Poligono> poligonos;

    public Mapa(int w, int h, int m) {
        this.w = w;
        this.h = h;
        this.m = m;
        this.poligonos = new ArrayList<>();
        this.minas = new ArrayList<>();
    }

    public ArrayList<Poligono> getPoligonos() {
        return poligonos;
    }

    public void setPoligonos(ArrayList<Poligono> poligonos) {
        this.poligonos = poligonos;
    }

    public ArrayList<Mina> getMinas() {
        return minas;
    }

    public void setMinas(ArrayList<Mina> minas) {
        this.minas = minas;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mapa: ").append(w).append("x").append(h).append(", Minas: ").append(m).append("\n");
        sb.append("Poligonos:\n");
        for (Poligono poligono : poligonos) {
            sb.append(poligono.toString()).append("\n");
        }
        return sb.toString();

    }

}