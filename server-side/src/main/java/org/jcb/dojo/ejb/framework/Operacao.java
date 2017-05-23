package org.jcb.dojo.ejb.framework;
/**
 *
 * @author jean
 */
public abstract class Operacao implements Nodo {
    private final Nodo esquerda;
    private final Nodo direita;

    public Operacao(Nodo esquerda, Nodo direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }

   
    @Override
    public final double calcula() {
        return executa(this.esquerda.calcula(), this.direita.calcula());
    }
    
    protected abstract double executa(double esquerda, double direita);
    
    protected abstract String simbolo();

    @Override
    public String toString() {
        return  "(" + esquerda + " " + simbolo() + " " + direita + ")";
    }
    
    
    
}
