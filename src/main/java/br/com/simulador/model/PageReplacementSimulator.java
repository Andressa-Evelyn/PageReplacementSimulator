package br.com.simulador.model;

import java.io.*;
import java.util.*;

public class PageReplacementSimulator {

    /**
     * Lê um arquivo texto e extrai uma lista de inteiros representando
     * a sequência de páginas referenciadas.
     *
     * @param fileName Nome do arquivo a ser lido (ex: "entrada.txt")
     * @return Lista de inteiros (páginas)
     * @throws IOException Se ocorrer erro ao ler o arquivo
     * @throws NumberFormatException Se o arquivo contiver tokens não numéricos
     */
    public static List<Integer> readPageReferencesFromFile(String fileName) throws IOException {
        List<Integer> pageReferences = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Remove espaços extras e separa por espaço
                String[] tokens = line.trim().split("\\s+");

                for (String token : tokens) {
                    if (token.isEmpty()) continue;

                    try {
                        int page = Integer.parseInt(token); //passou o token pra inteiro
                        if (page < 0) { // só existe página positiva
                            throw new NumberFormatException("Página negativa detectada: " + page);
                        }
                        pageReferences.add(page);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Valor inválido encontrado: \"" + token + "\"");
                    }
                }
            }
        }

        if (pageReferences.isEmpty()) {
            throw new IllegalArgumentException("O arquivo está vazio ou não contém números válidos.");
        }

        return pageReferences;
    }

    public static void main(String[] args) {
        String fileName = "entrada.txt"; // nome do arquivo de entrada

        try {
            List<Integer> pageRefs = readPageReferencesFromFile(fileName);

            System.out.println("Sequência de páginas carregada com sucesso!");
            System.out.println("Páginas: " + pageRefs);

            // Aqui você pode futuramente chamar seus algoritmos:
            // int faltasFIFO = Fifo.executar(pageRefs, numFrames);
            // int faltasLRU = Lru.executar(pageRefs, numFrames);
            // int faltasClock = Clock.executar(pageRefs, numFrames);
            // int faltasOtimo = Otimo.executar(pageRefs, numFrames);
            // System.out.println("- FIFO - " + faltasFIFO + " faltas de página");
            // ...

        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo \"" + fileName + "\" não encontrado.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
