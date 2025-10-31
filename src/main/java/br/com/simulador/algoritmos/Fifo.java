package br.com.simulador.algoritmos;
import java.util.*;

/**
 * O primeiro a entrar é o primeiro a sair.
 * Quando ocorre uma falta e a memória está cheia, remove-se a página mais antiga.
 *
 * HashSet -> não permite duplicatas
 * LinkedList -> lista duplamente encadeada, ou seja não é necessário realocar elementos
 */

public class Fifo {
    public static int executar(List<Integer> paginas, int numFrames) {
        Set<Integer> memoria = new HashSet<>();
        Queue<Integer> fila = new LinkedList<>();
        int faltas = 0;

        for (int pagina : paginas) {
            if (!memoria.contains(pagina)) {
                faltas++;

                if (memoria.size() == numFrames) {
                    int removida = fila.poll(); //remove o mais antigo
                    memoria.remove(removida);
                }

                memoria.add(pagina);
                fila.add(pagina);
            }
        }
        return faltas;
    }
}
