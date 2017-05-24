package org.jcb.dojo.ejb.server;

import java.util.Stack;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jcb.dojo.ejb.framework.Divisao;
import org.jcb.dojo.ejb.framework.Multiplicacao;
import org.jcb.dojo.ejb.framework.Nodo;
import org.jcb.dojo.ejb.framework.Soma;
import org.jcb.dojo.ejb.framework.Subtracao;
import org.jcb.dojo.ejb.framework.Valor;

@Stateless
@Remote(CalculadoraExpressaoInterface.class)
public class CalculadoraExpressao implements CalculadoraExpressaoInterface{

	@Override
	public String calculaExpressao(String expressao){
		if (expressao == null){
			return "";
		}
		StringBuilder expressaoTratada = preparaExpressao(expressao);
		String[] expressaoArray = expressaoTratada.toString().split(" ");
		Stack<Double> pilhaRPN = new Stack<Double>();
		Nodo esquerda, direita, calculo;
		for (int i = 0; i < expressaoArray.length; i++){
			switch (expressaoArray[i]) {
			case "+":
				direita = new Valor(pilhaRPN.pop());
				esquerda = new Valor(pilhaRPN.pop());
				calculo = new Soma(esquerda, direita);
				pilhaRPN.push(calculo.calcula());
				break;
			case "-":
				direita = new Valor(pilhaRPN.pop());
				esquerda = new Valor(pilhaRPN.pop());
				calculo = new Subtracao(esquerda, direita);
				pilhaRPN.push(calculo.calcula());
				break;
			case "*":
				direita = new Valor(pilhaRPN.pop());
				esquerda = new Valor(pilhaRPN.pop());
				calculo = new Multiplicacao(esquerda, direita);
				pilhaRPN.push(calculo.calcula());
				break;
			case "/":
				direita = new Valor(pilhaRPN.pop());
				esquerda = new Valor(pilhaRPN.pop());
				calculo = new Divisao(esquerda, direita);
				if (direita.calcula() == 0.0){
					return "Não pode dividir por zero!";
				}
				pilhaRPN.push(calculo.calcula());
				break;
			default:
				try {
					pilhaRPN.push(Double.parseDouble(expressaoArray[i]));
				} catch (NumberFormatException e) {	  
			           return "Expressão não pode conter letras!";
				}
				
				break;
			}
			
		}
		
		return "Resultado da Expressão: " + pilhaRPN.peek().toString();
	}
	
	@Override
	public StringBuilder preparaExpressao(String expressao) {
		char[] elemento = expressao.toCharArray();
		Stack<Character> pilhaOperadores = new Stack<Character>();
		StringBuilder expressaoTratada = new StringBuilder();
		for (int i = 0; i < elemento.length; i++){
			switch (elemento[i]) {
			case '+':
			case '-':
				while (!pilhaOperadores.empty() && (pilhaOperadores.peek() == '*' || pilhaOperadores.peek() == '/')){
					expressaoTratada.append(' ').append(pilhaOperadores.pop());
				}
			case '*':
			case '/':
				expressaoTratada.append(' ');
			case '(':
				pilhaOperadores.push(elemento[i]);
			case ' ':
				break;
			case ')':
				while (!pilhaOperadores.empty() && pilhaOperadores.peek() != '('){
					expressaoTratada.append(' ').append(pilhaOperadores.pop());
				}	                
				if (!pilhaOperadores.empty()){
					pilhaOperadores.pop();
				}
				break;
			default:
				expressaoTratada.append(elemento[i]);
				break;
			}
		}

		while (!pilhaOperadores.isEmpty()){
			expressaoTratada.append(' ').append(pilhaOperadores.pop());
		}	        
		return expressaoTratada;
	}

}
