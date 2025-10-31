package br.com.simulador.algoritmos;
import java.util.*;

/**
 *Remove a página menos frequentemente usada.
 * Se houver empate, remove a mais antiga.
 *
 * Criar mapa frequencia (conta quantas vezes cada página é usada).
 * Quando a memória estiver cheia e for necessário substituir:
 * Remover a página com menor frequência.
 * Se empate → a mais antiga.
 */

public class Lfu {
    public static int executar(List<Integer> paginas, int numFrames) {
        Map<Integer, Integer> freq = new HashMap<>(); //chave valor
        List<Integer> memoria = new ArrayList<>();
        int faltas = 0;

        for (int pagina : paginas) {
            freq.put(pagina, freq.getOrDefault(pagina, 0) + 1); //pagina usada, incrementa frequencia

            if (memoria.contains(pagina)) {
                continue; //nao tem falta
            }

            faltas++; //memoria nao contem pagina

            if (memoria.size() < numFrames) {
                memoria.add(pagina);
            } else { //memoria cheia
                int minFreq = Integer.MAX_VALUE;
                int paginaARemover = memoria.get(0);

                for (int p : memoria) {
                    int f = freq.getOrDefault(p, 0);
                    if (f < minFreq) {
                        minFreq = f; //remove a pagina com menor frequencia
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