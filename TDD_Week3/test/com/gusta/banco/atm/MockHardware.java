package com.gusta.banco.atm;

public class MockHardware implements IHardware {

	private String _numeroDaContaCartao;
	public enum FalhasHW { LEITURA_DO_CARTAO }
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
	public void entregarDinheiro() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lerEnvelope() {
		// TODO Auto-generated method stub

	}


}
