import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

        montaMapa(mapa, linhas);

        criaCantos(mapa);

        localizaPoligonos(mapa, new Coordenada(0, 0));
        /*
         * for (Coordenada canto : mapa.getCantos()) {
         * localizaPoligonos(mapa, canto);
         * }
         */

        System.out.println(mapa.toString());
    }

    private static void montaMapa(Mapa mapa, ArrayList<String> linhas) {
        mapa.setW(Integer.parseInt(linhas.get(0).split(" ")[0]));
        mapa.setH(Integer.parseInt(linhas.get(0).split(" ")[1]));
        mapa.setQtaMina(Integer.parseInt(linhas.get(0).split(" ")[2]));

        for (int i = 1; i < linhas.size(); i++) {
            Coordenada coordenada = new Coordenada(Integer.parseInt(linhas.get(i).split(" ")[0]),
                    Integer.parseInt(linhas.get(i).split(" ")[1]));
            Mina mina = new Mina(coordenada);
            mapa.getMinas().add(mina);
        }
    }

    private static void criaCantos(Mapa mapa) {
        mapa.getCantos().put("0-0", new Coordenada(0, 0));
        mapa.getCantos().put("0-" + mapa.getW(), new Coordenada(0, mapa.getW()));
        mapa.getCantos().put("" + mapa.getH() + "-0", new Coordenada(mapa.getH(), 0));
        mapa.getCantos().put("" + mapa.getH() + "-" + mapa.getW(), new Coordenada(mapa.getH(), mapa.getW()));

        for (Mina mina : mapa.getMinas()) {
            mapa.getCantos().put("0-" + mina.getCoordenada().getY(), new Coordenada(0, mina.getCoordenada().getY()));
            mapa.getCantos().put(mina.getCoordenada().getX() + "-0", new Coordenada(mina.getCoordenada().getX(), 0));
            mapa.getCantos().put(mapa.getH() + "-" + mina.getCoordenada().getY(),
                    new Coordenada(mapa.getH(), mina.getCoordenada().getY()));
            mapa.getCantos().put(mina.getCoordenada().getX() + "-" + mapa.getW(),
                    new Coordenada(mina.getCoordenada().getX(), mapa.getW()));
            for (Mina mina2 : mapa.getMinas()) {
                mapa.getCantos().put(mina.getCoordenada().getX() + "-" + mina2.getCoordenada().getY(),
                        new Coordenada(mina.getCoordenada().getX(), mina2.getCoordenada().getY()));
                mapa.getCantos().put(mina2.getCoordenada().getX() + "-" + mina.getCoordenada().getY(),
                        new Coordenada(mina2.getCoordenada().getX(), mina.getCoordenada().getY()));
            }
        }
    }

    private static void localizaPoligonos(Mapa mapa, Coordenada coordenadaInicial) {
        // vai precisar de while mais tarde
        int auxX = mapa.getW();
        int auxY = mapa.getH();
        int auxX1 = mapa.getW();
        int auxY1 = mapa.getH();

        int x = 0;
        int inicioX = coordenadaInicial.getX();
        int inicioY = coordenadaInicial.getY();
        while (x < 3) {
            System.out.println("-------------------------------");
            if (auxX != mapa.getW()) {
                inicioX = auxX;
                auxX = mapa.getW();

            }
            if (auxY != mapa.getH()) {
                inicioY = auxY;
                auxY = mapa.getH();
            }

            for (Mina mina : mapa.getMinas()) {

                if (mina.getCoordenada().getX() < auxX
                        && mina.getCoordenada().getX() > inicioX
                        && mina.getCoordenada().getY() < auxY) {
                    auxX = mina.getCoordenada().getX();
                    System.out.println("auxX: " + auxX);

                }

                if (mina.getCoordenada().getY() < auxY1
                        && mina.getCoordenada().getY() > inicioY
                        && mina.getCoordenada().getX() < auxX) {
                    auxY1 = mina.getCoordenada().getY();
                    System.out.println("auxY1: " + auxY1);

                }
            }
            for (Mina mina : mapa.getMinas()) {
                if (auxY > mina.getCoordenada().getY()
                        && inicioX < mina.getCoordenada().getX()
                        && auxX > mina.getCoordenada().getX()) {

                    auxY = mina.getCoordenada().getY();
                    System.out.println("auxY: " + auxY);

                }

                if (auxX1 > mina.getCoordenada().getX()
                        && inicioY < mina.getCoordenada().getY()
                        && auxY1 > mina.getCoordenada().getY()) {

                    auxX1 = mina.getCoordenada().getX();
                    System.out.println("auxX1: " + auxX1);

                }
            }

            mapa.getPoligonos().add(new Poligono(
                    new Coordenada(coordenadaInicial.getX(), coordenadaInicial.getY()),
                    new Coordenada(auxX, coordenadaInicial.getY()),
                    new Coordenada(coordenadaInicial.getX(), auxY),
                    new Coordenada(auxX, auxY)));
            mapa.getPoligonos().add(new Poligono(
                    new Coordenada(coordenadaInicial.getX(), coordenadaInicial.getY()),
                    new Coordenada(auxX1, coordenadaInicial.getY()),
                    new Coordenada(coordenadaInicial.getX(), auxY1),
                    new Coordenada(auxX1, auxY1)));

            x++;
        }
    }
}