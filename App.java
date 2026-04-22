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

        mapa.setW(Integer.parseInt(linhas.get(0).split(" ")[0]));
        mapa.setH(Integer.parseInt(linhas.get(0).split(" ")[1]));
        mapa.setQtaMina(Integer.parseInt(linhas.get(0).split(" ")[2]));

        for (int i = 1; i < linhas.size(); i++) {
            Coordenada coordenada = new Coordenada(Integer.parseInt(linhas.get(i).split(" ")[0]),
                    Integer.parseInt(linhas.get(i).split(" ")[1]));
            Mina mina = new Mina(coordenada);
            mapa.getMinas().add(mina);
        }

        mapa.getCantos().add(new Coordenada(0, 0));
        mapa.getCantos().add(new Coordenada(0, mapa.getW()));
        mapa.getCantos().add(new Coordenada(mapa.getH(), 0));
        mapa.getCantos().add(new Coordenada(mapa.getH(), mapa.getW()));
        for (Mina mina : mapa.getMinas()) {
            mapa.getCantos().add(new Coordenada(0 , mina.getCoordenada().getY()));            
            mapa.getCantos().add(new Coordenada(mina.getCoordenada().getX(), 0));
            mapa.getCantos().add(new Coordenada(mapa.getH(), mina.getCoordenada().getY()));            
            mapa.getCantos().add(new Coordenada(mina.getCoordenada().getX(), mapa.getW()));
            for (Mina mina2 : mapa.getMinas()){
                mapa.getCantos().add(new Coordenada(mina.getCoordenada().getX(),mina2.getCoordenada().getY()));
                mapa.getCantos().add(new Coordenada(mina2.getCoordenada().getX(),mina.getCoordenada().getY()));
            }
        }
        int teste = 0;
    }    
}