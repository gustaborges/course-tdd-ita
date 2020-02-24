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
		this._titular = nomeTitular;
	}

	public String getNumeroDaConta() {
		return this._numeroDaConta;
	}
	
	private void geraNumeroDaConta() {
		int numero = ++_lastNumeroDeConta;
		this._numeroDaConta = String.valueOf(numero);
	}
}
