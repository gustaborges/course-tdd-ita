package com.gusta.banco.atm;

public interface IHardware {
	public String pegarNumeroDaContaCartao() throws FalhaNaLeituraDoCartaoException;
	public void entregarDinheiro();
	public void lerEnvelope();	
}
