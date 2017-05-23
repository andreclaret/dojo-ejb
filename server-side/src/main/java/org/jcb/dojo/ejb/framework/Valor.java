package org.jcb.dojo.ejb.framework;
/**
 *
 * @author jean
 */
public class Valor implements Nodo {

    private final Double val;

    public Valor(double i) {
        this.val = i;
    }
    
    
	@Override
    public double calcula() {
        return this.val;
    }

    @Override
    public String toString() {
        return Double.toString(val);
    }
    
    
}
