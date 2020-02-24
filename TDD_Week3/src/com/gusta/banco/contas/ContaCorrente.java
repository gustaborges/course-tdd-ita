package com.gusta.banco.contas;

public class ContaCorrente {
	private static int _lastNumeroDeConta = 9999;
	
	private String _titular;
	private String _numeroDaConta;
	
	public ContaCorrente(String nomeTitular) {
		setTitular(nomeTitular);
		geraNumeroDaConta();
	}
	
	private void setTitular(String nomeTitular) {
		if (nomeTitular == null || nomeTitular.isBlank()) throw new IllegalArgumentException("O nome fornecido é invalido");
		_titular = nomeTitular;
	}

	public String getNumeroDaConta() {
		return _numeroDaConta;
	}
	
	private void geraNumeroDaConta() {
		int numero = ++_lastNumeroDeConta;
		_numeroDaConta = String.valueOf(numero);
	}
}
