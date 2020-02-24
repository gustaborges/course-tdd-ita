package com.gusta.banco.atm;

public class MockHardware implements IHardware {

	private String _numeroDaContaCartao;
	
	public MockHardware() {	}
	
	public void setNumeroDaConta(String numeroDaConta) {
		this._numeroDaContaCartao = numeroDaConta;
	}
	
	@Override
	public String pegarNumeroDaContaCartao() {
		return this._numeroDaContaCartao;
	}

	@Override
	public void entregarDinheiro() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lerEnvelope() {
		// TODO Auto-generated method stub

	}

}
