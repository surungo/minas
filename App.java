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

        // IDEIA PRA FAZER O V3 V4 DANE-SE
        //
        // AO INVES DE VER TODAS AS LINHAS DO MAPA
        // VER AS LINHAS ONDE AS BOMBAS ESTAO
        // E A PARTIR DISSO CRIAR OS POLIGONOS!!!!!!!!!




        for (Mina mina : mapa.getMinas()) {
            Poligono poligonoNorte = criarPoligonoNorte(mina, mapa);
            Poligono poligonoSul = criarPoligonoSul(mina, mapa);
            Poligono poligonoLeste = criarPoligonoLeste(mina, mapa);
            Poligono poligonoOeste = criarPoligonoOeste(mina, mapa);
        }


    }
    
    public static Poligono criarPoligonoNorte(Mina mina, Mapa mapa) {
        Mina colisaoEsquerda = new Mina(new Coordenada(0,0));
        Mina colisaoSuperior = new Mina(new Coordenada(0,0));
        for (Mina mina2 : mapa.getMinas()) {
            if(mina2.getCoordenada().getX() < mina.getCoordenada().getX()
            && mina2.getCoordenada().getY() < mina.getCoordenada().getY()

            && colisaoEsquerda.getCoordenada().getX() < mina2.getCoordenada().getX()

            ) {
                colisaoEsquerda.setMina(mina2);
            }
        }
        for (Mina mina2 : mapa.getMinas()) {
            if(mina2.getCoordenada().getY() < mina.getCoordenada(). getY()
            ){
                colisaoSuperior.setMina(mina2);
            }
        }

       

        Coordenada coordenadaNW = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY() - 1);

        Coordenada coordenadaNE = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY() - 1);
        Coordenada coordenadaSW = new Coordenada(mina.getCoordenada().getX() - 1, mina.getCoordenada().getY());
        Coordenada coordenadaSE = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY());
        Poligono poligonoNorte = new Poligono(coordenadaNW, coordenadaNE, coordenadaSW, coordenadaSE);
        return poligonoNorte;
       
    }

    public static Poligono criarPoligonoSul(Mina mina, Mapa mapa) {
        Coordenada coordenadaNW = new Coordenada(mina.getCoordenada().getX() - 1, mina.getCoordenada().getY());
        Coordenada coordenadaNE = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY());
        Coordenada coordenadaSW = new Coordenada(mina.getCoordenada().getX() - 1, mina.getCoordenada().getY() + 1);
        Coordenada coordenadaSE = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY() + 1);
        Poligono poligonoSul = new Poligono(coordenadaNW, coordenadaNE, coordenadaSW, coordenadaSE);
        return poligonoSul;
    }

    public static Poligono criarPoligonoLeste(Mina mina, Mapa mapa) {
        Coordenada coordenadaNW = new Coordenada(mina.getCoordenada().getX(), mina.getCoordenada().getY() - 1);
        Coordenada coordenadaNE = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY() - 1);
        Coordenada coordenadaSW = new Coordenada(mina.getCoordenada().getX(), mina.getCoordenada().getY() + 1);
        Coordenada coordenadaSE = new Coordenada(mina.getCoordenada().getX() + 1, mina.getCoordenada().getY() + 1);
        Poligono poligonoLeste = new Poligono(coordenadaNW, coordenadaNE, coordenadaSW, coordenadaSE);
        return poligonoLeste;
    }

    public static Poligono criarPoligonoOeste(Mina mina, Mapa mapa) {
        Coordenada coordenadaNW = new Coordenada(mina.getCoordenada().getX() - 1, mina.getCoordenada().getY() - 1);
        Coordenada coordenadaNE = new Coordenada(mina.getCoordenada().getX(), mina.getCoordenada().getY() - 1);
        Coordenada coordenadaSW = new Coordenada(mina.getCoordenada().getX() - 1, mina.getCoordenada().getY() + 1);
        Coordenada coordenadaSE = new Coordenada(mina.getCoordenada().getX(), mina.getCoordenada().getY() + 1);
        Poligono poligonoOeste = new Poligono(coordenadaNW, coordenadaNE, coordenadaSW, coordenadaSE);
        return poligonoOeste;
    }
}