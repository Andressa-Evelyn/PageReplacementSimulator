package br.com.simulador.model;
import br.com.simulador.algoritmos.*;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            List<Integer> paginas = PageReplacementSimulator.readPageReferencesFromFile("entrada.txt"); //entrada

           // Scanner scanner = new Scanner(System.in);
           // System.out.print("Informe o número de molduras: ");
          //  int frames = scanner.nextInt();
            int frames = 3;

            int fifo = Fifo.executar(paginas, frames);
            int otimo = Otimo.executar(paginas, frames);
            int lfu = Lfu.executar(paginas, frames);
            int nfu = Nfu.executar(paginas, frames);
            int clock = Clock.executar(paginas, frames);

            System.out.println("\n=== RESULTADO DAS SIMULAÇÕES ===");
            System.out.println("- FIFO  : " + fifo + " faltas de página");
            System.out.println("- ÓTIMO : " + otimo + " faltas de página");
            System.out.println("- LFU   : " + lfu + " faltas de página");
            System.out.println("- NFU   : " + nfu + " faltas de página");
            System.out.println("- CLOCK : " + clock + " faltas de página");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
