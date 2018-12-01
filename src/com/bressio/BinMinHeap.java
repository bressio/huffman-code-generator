package com.bressio;

public class BinMinHeap {
    private int n; /* Numero de elementos no heap */
    private int tam; /* Tamanho do heap */
    private	Arvbin[] vetor; /* Vetor com elementos */

    /* Constr�i heap vazio a partir dos elementos (caracteres). */
    public BinMinHeap(int tamanho) {
        n = tamanho;
        tam = tamanho;
        vetor = new Arvbin[tamanho+1];
    }

    /* Testa se a heap est� vazia. */
    public boolean vazia() {
        return n == 0;
    }

    /* Refaz a condi��o de heap (heapify). */
    public void refaz(int i) {
        Arvbin x = vetor[i];

        while (2 * i <= n) {
            int leftmostChild = 2 * i;
            int rightmostChild = (2 * i) + 1;
            int smallestChild = leftmostChild;

            if ((leftmostChild != n) && (vetor[rightmostChild].compareTo(vetor[leftmostChild]) < 0)) {
                smallestChild = rightmostChild;
            }
            
            if (vetor[smallestChild].compareTo(x) < 0) {
                vetor [i] = vetor[smallestChild];
            } else {
                break;
            }

            i = smallestChild;
        }

        vetor[i] = x;
    }

    /* Constr�i a heap (build heap). */
    public void constroiHeap() {
        for( int i = n / 2; i > 0; i-- )
            refaz(i);
    }

    /* L� dados via teclado e insere na heap. */
    public void carregaDados() {
        int errors = 0;
        for (int i = 0; i < tam + errors; i++) {
            StringFormat.printInputDialog(
                    "Digite o s�mbolo e a frequ�ncia" +
                            " (" + (i + 1 - errors) + "/" + tam + ")");
            String input = StringIO.getInstance().textInput();
            if (StringFormat.isValid(input)) {
                vetor[i + 1 - errors] = new Arvbin(StringFormat.getChar(input), StringFormat.getInt(input));
            } else {
                StringFormat.printError("Voc� digitou uma entrada inv�lida");
                System.out.println();
                errors++;
            }
        }
        constroiHeap();
    }

    /* Executa o algoritmo de Huffman. */
    public void aplicaHuffman() {
        /* O grupo deve preencher a implementa��o. */
    }

    /* Chama o m�todo mostra c�digos da classe Arvbin. */
    public void mostraCodigos() {
        /* O grupo deve preencher a implementa��o. */
    }

    /* Insere x na heap. */
    public void insere(Arvbin x) {
        /* O grupo deve preencher a implementa��o. */
    }

    /* Remove e retorna o menor item. */
    public Arvbin removeMin() {
        Arvbin smallestElement;

        if(this.vazia()) {
            StringFormat.printError("Fila de prioridades vazia!");
            return null;
        }

        smallestElement = vetor[1];
        vetor[1] = vetor[n];
        n--;
        refaz(1);

        return smallestElement;
    }

    /* Imprime o conte�do da heap. */
    public void imprime() {
        for(int i = 1; i <= n; i++)
            StringFormat.printColumn(vetor[i].toString() + " ");

        System.out.println();

    }
}
