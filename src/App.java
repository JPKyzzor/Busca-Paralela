import java.util.Scanner;

import Buscadores.BuscadorArquivo;
import Buscadores.BuscadorNaoParalelo;
import Buscadores.ResultadoBusca;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome que deseja buscar: ");
        String nome = scanner.nextLine().trim();

        BuscadorArquivo buscador = new BuscadorNaoParalelo(7);
        ResultadoBusca resultado = buscador.BuscaNome(nome);

        if (!resultado.isEncontrado()) {
            System.out.println("Nome não encontrado em nenhum arquivo.");
        }

        System.out.println("Tempo de execução: " + resultado.getDuracaoMs() + " ms");

        scanner.close();
    }
}
