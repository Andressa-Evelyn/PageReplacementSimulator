package br.com.simulador.algoritmos;
import java.util.*;

/**
 *Substitui a página que demorará mais para ser usada novamente (ou não será mais usada).
 *
 * Se já estiver na memória → ok.
 * Se a memória estiver cheia → olhar à frente na lista e remover a página cujo próximo uso está mais distante (ou que não será usada novamente).
 */

public class Otimo {
    public static int executar(List<Integer> paginas, int numFrames) {
        List<Integer> memoria = new ArrayList<>(); //como é arraylist, tem que realocar todos quando há deslocamento
        int faltas = 0;

        for (int i = 0; i < paginas.size(); i++) {
            int pagina = paginas.get(i);

            if (!memoria.contains(pagina)) {
                faltas++;

                if (memoria.size() < numFrames) {
                    memoria.add(pagina);
                } else {
                    int paginaARemover = -1;
                    int maxDistancia = -1;

                    for (int p : memoria) { // memória cheia e tenho que remover o com maxdistancia de uso
                        int proximoUso = paginas.subList(i + 1, paginas.size()).indexOf(p); //pega o indice
                        if (proximoUso == -1) { //se nao encontrar a pagina na referencia futura
                            paginaARemover = p;
                            break;
                        } else if (proximoUso > maxDistancia) { //encontrou pagina na referencia futura
                            maxDistancia = proximoUso;
                            paginaARemover = p;
                        }
                    }

                    memoria.remove((Integer) paginaARemover);
                    memoria.add(pagina);
                }
            }
        }

        return faltas;
    }
}