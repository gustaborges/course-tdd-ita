package com.gusta.banco.atm;


import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.IServicoRemoto;

public class MockServicoRemoto implements IServicoRemoto {

	private ContaCorrente _contaQueSeraRecuperada;
	
	public MockServicoRemoto() {
		_contaQueSeraRecuperada = new ContaCorrente("Joao");
	}
	
	@Override
	public ContaCorrente recuperarConta(String numeroDaConta) {
		return _contaQueSeraRecuperada;
	}



	@Override
	public void persistirConta(ContaCorrente conta) {
		
	}

}
