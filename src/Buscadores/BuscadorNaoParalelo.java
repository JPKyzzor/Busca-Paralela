package Buscadores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BuscadorNaoParalelo implements BuscadorArquivo {

    private final int quantidadeArquivos;

    public BuscadorNaoParalelo(int quantidadeArquivos) {
        this.quantidadeArquivos = quantidadeArquivos;
    }

    @Override
    public ResultadoBusca BuscaNome(String nome) {
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        long inicio = System.currentTimeMillis();

        for (int i = 1; i <= quantidadeArquivos; i++) {
            String nomeArquivo = "database//a" + i + ".txt";

            try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
                String linha;
                int numeroLinha = 1;

                while ((linha = br.readLine()) != null) {
                    if (linha.equalsIgnoreCase(nome)) {
                        long fim = System.currentTimeMillis();
                        System.out.println("Nome encontrado no arquivo " + nomeArquivo +
                                " na linha " + numeroLinha);
                        return new ResultadoBusca(true, fim - inicio);
                    }
                    numeroLinha++;
                }

            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        long fim = System.currentTimeMillis();
        return new ResultadoBusca(false, fim - inicio);
    }
}
