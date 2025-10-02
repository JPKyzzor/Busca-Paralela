package Buscadores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThreadParalela implements Runnable {
    private final int numArquivo;
    private final String nome;
    private final long inicio;
    private static volatile boolean encontrado = false;

    public ThreadParalela(int numArquivo, String nome, long inicio) {
        this.numArquivo = numArquivo;
        this.nome = nome;
        this.inicio = inicio;
    }

    @Override
    public void run() {
        String nomeArquivo = "database//a" + numArquivo + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int numeroLinha = 1;

            while ((linha = br.readLine()) != null) {
                if (linha.equalsIgnoreCase(nome)) {
                    long fim = System.currentTimeMillis();
                    System.out.println("Nome encontrado no arquivo a" + numArquivo +
                            ".txt na linha " + numeroLinha);
                    System.out.println("Tempo de execução do buscador paralelo: " + (fim - this.inicio) + "ms");
                    encontrado = true;
                    return;
                }
                numeroLinha++;
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static boolean IsEncontrado() {
        return encontrado;
    }
}
