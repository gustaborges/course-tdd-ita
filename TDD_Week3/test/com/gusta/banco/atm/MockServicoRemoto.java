package com.gusta.banco.atm;

import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.IServicoRemoto;

public class MockServicoRemoto implements IServicoRemoto {

	@Override
	public ContaCorrente recuperarConta(String numeroDaConta) {
		return new ContaCorrente("Joao");
	}

	@Override
	public void persistirConta() {
		// TODO Auto-generated method stub

	}

}
