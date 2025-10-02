package Buscadores;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutaBuscaPorBlocos {

    private final int quantidadeArquivos;
    private final String nome;
    private final int numThreads = 4;

    public ExecutaBuscaPorBlocos(int quantidadeArquivos, String nome) {
        this.quantidadeArquivos = quantidadeArquivos;
        this.nome = nome;
    }

    public void executarBusca() throws Exception {
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        long inicio = System.currentTimeMillis();
        BuscaParalelaPorBlocos.resetEncontrado(); // <- reseta antes de começar

        for (int i = 1; i <= quantidadeArquivos; i++) {
            String arquivo = "database//a" + i + ".txt";
            try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r");
                    FileChannel channel = raf.getChannel()) {

                long tamanho = channel.size();
                long bloco = tamanho / numThreads;

                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, tamanho);

                ExecutorService pool = Executors.newFixedThreadPool(numThreads);

                for (int j = 0; j < numThreads; j++) {
                    long start = j * bloco;
                    long end = (j == numThreads - 1) ? tamanho : (start + bloco);

                    pool.execute(new BuscaParalelaPorBlocos(buffer, start, end, nome, i, inicio));
                }

                pool.shutdown();
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            }
        }

        long fim = System.currentTimeMillis();

        if (!BuscaParalelaPorBlocos.IsEncontrado()) {
            System.out.println("Nome não foi encontrado em nenhum arquivo");
            System.out.println("Tempo total de execução: " + (fim - inicio) + "ms");
        }
    }

}