package com.gusta.banco.atm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gusta.banco.atm.MockHardware.FalhasHW;

class CaixaEletronicoTest {
	
	@BeforeEach
	void setUp() {
		mockHardware = new MockHardware();
		mockServicoRemoto = new MockServicoRemoto();
		caixaATM = new CaixaEletronico(mockHardware,mockServicoRemoto);
	}
	
	@Test
	void whenAutenticacaoBemSucedidaEntaoDevolveMensagemExito() {
		mockHardware.setNumeroDaConta("10000");			
		assertEquals("Usuário Autenticado", caixaATM.logar());
	}

	@Test
	void whenTentoAutenticarELeituraDoCartaoFalhaEntaoDevolveMensagemFalha() {	
		mockHardware.setFalha(FalhasHW.LEITURA_DO_CARTAO);
		assertEquals("Não foi possível autenticar o usuário", caixaATM.logar());
	}
	

	@Test
	void whenNenhumDepositoEConsultoSaldoEntaoSaldoEhZero() {		
		caixaATM.logar();
		assertEquals("O saldo é R$ 0,00", caixaATM.saldo());
	}
	
	
	@Test
	void whenDeposito100EConsultoSaldoEntaoDevolveMensagemSaldoEh100() throws FalhaNoLeitorDeEnvelopeException {		
		caixaATM.logar();
		mockHardware.setValorASerLidoDoEnvelope(100.00);
		caixaATM.depositar();
		assertEquals("O saldo é R$ 100,00", caixaATM.saldo());
	}
	
	@Test
	void whenDepositoELeitorDoEnvelopeFalhaEntaoDevolvoFalhaNoLeitorDeEnvelopeException() {		

		try {
			caixaATM.logar();
			mockHardware.setValorASerLidoDoEnvelope(1050.00);
			mockHardware.setFalha(FalhasHW.LEITOR_DE_ENVELOPE);
			caixaATM.depositar();
			fail();
		}
		catch(FalhaNoLeitorDeEnvelopeException ex) { }
		
		assertEquals("O saldo é R$ 0,00", caixaATM.saldo());
	}
	
	@Test
	void whenSaqueBemSucedidoEntaoDevolveMensagemExito() throws FalhaNaEmissaoDasNotasException, FalhaNoLeitorDeEnvelopeException {		
		caixaATM.logar();
		mockHardware.setValorASerLidoDoEnvelope(1000.00);
		caixaATM.depositar();
		assertEquals("Retire seu dinheiro", caixaATM.sacar(500.00));
	}
	
	@Test
	void whenSolicitoSaqueMaiorQueSaldoEntaoLancaMensagemSaldoInsuficiente() throws FalhaNaEmissaoDasNotasException, FalhaNoLeitorDeEnvelopeException {		
		caixaATM.logar();
		mockHardware.setValorASerLidoDoEnvelope(1000.00);
		caixaATM.depositar();
		assertEquals("Saldo Insuficiente", caixaATM.sacar(1500.00));
	}
	
	@Test
	void whenSolicitoSaqueEHardwareFalhaEntaoLancaFalhaNoEmissorDeNotaException() throws FalhaNoLeitorDeEnvelopeException {		
		try {
			caixaATM.logar();
			
			mockHardware.setValorASerLidoDoEnvelope(100.00); 		// Prepara saldo
			caixaATM.depositar();
			
			mockHardware.setFalha(FalhasHW.EMISSOR_DE_NOTAS);
			caixaATM.sacar(50.00);
			fail();
		}
		catch (FalhaNaEmissaoDasNotasException ex) { }		
	}
	
	private MockHardware mockHardware;
	private MockServicoRemoto mockServicoRemoto;	
	private CaixaEletronico caixaATM;
}
