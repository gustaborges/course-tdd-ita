package com.gusta.banco.atm;


import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.FalhaAoRecuperarContaException;
import com.gusta.banco.contas.IServicoRemoto;

public class MockServicoRemoto implements IServicoRemoto {

	public enum FalhasSR { RECUPERACAO_DA_CONTA }
	private FalhasSR _erroForcado;
	
	@Override
	public ContaCorrente recuperarConta(String numeroDaConta) throws FalhaAoRecuperarContaException {
		if (getErroForcado() == FalhasSR.RECUPERACAO_DA_CONTA)
			throw new FalhaAoRecuperarContaException("Erro forcado pelo mock ao recuperar conta.");
		return new ContaCorrente("Joao");
	}



	@Override
	public void persistirConta() {
		// TODO Auto-generated method stub

	}

	public void setFalha(FalhasSR falha) {
		this._erroForcado = falha;		
	}
	
	private FalhasSR getErroForcado() {
		return _erroForcado;
	}

}
