package com.gusta.banco.atm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.gusta.banco.contas.IServicoRemoto;

class CaixaEletronicoTest {
	
	@Test
	void whenAutenticacaoBemSucedidaEntaoDevolveMensagemExito() {
		// Prepara o mock Hardware
		MockHardware mockHardware = new MockHardware();
		mockHardware.setNumeroDaConta("10000");
		// Prepara o mock ServicoRemoto
		IServicoRemoto mockServicoRemoto = new MockServicoRemoto();
		//
		CaixaEletronico caixaATM = new CaixaEletronico(mockHardware, mockServicoRemoto);
		//
		assertEquals("Usuário Autenticado", caixaATM.logar());
	}
}
