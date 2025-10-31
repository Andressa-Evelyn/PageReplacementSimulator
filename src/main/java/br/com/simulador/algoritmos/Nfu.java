package br.com.simulador.algoritmos;

import java.util.*;

/**
 * NFU (Not Frequently Used)
 * Remove a página menos frequentemente usada, com envelhecimento.
 *
 * Cada página tem um contador que representa a frequência ponderada.
 * A cada acesso:
 *   - incrementa o contador da página usada;
 *   - periodicamente, todos os contadores são reduzidos (simulando esquecimento).
 * Quando a memória estiver cheia:
 *   - remove a página com menor contador;
 *   - em empate, remove a mais antiga.
 */
public class Nfu {

    public static int executar(List<Integer> paginas, int numFrames) {
        Map<Integer, Integer> freq = new HashMap<>();
        List<Integer> memoria = new ArrayList<>();
        int faltas = 0;

        // define a taxa de envelhecimento (a cada N acessos)
        final int TAXA_ENVELHECIMENTO = 4;
        int acessos = 0;

        for (int pagina : paginas) {
            acessos++;

            // envelhecimento: divide todas as frequências por 2 a cada intervalo
            if (acessos % TAXA_ENVELHECIMENTO == 0) {
                for (int p : freq.keySet()) {
                    freq.put(p, freq.get(p) / 2);
                }
            }

            // incrementa o contador da página atual
            freq.put(pagina, freq.getOrDefault(pagina, 0) + 1);

            // se já está na memória, não há falta
            if (memoria.contains(pagina)) {
                continue;
            }

            faltas++;

            if (memoria.size() < numFrames) {
                memoria.add(pagina);
            } else {
                // encontra a página menos frequentemente usada
                int minFreq = Integer.MAX_VALUE;
                int paginaARemover = memoria.get(0);

                for (int p : memoria) {
                    int f = freq.getOrDefault(p, 0);
                    if (f < minFreq) {
                        minFreq = f;
                        paginaARemover = p;
                    }
                }

                memoria.remove((Integer) paginaARemover);
                memoria.add(pagina);
            }
        }

        return faltas;
    }
}
