
package org.jcb.dojo.ejb.cliente;

import java.util.Hashtable;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jcb.dojo.ejb.server.CalculadoraExpressaoInterface;

public class RemoteEJBCalculadoraClient {

	public static void main(String[] args) throws Exception {
		System.out.println("###########################\nExecutando remoto");

		invokeCalculadora();
	}

	private static void invokeCalculadora() throws NamingException {
		final CalculadoraExpressaoInterface calc = lookupRemoteCalculadora();
		System.out.println("##############\nExecutando Calculadora!!!");
		Scanner input = new Scanner(System.in);
		String expressao;
		int opcao = 1;
		while (opcao != 0){
			System.out.println("Entre com a expressão: ");
			Scanner input1 = new Scanner(System.in);
			expressao = input1.next();
			System.out.println("\n" + calc.calculaExpressao(expressao));
			System.out.println("###############\n- Enviar outra expressão (1) ou Sair (0)?");
//			System.out.println("1. Enviar outra expressão");
//			System.out.println("0. Sair");
//			System.out.println("Opção: ");
			opcao = input.nextInt();
			if (opcao != 0){
				System.out.println("#####################################");
				System.out.println("#####################################");
			}
		}
	}
	
	private static CalculadoraExpressaoInterface lookupRemoteCalculadora() throws NamingException {
		final Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);

		// The JNDI lookup name for a stateless session bean has the syntax of:
		// ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
		//
		// <appName> The application name is the name of the EAR that the EJB is
		// deployed in
		// (without the .ear). If the EJB JAR is not deployed in an EAR then
		// this is
		// blank. The app name can also be specified in the EAR's
		// application.xml
		//
		// <moduleName> By the default the module name is the name of the EJB
		// JAR file (without the
		// .jar suffix). The module name might be overridden in the ejb-jar.xml
		//
		// <distinctName> : EAP allows each deployment to have an (optional)
		// distinct name.
		// This example does not use this so leave it blank.
		//
		// <beanName> : The name of the session been to be invoked.
		//
		// <viewClassName>: The fully qualified classname of the remote
		// interface. Must include
		// the whole package name.

		// let's do the lookup
		return (CalculadoraExpressaoInterface) context
				.lookup("ejb:/wildfly-ejb-remote-server-side/CalculadoraExpressao!" + CalculadoraExpressaoInterface.class.getName());
	}


}