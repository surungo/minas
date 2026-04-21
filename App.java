import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    public static ArrayList<String> lerArquivo(String caminhoArquivo) {
        ArrayList<String> linhas = new ArrayList<>();

        // try-with-resources garante que o arquivo será fechado automaticamente
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return linhas;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Mapa mapa = new Mapa(0, 0, 0);

        System.out.println("Digite o nome do arquivo: (ex caso1)");
        // String texto = in.nextLine();
        // ArrayList<String> linhas = lerArquivo(texto + ".txt");
        ArrayList<String> linhas = lerArquivo("caso01.txt");
        in.close();

        mapa.setW(Integer.parseInt(linhas.get(0).split(" ")[0]));
        mapa.setH(Integer.parseInt(linhas.get(0).split(" ")[1]));
        mapa.setM(Integer.parseInt(linhas.get(0).split(" ")[2]));

        for (int i = 1; i < linhas.size(); i++) {
            Coordenada coordenada = new Coordenada(Integer.parseInt(linhas.get(i).split(" ")[0]),
                    Integer.parseInt(linhas.get(i).split(" ")[1]));
            Mina mina = new Mina(coordenada);
            mapa.getMinas().add(mina);
        }

        Poligono poligonoAnterior = new Poligono(new Coordenada(0, 0), new Coordenada(mapa.getW(), 0),
                new Coordenada(0, mapa.getH()), new Coordenada(mapa.getW(), mapa.getH()));
        poligonoAnterior.setColisaoInferior(new Coordenada(mapa.getW(), 0));
        poligonoAnterior.setColisaoDireita(new Coordenada(mapa.getW(), 0));

        Poligono poligono = criaPoligonos(mapa, poligonoAnterior);

        poligono = criaPoligonos(mapa, poligono);
        poligono = criaPoligonos(mapa, poligono);

        // 3º passo: criar DFS
        // 4º passo: criar logica de entcontrar a maior area
    }

    public static Poligono criaPoligonos(Mapa mapa, Poligono poligonoAnterior) {
        Poligono poligono = new Poligono(
                poligonoAnterior.getCoordenadaNW(),
                poligonoAnterior.getCoordenadaNE(),
                poligonoAnterior.getCoordenadaSW(),
                poligonoAnterior.getCoordenadaSE());

        localizarCoordenadaNW(mapa, poligono, poligonoAnterior); // coordenada NW do poligono anterior até que termine
                                                                 // as possibilidades

        localizarYdaCoordenadaNE(mapa, poligono, poligonoAnterior);

        localizarCoordenadaSW(mapa, poligono, poligonoAnterior);

        poligono.setCoordenadaSE(new Coordenada(poligono.getCoordenadaNE().getX(), poligono.getCoordenadaSW().getY()));
        mapa.getPoligonos().add(poligono);
        System.out.println("Poligono criado: NW(" + poligono.getCoordenadaNW().getX() + ","
                + poligono.getCoordenadaNW().getY() + ") " +
                "NE(" + poligono.getCoordenadaNE().getX() + "," + poligono.getCoordenadaNE().getY() + ") " +
                "SW(" + poligono.getCoordenadaSW().getX() + "," + poligono.getCoordenadaSW().getY() + ") " +
                "SE(" + poligono.getCoordenadaSE().getX() + "," + poligono.getCoordenadaSE().getY() + ")");
        return poligono;

    }

    public static void localizarCoordenadaNW(Mapa mapa, Poligono poligono, Poligono poligonoAnterior) {
        poligono.setCoordenadaNW(
                new Coordenada(poligonoAnterior.getCoordenadaNW().getX(), poligonoAnterior.getCoordenadaNW().getY())); 
    }

    public static void localizarYdaCoordenadaNE(Mapa mapa, Poligono poligono, Poligono poligonoAnterior) {
        poligono.setColisaoDireita(new Coordenada(poligonoAnterior.getColisaoInferior().getX(), poligonoAnterior.getColisaoInferior().getY()));
        poligono.setCoordenadaNE(new Coordenada(poligonoAnterior.getColisaoInferior().getX(), poligono.getCoordenadaNW().getY()));
    }

    public static void localizarYdaCoordenadaNEOLD(Mapa mapa, Poligono poligono, Poligono poligonoAnterior) {
        Coordenada coordenadaNW = poligono.getCoordenadaNW();
        Coordenada coordenadaColisaoDireita = poligonoAnterior.getColisaoDireita();
        Coordenada coordenadaColisaoInferior = poligonoAnterior.getColisaoInferior();
        Coordenada coordenadaNE = new Coordenada(poligonoAnterior.getColisaoInferior().getX(), poligonoAnterior.getColisaoInferior().getY());
        for (Mina mina : mapa.getMinas()) {
            Coordenada coordenadaMina = mina.getCoordenada();
            if (    coordenadaNW.getX() <= coordenadaMina.getX()
                    && coordenadaColisaoDireita.getX() >= coordenadaMina.getX()
                    
                    && coordenadaNW.getY() <= coordenadaMina.getY()
                    && coordenadaColisaoInferior.getY() >= coordenadaMina.getY()
                    
                    && coordenadaMina.getY() <= coordenadaNE.getY()) {
                coordenadaNE = coordenadaMina;
            }
        }
        poligono.setColisaoDireita(coordenadaNE);
        poligono.setCoordenadaNE(coordenadaNE);
    }

    public static void localizarCoordenadaSW(Mapa mapa, Poligono poligono, Poligono poligonoAnterior) {
        Coordenada coordenadaNW = poligono.getCoordenadaNW();
        Coordenada coordenadaSW = new Coordenada(0, mapa.getH());
        Coordenada coordenadaNE = poligono.getCoordenadaNE();

        for (Mina mina : mapa.getMinas()) {
            Coordenada coordenadaMina = mina.getCoordenada();
            if (coordenadaNW.getY() < coordenadaMina.getY() &&
                coordenadaSW.getY() > coordenadaMina.getY() &&

                coordenadaNW.getX() < coordenadaMina.getX() &&
                coordenadaNE.getX() > coordenadaMina.getX()) {
                coordenadaSW = coordenadaMina;
            }
        }
        poligono.setColisaoInferior(coordenadaSW);

        poligono.setCoordenadaSW(new Coordenada(coordenadaNW.getX(), coordenadaSW.getY()));
    }
}