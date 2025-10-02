package Buscadores;

import java.util.ArrayList;
import java.util.List;

public class BuscadorParalelo {
    private final String nome;
    private final int quantidadeArquivos;

    public BuscadorParalelo(int quantidadeArquivos, String nome) {
        this.quantidadeArquivos = quantidadeArquivos;
        this.nome = nome;
    }

    List<Thread> threads = new ArrayList<>();

    public void iniciarThreads() throws InterruptedException {
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        long inicio = System.currentTimeMillis();
        for (int i = 1; i <= quantidadeArquivos; i++) {
            Thread t = new Thread(new ThreadParalela(i, nome, inicio));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        if (!ThreadParalela.IsEncontrado()) {
            long fim = System.currentTimeMillis();
            System.out.println("Nome não foi encontrado em nenhum arquivo");
            System.err.println("Tempo de execução: " + (fim - inicio) + "ms");
        }
    }
}
