package com.bressio;

import java.util.Map;
import java.util.TreeMap;

/* Nessa implementa��o, os conceitos de "n�" e "�rvore" se misturam. */
public class Arvbin implements Comparable<Arvbin>{
    private char simbolo; /* S�mbolo armazenado na raiz. */
    private int frequencia; /* Frequ�ncia do s�mbolo armazenado na raiz. */
    private Arvbin esq, dir; /* Refer�ncias para sub�rvores esquerda e direita. */
    private static Map<Character, String> dictionary = new TreeMap<>();

    /* Construtor de �rvore sem sub�rvores (dir = esq = null). S�o fornecidos
     * apenas o s�mbolo e a frequ�ncia da raiz. */
    public Arvbin(char simbolo, int frequencia) {
        this.simbolo = simbolo;
        this.frequencia = frequencia;
        esq = null;
        dir = null;
    }

    /* Construtor de �rvore com sub�rvores. Al�m de s�mbolo e frequ�ncia da raiz,
     * s�o fornecidas as sub�rvores, que devem ter sido constru�das previamente. */
    public Arvbin(char simbolo, int frequencia, Arvbin esq, Arvbin dir) {
        this.simbolo = simbolo;
        this.frequencia = frequencia;
        this.esq = esq;
        this.dir = dir;
    }

    /* Imprime o conte�do da �rvore em pr�-ordem. */
    public void mostra() {
        StringOut.printInline("( ");
        StringOut.printInline(String.valueOf(frequencia) + " " + simbolo);
        if (esq != null) {
            esq.mostra();
        }
        if (dir != null) {
            dir.mostra();
        }
        StringOut.printInline(" )");
    }

    /* Novo m�todo para imprimir os c�digos de Huffman de cada s�mbolo na �rvore. */
    public void mostraCodigo(String codigo) {
        if (simbolo != '\f') {
            StringOut.printBlock(simbolo + "\t\t|\t\t" + codigo);
            dictionary.put(simbolo, codigo);
        } else {
            esq.mostraCodigo(codigo + 0);
            dir.mostraCodigo(codigo + 1);
        }
    }

    public void encode() {
        String input;
        boolean undefined;
        int size = 0;

        do {
            input = StringIn.getInstance().getInput(
                    "Digite um texto para ser codificado, com base no dicion�rio",
                    "^.+$",
                    "Voc� digitou uma entrada inv�lida"
            );

            undefined = false;

            for (char c : input.toCharArray()) {
                if (!dictionary.containsKey(c)) {
                    StringOut.printError("O texto cont�m caracteres fora do dicion�rio");
                    undefined = true;
                    break;
                }
            }
        } while (undefined);

        StringOut.printNewLine();
        StringOut.printSeparator();
        StringOut.printTitleBlock("Texto codificado:");
        StringOut.printNewLine();

        Stopwatch stopwatch = new Stopwatch();

        for (char c : input.toCharArray()) {
            String code = dictionary.get(c);
            StringOut.printInline(code);
            size += code.length();
        }

        StringOut.printNewLine();

        int original = (int) Math.ceil(((8.0 * input.length()) / 8.0));
        int compressed = (int) Math.ceil((size / 8.0));

        StringOut.printBlock("Tamanho original: " + original + (original >= 2 ? " bytes" : " byte"));
        StringOut.printBlock("Tamanho comprimido: " + compressed + (compressed >= 2 ? " bytes" : " byte"));
        StringOut.printBlock("Redu��o: " + (int) (((float) compressed / original) * 100) + "%");

        stopwatch.stop("Tempo de execu��o do encoding:");
    }

    public void encode(String input) {
        int size = 0;

        StringOut.printTitleBlock("Texto codificado:");
        StringOut.printNewLine();

        for (char c : input.toCharArray()) {
            String code = dictionary.get(c);
            StringOut.printInline(code);
            size += code.length();
        }

        StringOut.printNewLine();

        int original = (int) Math.ceil(((8.0 * input.length()) / 8.0));
        int compressed = (int) Math.ceil((size / 8.0));

        StringOut.printBlock("Tamanho original: " + original + (original >= 2 ? " bytes" : " byte"));
        StringOut.printBlock("Tamanho comprimido: " + compressed + (compressed >= 2 ? " bytes" : " byte"));
        StringOut.printBlock("Redu��o: " + (int) (((float) compressed / original) * 100) + "%");
    }

    public void decode(String input) {
        Arvbin current = this;
        StringBuilder text = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '0') {
                current = current.esq;
            } else {
                current = current.dir;
            }

            if (current.esq == null && current.dir == null) {
                text.append(current.simbolo);
                current = this;
            }
        }
        StringOut.printTitleBlock("Texto decodificado:");
        StringOut.printNewLine();
        StringOut.printInline(text.toString());

    }

    public int getFrequencia() {
        return frequencia;
    }

    @Override
    public int compareTo(Arvbin arvbin) {
        return Integer.compare(frequencia, arvbin.frequencia);
    }

    @Override
    public String toString() {
        return "[" + simbolo + " | " + frequencia + "]";
    }

    /* Caso necess�rio, o grupo pode definir novos m�todos. */
}
