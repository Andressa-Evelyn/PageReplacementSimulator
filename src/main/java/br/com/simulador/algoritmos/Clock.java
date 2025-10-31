package br.com.simulador.algoritmos;
import java.util.*;

/**
 *Cada página tem um “bit de uso”.
 *
 * Se o bit de uso = 0 → substitui essa página.
 * Se = 1 → zera o bit e passa para a próxima.
 *
 * Usar duas listas paralelas (memoria e uso).
 * Mover o ponteiro circular.
 * Contar faltas e atualizações de bits.,
 *
 * Se a luz estiver acesa (true), você apaga e gira a roda.
 * Se estiver apagada (false), você troca a página ali.
 * Depois da troca, você acende a luz e gira a roda de novo.
 */

public class Clock {
    public static int executar(List<Integer> paginas, int numFrames) {
        int faltas = 0;
        int ponteiro = 0;

        int[] memoria = new int[numFrames];
        boolean[] uso = new boolean[numFrames];
        Arrays.fill(memoria, -1); //preenche a memoria so com -1

        for (int pagina : paginas) {
            boolean hit = false;

            // verifica se a página já está na memória pela primeira vez
            for (int i = 0; i < numFrames; i++) {
                if (memoria[i] == pagina) { //se ele encontrar a pagina na memoria, então nao houve falta
                    uso[i] = true;
                    hit = true;
                    break;
                }
            }

            if (!hit) { ;//essa pagina não ta na memoria
                faltas++;
                while (uso[ponteiro]) { //verifique se a pagina foi usada recentemente
                    uso[ponteiro] = false; //zera o bit
                    ponteiro = (ponteiro + 1) % numFrames; //avançando pra proxima posi~]ao ciruclar
                }

                //pagina nao foi usada recentemente
                memoria[ponteiro] = pagina; //substituindo a pagina
                uso[ponteiro] = true; //ligando o bit
                ponteiro = (ponteiro + 1) % numFrames;
            }
        }

        return faltas;
    }
}