package org.jcb.dojo.ejb.framework;
/**
 *
 * @author jean
 */
public class Subtracao extends Operacao {

    public Subtracao(Nodo esquerda, Nodo direita) {
        super(esquerda, direita);
    }

    @Override
    protected double executa(double esquerda, double direita) {
        return esquerda - direita;
    }

    @Override
    protected String simbolo() {
        return "-";
    }

}
