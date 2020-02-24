package com.gusta.banco.contas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContaCorrenteTest {
	
	@BeforeEach
	void setUp() {
		conta = new ContaCorrente("Joao");
		System.out.println("foi");
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
	private ContaCorrente conta;
}
