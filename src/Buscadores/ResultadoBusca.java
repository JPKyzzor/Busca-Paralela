package Buscadores;

public class ResultadoBusca {
  private final boolean encontrado;
  private final long duracaoMs;

  public ResultadoBusca(boolean encontrado, long duracaoMs) {
    this.encontrado = encontrado;
    this.duracaoMs = duracaoMs;
  }

  public boolean isEncontrado() {
    return encontrado;
  }

  public long getDuracaoMs() {
    return duracaoMs;
  }
}