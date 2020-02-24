package com.gusta.banco.atm;

public class MockHardware implements IHardware {

	private String _numeroDaContaCartao;
	
	private double _valorASerLidoDoEnvelope;
	
	public enum FalhasHW { LEITURA_DO_CARTAO, LEITOR_DE_ENVELOPE, EMISSOR_DE_NOTAS }
	private FalhasHW _erroForcado;
	
	
	
	public MockHardware() {	}

	public void setNumeroDaConta(String numeroDaConta) {
		_numeroDaContaCartao = numeroDaConta;
	}
	
	public void setFalha(FalhasHW falha) {
		_erroForcado = falha;
	}

	
	@Override
	public String pegarNumeroDaContaCartao() throws FalhaNaLeituraDoCartaoException {
		if (_erroForcado == FalhasHW.LEITURA_DO_CARTAO)
			throw new FalhaNaLeituraDoCartaoException("Erro forçado pelo mock na leitura do cartão.");
		
		return _numeroDaContaCartao;
	}

	@Override
	public void entregarDinheiro(double valor) throws FalhaNaEmissaoDasNotasException {
		if (_erroForcado == FalhasHW.EMISSOR_DE_NOTAS)
			throw new FalhaNaEmissaoDasNotasException("Erro forçado pelo mock no emissor de notas");
	}

	@Override
	public double lerEnvelope() throws FalhaNoLeitorDeEnvelopeException {
		if (_erroForcado == FalhasHW.LEITOR_DE_ENVELOPE) throw new FalhaNoLeitorDeEnvelopeException("Erro forçado pelo mock no leitor de envelope");
		return _valorASerLidoDoEnvelope;
	}

	public void setValorASerLidoDoEnvelope(double valor) {
		_valorASerLidoDoEnvelope = valor;
	}


}
