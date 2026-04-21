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
        System.out.println(new java.util.Date().toString());
        mapa.setW(100);
        mapa.setH(77);

        for(int i = 0; i < mapa.getW(); i++) {
            for(int j = 0; j < mapa.getH(); j++) {
                for(int k = 0; k < mapa.getW(); k++) {
                    for(int l = 0; l < mapa.getH(); l++) {
                        try{                
                            Coordenada coordenadaNW = new Coordenada(i, j);
                            Coordenada coordenadaNE = new Coordenada(k, j);
                            Coordenada coordenadaSW = new Coordenada(i, l);
                            Coordenada coordenadaSE = new Coordenada(k, l);    
                            Poligono poligono = new Poligono(coordenadaNW, coordenadaNE, coordenadaSW, coordenadaSE);
                            mapa.getPoligonos().add(poligono);
                        } catch (Exception e) {
                            System.err.println("Erro ao criar poligono: "+ i+","+j+","+k+","+l+" - " + e.getMessage());
                        }
                    }
                }
            }
        }
        System.out.println(new java.util.Date().toString());
        

    }
}