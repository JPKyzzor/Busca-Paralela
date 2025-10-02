package Buscadores;

import java.nio.MappedByteBuffer;

public class BuscaParalelaPorBlocos implements Runnable {
    private final MappedByteBuffer buffer;
    private final long start;
    private final long end;
    private final String nome;
    private final int numArquivo;
    private long inicio;
    private static volatile boolean encontrado = false;

    public BuscaParalelaPorBlocos(MappedByteBuffer buffer, long start, long end, String nome, int numArquivo,
            long inicio) {
        this.buffer = buffer;
        this.start = start;
        this.end = end;
        this.nome = nome;
        this.numArquivo = numArquivo;
        this.inicio = inicio;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        int numeroLinhas = 1;

        for (long i = start; i < end && !encontrado; i++) {
            char c = (char) buffer.get((int) i);

            if (c == '\n') {
                String linha = sb.toString();
                if (linha.contains(nome)) {
                    long fim = System.currentTimeMillis();
                    System.out.printf("Nome encontrado no arquivo a%d.txt na linha %d \n", numArquivo, numeroLinhas);
                    System.out.println(
                            "Tempo de execução do buscador paralelo por blocos: " + (fim - this.inicio) + "ms");
                    encontrado = true;
                    return;
                }
                sb.setLength(0);
                numeroLinhas++;
            } else {
                sb.append(c);
            }
        }

        if (sb.length() > 0 && !encontrado) {
            String linha = sb.toString();
            if (linha.contains(nome)) {
                System.out.printf("Nome encontrado no arquivo a%d.txt: %s%n", numArquivo, linha);
                encontrado = true;
            }
        }
    }

    public static boolean IsEncontrado() {
        return encontrado;
    }

    public static void resetEncontrado() {
        encontrado = false;
    }
}
