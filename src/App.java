import Buscadores.BuscadorArquivo;
import Buscadores.BuscadorNaoParalelo;
import Buscadores.BuscadorParalelo;
import Buscadores.ExecutaBuscaPorBlocos;
import Buscadores.ResultadoBusca;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Digite o nome que deseja buscar: ");
            String nome = scanner.nextLine().trim();

            System.out.print(
                    "Escolha o Buscador:\n1 - Buscador Não Paralelo\n2 - Buscador Paralelo\n3 - Buscador Paralelo por Blocos\nDigite sua opção: ");
            String op = scanner.nextLine().trim();

            switch (op) {
                case "1": {
                    BuscadorArquivo buscador = new BuscadorNaoParalelo(7);
                    ResultadoBusca resultado = buscador.BuscaNome(nome);
                    if (!resultado.isEncontrado()) {
                        System.out.println("Nome não encontrado em nenhum arquivo.");
                    }
                    System.out
                            .println("Tempo de execução do buscador não paralelo: " + resultado.getDuracaoMs() + " ms");
                    break;
                }
                case "2": {
                    BuscadorParalelo buscadorParalelo = new BuscadorParalelo(7, nome);
                    buscadorParalelo.iniciarThreads();
                    break;
                }
                case "3": {
                    ExecutaBuscaPorBlocos executaBusca = new ExecutaBuscaPorBlocos(7, nome);
                    try {
                        executaBusca.executarBusca();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    System.out.print("Essa opção nao existe!");
                    break;
                }
            }
        }
    }
}
