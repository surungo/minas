public class Poligono {
    private Coordenada coordenadaNW;
    private Coordenada coordenadaNE;
    private Coordenada coordenadaSW;
    private Coordenada coordenadaSE;

    public Poligono(Coordenada coordenadaNW, Coordenada coordenadaNE, Coordenada coordenadaSW, Coordenada coordenadaSE) {
        this.coordenadaNW = coordenadaNW;
        this.coordenadaNE = coordenadaNE;
        this.coordenadaSW = coordenadaSW;
        this.coordenadaSE = coordenadaSE;
    }

    public Coordenada getCoordenadaNW() {
        return coordenadaNW;
    }

    public Coordenada getCoordenadaNE() {
        return coordenadaNE;
    }

    public Coordenada getCoordenadaSW() {
        return coordenadaSW;
    }

    public Coordenada getCoordenadaSE() {
        return coordenadaSE;
    }

    public Coordenada getCoordenadaNWPlus() {
        return new Coordenada(coordenadaNW.getX() + 1, coordenadaNW.getY() + 1);
    }

    public Coordenada getCoordenadaNEPlus() {
        return new Coordenada(coordenadaNE.getX() + 1, coordenadaNE.getY() + 1);
    }

    public Coordenada getCoordenadaSWPlus() {
        return new Coordenada(coordenadaSW.getX() + 1, coordenadaSW.getY() + 1);
    }

    public Coordenada getCoordenadaSEPlus() {
        return new Coordenada(coordenadaSE.getX() + 1, coordenadaSE.getY() + 1);
    }

    public void setCoordenadaNW(Coordenada coordenadaNW) {
        this.coordenadaNW = coordenadaNW;
    }

    public void setCoordenadaNE(Coordenada coordenadaNE) {
        this.coordenadaNE = coordenadaNE;
    }

    public void setCoordenadaSW(Coordenada coordenadaSW) {
        this.coordenadaSW = coordenadaSW;
    }

    public void setCoordenadaSE(Coordenada coordenadaSE) {
        this.coordenadaSE = coordenadaSE;
    }
    
}
