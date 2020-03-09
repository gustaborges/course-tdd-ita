package com.gust.armazenamento;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gust.placar.TipoPonto;
import com.gust.user.UserNotFoundException;

class TestArmazenamento {
	
	public TestArmazenamento() {
		String modelXmlPath = thisClass.getAbsolutePath() + "\\pontuacoesModelo.xml";
		String testXmlPath =  thisClass.getAbsolutePath() + "\\pontuacoesTeste.xml";
		modelXMLFile = new File(modelXmlPath);
		testXMLFile = new File(testXmlPath);
	}
	
	@BeforeEach
	void setUp() throws IOException
	{
		Files.copy(modelXMLFile.toPath(), testXMLFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		armazenamento = new ArmazenamentoXML(testXMLFile);
	}
	/*
	 * Responsabilidade ArmazenaRecebimentoPontos
	 * */
	@Test
	void addPontos_UsuarioZeradoDePontosRecebeDezPontosDeUmTipo_ThenUsuarioTemDezPontosSomenteDoTipo() throws UserNotFoundException, FalhaArmazenamentoException {
		armazenamento.addPontos(user1, TipoPonto.ESTRELA, 5);
		assertEquals(5, armazenamento.getPontos(user1, TipoPonto.ESTRELA));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.COMENTARIO));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.CURTIDA));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.MOEDA));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.TOPICO));
	}
	
	@Test
	void addPontos_UsuarioRecebeDezPontosDeUmTipoEmChamadasSeparadas_ThenUsuarioTemDezPontosDoTipo() throws UserNotFoundException, FalhaArmazenamentoException {
		armazenamento.addPontos(user1, TipoPonto.ESTRELA, 5);
		armazenamento.addPontos(user1, TipoPonto.ESTRELA, 5);
		assertEquals(10, armazenamento.getPontos(user1, TipoPonto.ESTRELA));
	}
	
	@Test
	void addPontos_UsuarioRecebePontosDeVariosTipos_ThenUsuarioTemRespectivosPontos() throws UserNotFoundException, FalhaArmazenamentoException {
		armazenamento.addPontos(user1, TipoPonto.ESTRELA, 5);
		armazenamento.addPontos(user1, TipoPonto.ESTRELA, 5);
		armazenamento.addPontos(user1, TipoPonto.MOEDA, 3);
		armazenamento.addPontos(user1, TipoPonto.TOPICO, 3);
		armazenamento.addPontos(user1, TipoPonto.MOEDA, 2);
		armazenamento.addPontos(user1, TipoPonto.CURTIDA, 8);
		assertEquals(10, armazenamento.getPontos(user1, TipoPonto.ESTRELA));
		assertEquals(3, armazenamento.getPontos(user1, TipoPonto.TOPICO));
		assertEquals(5, armazenamento.getPontos(user1, TipoPonto.MOEDA));
		assertEquals(8, armazenamento.getPontos(user1, TipoPonto.CURTIDA));
	}
	
	@Test
	void addPontos_PassoPontoZerado_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.addPontos(user1, TipoPonto.ESTRELA, 0));
	}
	
	@Test
	void addPontos_PassoPontoNegativo_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.addPontos(user1, TipoPonto.ESTRELA, -1));
	}
	
	@Test
	void addPontos_PassoTipoNulo_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.addPontos(user1, null, 1));
	}
	
	@Test
	void addPontos_PassoUserNulo_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.addPontos(null, TipoPonto.CURTIDA, 1));
	}
	
	@Test
	void addPontos_PassoUserWhiteSpace_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.addPontos("  ", TipoPonto.CURTIDA, 1));
	}
	
	@Test
	void addPontos_PassoUserVazio_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.addPontos("", TipoPonto.CURTIDA, 1));
	}
	
	/*
	 * Responsabilidade Consulta Pontos Do Usuario Por Tipo De Ponto
	 * */
	@Test
	void getPontos_UsuarioNaoTemPontosRegistrados_ThenUsuarioEhZeradoDePontos() throws UserNotFoundException, FalhaArmazenamentoException {
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.ESTRELA));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.COMENTARIO));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.CURTIDA));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.MOEDA));
		assertEquals(0, armazenamento.getPontos(user1, TipoPonto.TOPICO));
	}
	
	@Test
	void getPontos_PassoUserVazio_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos("", TipoPonto.CURTIDA));
	}
	
	@Test
	void getPontos_PassoUserWhiteSpace_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos(" ", TipoPonto.CURTIDA));
	}
	
	@Test
	void getPontos_PassoUserNulo_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos(null, TipoPonto.CURTIDA));
	}
	@Test
	void getPontos_PassoTipoNulo_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos(user1, null));
	}
	
	/*
	 * Responsabilidade Consulta Tipos De Pontos Registrados para Um Usuário
	 * */
	@Test
	void getTiposDePontosJaRegistrados_UsuarioNaoTemPontosRegistrados_ThenResultadoEhListaVazia() throws UserNotFoundException, FalhaArmazenamentoException {
		assertEquals(0, armazenamento.getTiposDePontosJaRegistrados(user1).size());
	}
	
	@Test
	void getTiposDePontosJaRegistrados_UsuarioTemApenasUmTipoPonto_ThenListaContemTipoPonto() throws UserNotFoundException, FalhaArmazenamentoException {
		armazenamento.addPontos(user1, TipoPonto.CURTIDA, 1);
		List<TipoPonto> listaTipos = armazenamento.getTiposDePontosJaRegistrados(user1);
		assertEquals(1, listaTipos.size());
		assertEquals(TipoPonto.CURTIDA, listaTipos.get(0));
	}
	
	@Test
	void getTiposDePontosJaRegistrados_UsuarioTemMaisDeUmTipoPonto_ThenListaContemTiposPontos() throws UserNotFoundException, FalhaArmazenamentoException {
		armazenamento.addPontos(user1, TipoPonto.CURTIDA, 1);
		armazenamento.addPontos(user1, TipoPonto.MOEDA, 3);
		armazenamento.addPontos(user1, TipoPonto.TOPICO, 4);
		List<TipoPonto> listaTipos = armazenamento.getTiposDePontosJaRegistrados(user1);
		assertEquals(3, listaTipos.size());
		assertEquals(TipoPonto.CURTIDA, listaTipos.get(0));
		assertEquals(TipoPonto.MOEDA, listaTipos.get(1));
		assertEquals(TipoPonto.TOPICO, listaTipos.get(2));
	}
	
	@Test
	void getTiposDePontosJaRegistrados_PassoUserVazio_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos("", TipoPonto.CURTIDA));
	}
	
	@Test
	void getTiposDePontosJaRegistrados_PassoUserWhiteSpace_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos(" ", TipoPonto.CURTIDA));
	}
	
	@Test
	void getTiposDePontosJaRegistrados_PassoUserNulo_ThenIllegalArgumentExceptionEhLancada() {
		assertThrows(IllegalArgumentException.class,() -> armazenamento.getPontos(null, TipoPonto.CURTIDA));
	}
	/*
	 * Responsabilidade Consulta Usuários por Tipo de Ponto
	 *
	 **/
	@Test
	void getUsuariosPorTipoPonto_ApenasUmUserPossuiTipoPonto_ThenResultadoContemApenasUser() throws UserNotFoundException, FalhaArmazenamentoException {
		armazenamento.addPontos(user1, TipoPonto.CURTIDA, 5);
		List<String> listaUsers = armazenamento.getUsuariosPorTipoPonto(TipoPonto.CURTIDA);
		assertEquals(1, listaUsers.size());
		assertTrue(listaUsers.contains(user1));
	}
	
	@Test
	void getUsuariosPorTipoPonto_NenhumUserTemPontoRegistrado_ThenResultadoEhListaVazia() throws UserNotFoundException, FalhaArmazenamentoException {
		assertEquals(0, armazenamento.getUsuariosPorTipoPonto(TipoPonto.CURTIDA).size());
		assertEquals(0, armazenamento.getUsuariosPorTipoPonto(TipoPonto.COMENTARIO).size());
		assertEquals(0, armazenamento.getUsuariosPorTipoPonto(TipoPonto.ESTRELA).size());
		assertEquals(0, armazenamento.getUsuariosPorTipoPonto(TipoPonto.MOEDA).size());
		assertEquals(0, armazenamento.getUsuariosPorTipoPonto(TipoPonto.TOPICO).size());
	}
	
	@Test
	void getUsuariosPorTipoPonto_MaisDeUmUserPossuiTipoPonto_ThenResultadoContemUsersComTipoPonto() throws FalhaArmazenamentoException, UserNotFoundException  {
		armazenamento.addPontos(user1, TipoPonto.CURTIDA, 5);
		armazenamento.addPontos(user1, TipoPonto.CURTIDA, 2);
		armazenamento.addPontos(user3, TipoPonto.CURTIDA, 1);
		armazenamento.addPontos(user2, TipoPonto.CURTIDA, 8);
		List<String> listaUsers = armazenamento.getUsuariosPorTipoPonto(TipoPonto.CURTIDA);
		assertEquals(3, listaUsers.size());
		assertTrue(listaUsers.contains(user1));
		assertTrue(listaUsers.contains(user2));
		assertTrue(listaUsers.contains(user3));
	}
	
	@Test
	void getUsuariosPorTipoPonto_PassoTipoNulo_ThenThrowIllegalArgumentException()  {
		assertThrows(IllegalArgumentException.class, () -> armazenamento.getUsuariosPorTipoPonto(null));
	}
	
	String user1 = "ricard", user2 = "jujubs", user3 = "mario", user4="henrique", user5="peter";
	final File thisClass = new File(TestArmazenamento.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	File modelXMLFile, testXMLFile;
	ArmazenamentoXML armazenamento;	
}
