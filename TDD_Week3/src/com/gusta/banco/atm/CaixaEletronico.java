package com.gusta.banco.atm;

import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.FalhaAoRecuperarContaException;
import com.gusta.banco.contas.IServicoRemoto;

public class CaixaEletronico {
	private IHardware _hardware;
	private IServicoRemoto _servicoRemotoContas;
	
	private ContaCorrente _conta;
	
	public CaixaEletronico(IHardware hardware, IServicoRemoto servicoRemotoConta) {
		this._hardware = hardware;
		this._servicoRemotoContas = servicoRemotoConta;
	}
	
	public String logar() {
		String numeroDaConta;
		try {
			numeroDaConta = this._hardware.pegarNumeroDaContaCartao();
			this.setConta(this._servicoRemotoContas.recuperarConta(numeroDaConta));
			return "Usuário Autenticado";
		} catch (FalhaNaLeituraDoCartaoException | FalhaAoRecuperarContaException ex ) {
			return "Não foi possível autenticar o usuário";
		}
	}

	public ContaCorrente getConta() {
		return _conta;
	}

	public void setConta(ContaCorrente conta) {
		this._conta = conta;
	}
}
