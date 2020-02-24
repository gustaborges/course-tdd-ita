package com.gusta.banco.contas;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContaCorrenteTest {
	
	@BeforeEach
	void setUp() {
		conta = new ContaCorrente("Joao");
	}
	
	@Test
	void whenCrioNovaContaCorrenteEntaoEhGeradoNumeroDaContaValido() {
		String numeroConta = conta.getNumeroDaConta();
		Integer.parseUnsignedInt(numeroConta);
	}
	
	@Test
	void whenPassoNomeVazioEntaoIllegalArgumentExceptionEhLancado() {
		try {
			conta = new ContaCorrente("");
		}
		catch (IllegalArgumentException e) {}
	}
	
	@Test
	void whenPassoNomeNuloEntaoIllegalArgumentExceptionEhLancado() {
		try {
			conta = new ContaCorrente(null);
		}
		catch (IllegalArgumentException e) {}
	}
	
	@Test
	void whenSaldoEh100ESaco50EntaoSaldoEh50() throws SaqueMaiorQueSaldoException {
		conta.deposita(100.00);
		conta.saca(50.00);
		assertTrue(conta.getSaldo() == 50.00);
	}
	
	@Test
	void whenSaqueMaiorQueSaldoEntaoSaqueMaiorQueSaldoExceptionEhLancado() {
		try {
			conta.saca(100.00);
		}
		catch (SaqueMaiorQueSaldoException ex) {}
	}
	
	private ContaCorrente conta;
}
