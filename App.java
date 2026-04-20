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

        criaPoligonos(mapa, new Coordenada(0, 0));

        criaPoligonos(mapa, mapa.getPoligonos().get(0).getCoordenadaNE());
        criaPoligonos(mapa, mapa.getPoligonos().get(0).getCoordenadaSW());
        criaPoligonos(mapa, mapa.getPoligonos().get(0).getCoordenadaSE());

        // 3º passo: criar DFS
        // 4º passo: criar logica de entcontrar a maior area
    }

    public static void criaPoligonos(Mapa mapa, Coordenada coordenadaNW) {
        Coordenada auxX = new Coordenada(mapa.getW(), mapa.getH());
        Coordenada auxY = new Coordenada(mapa.getW(), mapa.getH());
        // 1- LINHA X - PROCURAR MENOR X MAIOR QUE BX, exemplo 3,9 que é o M
        // 1 - LINHA Y - PROCURAR MENOR Y MAIOR QUE BY, exemplo 10,2 que é o P
        for (Mina mina : mapa.getMinas()) {
            Coordenada coordenada = mina.getCoordenada();
            if (coordenada.getX() > coordenadaNW.getX() &&
                    coordenada.getX() < auxX.getX()) {
                auxX = coordenada;
            }
            if (coordenada.getY() > coordenadaNW.getY()
                    && coordenada.getY() < auxY.getY()) {
                auxY = coordenada;
            }
        }
        // 2 - LINHA X - PROCURAR MENOR X MAIOR QUE MX, que é 3, exemplo 5,4 que é o N
        // 2 - LINHA Y - PROCURAR MENOR Y MAIOR QUE PY, que é 2, exemplo 5,4 que é o N

        Coordenada aux2X = new Coordenada(mapa.getW(), mapa.getH());
        Coordenada aux2Y = new Coordenada(mapa.getW(), mapa.getH());
        for (Mina mina : mapa.getMinas()) {
            Coordenada coordenada = mina.getCoordenada();
            if (coordenada.getX() >= auxX.getX() &&
                    coordenada.getX() < aux2X.getX()) {
                aux2X = coordenada;
            }
            if (coordenada.getY() >= auxY.getY()
                    && coordenada.getY() < aux2Y.getY()) {
                aux2Y = coordenada;
            }
        }

        Coordenada coordenadaNE = new Coordenada(auxY.getX(), coordenadaNW.getY());
        Coordenada coordenadaSW = new Coordenada(coordenadaNW.getX(), auxX.getY());
        Coordenada coordenadaSE = new Coordenada(auxY.getX(), auxX.getY());
        Poligono poligono = new Poligono(
                coordenadaNW,
                coordenadaNE,
                coordenadaSW,
                coordenadaSE);
        mapa.getPoligonos().add(poligono);

    }

}