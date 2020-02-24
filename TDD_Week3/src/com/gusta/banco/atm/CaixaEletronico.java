package com.gusta.banco.atm;

import java.text.DecimalFormat;

import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.IServicoRemoto;

public class CaixaEletronico {
	private IHardware _hardware;
	private IServicoRemoto _servicoRemotoContas;
	
	private String _numeroDaConta;
	
	public CaixaEletronico(IHardware hardware, IServicoRemoto servicoRemotoConta) {
		_hardware = hardware;
		_servicoRemotoContas = servicoRemotoConta;
	}
	
	public String logar() {
		try {
			_numeroDaConta = _hardware.pegarNumeroDaContaCartao();
			return "Usuário Autenticado";
		} catch (FalhaNaLeituraDoCartaoException ex ) {
			return "Não foi possível autenticar o usuário";
		}
	}

	public String saldo() {
		double saldo = _servicoRemotoContas.recuperarConta(_numeroDaConta).getSaldo();
		DecimalFormat formatoMonetario = new DecimalFormat();
		formatoMonetario.applyPattern("R$ ###,###,##0.00");
		return "O saldo é " + formatoMonetario.format(saldo);
	}
	

	public String depositar() throws FalhaNoLeitorDeEnvelopeException {
		try {
			double valorDeposito = _hardware.lerEnvelope();
			ContaCorrente conta = _servicoRemotoContas.recuperarConta(_numeroDaConta);
			conta.deposita(valorDeposito);
			_servicoRemotoContas.persistirConta(conta);
			return "Depósito recebido com sucesso";
		} catch (FalhaNoLeitorDeEnvelopeException ex) {
			throw ex;
		}
		
	}


}
