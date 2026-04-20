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

        Poligono poligono = new Poligono(new Coordenada(0, 0), new Coordenada(mapa.getW(), 0), new Coordenada(0, mapa.getH()), new Coordenada(mapa.getW(), mapa.getH()));
        
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
                poligonoAnterior.getCoordenadaSE()
        );

        localizarCoordenadaNW(mapa, poligono, poligonoAnterior);  // coordenada NW do poligono anterior até que termine as possibilidades

        localizarCoordenadaNE(mapa, poligono, poligonoAnterior);
        
        
        Coordenada coordenadaSW = localizarCoordenadaSW(mapa, poligono);

        Coordenada coordenadaSE = new Coordenada(coordenadaNE.getX(), coordenadaSW.getY());


        poligono = new Poligono(
                coordenadaNW,
                coordenadaNE,
                coordenadaSW,
                coordenadaSE);
        mapa.getPoligonos().add(poligono);
        System.out.println("Poligono criado: NW(" + poligono.getCoordenadaNW().getX() + "," + poligono.getCoordenadaNW().getY() + ") " +
                "NE(" + poligono.getCoordenadaNE().getX() + "," + poligono.getCoordenadaNE().getY() + ") " +
                "SW(" + poligono.getCoordenadaSW().getX() + "," + poligono.getCoordenadaSW().getY() + ") " +
                "SE(" + poligono.getCoordenadaSE().getX() + "," + poligono.getCoordenadaSE().getY() + ")"
        );
        return poligono;

    }
    public static void localizarCoordenadaNW(Mapa mapa, Poligono poligono, Poligono poligonoAnterior) {
        poligono.setCoordenadaNW(new Coordenada(poligonoAnterior.getCoordenadaNW().getX(), poligonoAnterior.getCoordenadaNW().getY())); // funciona para caso 1
    }

    public static void localizarCoordenadaNE(Mapa mapa, Poligono poligono, Poligono poligonoAnterior) {
        Coordenada coordenadaSE = poligonoAnterior.getCoordenadaSE();
        Coordenada coordenadaNE = poligonoAnterior.getCoordenadaNE();   
        for (Mina mina : mapa.getMinas()) {
            Coordenada coordenadaMina = mina.getCoordenada();
            if (coordenadaNE.getX() < coordenadaMina.getX() &&
             coordenadaSE.getX() > coordenadaMina.getX() &&
             coordenadaNE.getY() == coordenadaMina.getY()
            ) {
                coordenadaNE = coordenadaMina;
            }
        }
        poligonoAnterior.setColisaoX(coordenadaNE);
        return coordenadaNE;
    }

    public static Coordenada localizarCoordenadaSW(Mapa mapa, Poligono poligonoAnterior) {
        Coordenada coordenadaNW = poligonoAnterior.getCoordenadaNW();
        Coordenada coordenadaSW = poligonoAnterior.getCoordenadaSW();
        Coordenada coordenadaNE = poligonoAnterior.getCoordenadaNE();

        for (Mina mina : mapa.getMinas()) {
            Coordenada coordenadaMina = mina.getCoordenada();
            if (coordenadaNW.getY() < coordenadaMina.getY() &&
             coordenadaSW.getY() > coordenadaMina.getY() &&
             coordenadaNW.getX() < coordenadaMina.getX() &&
                coordenadaNE.getX() > coordenadaMina.getX()
            ) {
                coordenadaSW = coordenadaMina;
            }
        }
        poligonoAnterior
        coordenadaSW = new Coordenada(coordenadaNW.getX(), coordenadaSW.getY());
        return coordenadaSW;
    }
}