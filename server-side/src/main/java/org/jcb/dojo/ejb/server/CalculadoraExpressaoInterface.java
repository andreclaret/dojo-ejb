package org.jcb.dojo.ejb.server;

import javax.ejb.Remote;

@Remote
public interface CalculadoraExpressaoInterface {

	String calculaExpressao(String expressao);
	StringBuilder preparaExpressao(String expressao);
	
	
}
