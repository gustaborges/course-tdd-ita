package com.gusta.banco.contas;

public interface IServicoRemoto {
	public ContaCorrente recuperarConta(String numeroDaConta);
	public void persistirConta(ContaCorrente conta);

}
