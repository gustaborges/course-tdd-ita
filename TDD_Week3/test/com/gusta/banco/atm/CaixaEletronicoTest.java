package com.gusta.banco.atm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gusta.banco.atm.MockHardware.FalhasHW;
import com.gusta.banco.atm.MockServicoRemoto.FalhasSR;

class CaixaEletronicoTest {
	
	@BeforeEach
	void setUp() {
		mockHardware = new MockHardware();
		mockServicoRemoto = new MockServicoRemoto();
	}
	
	@Test
	void whenAutenticacaoBemSucedidaEntaoDevolveMensagemExito() {
		mockHardware.setNumeroDaConta("10000");		
		CaixaEletronico caixaATM = new CaixaEletronico(mockHardware, mockServicoRemoto);		
		assertEquals("Usuário Autenticado", caixaATM.logar());
	}

	@Test
	void whenTentoAutenticarELeituraDoCartaoFalhaEntaoDevolveMensagemFalha() {	
		mockHardware.setFalha(FalhasHW.LEITURA_DO_CARTAO);		
		CaixaEletronico caixaATM = new CaixaEletronico(mockHardware, mockServicoRemoto);		
		assertEquals("Não foi possível autenticar o usuário", caixaATM.logar());
	}
	
	@Test
	void whenTentoAutenticarETentativaRecuperarContaFalhaEntaoDevolveMensagemFalha() {		
		mockHardware.setNumeroDaConta("1000");
		mockServicoRemoto.setFalha(FalhasSR.RECUPERACAO_DA_CONTA);
		CaixaEletronico caixaATM = new CaixaEletronico(mockHardware, mockServicoRemoto);		
		assertEquals("Não foi possível autenticar o usuário", caixaATM.logar());
	}
	
	private MockHardware mockHardware;
	private MockServicoRemoto mockServicoRemoto;	
}
