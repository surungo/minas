public class Mina {
    private Coordenada coordenada;

    public Mina(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    @Override
    public String toString() {
        return "Mina [coordenada=" + coordenada + "]";
    }

    public void setMina(Mina mina) {
        this.coordenada = new Coordenada(mina.getCoordenada().getX(), mina.getCoordenada().getY());
    }
}
