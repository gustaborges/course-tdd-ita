package com.gusta.banco.contas;

public interface IServicoRemoto {
	public ContaCorrente recuperarConta(String numeroDaConta) throws FalhaAoRecuperarContaException;
	public void persistirConta();

}
