package com.gust.placar;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gust.armazenamento.FalhaArmazenamentoException;
import com.gust.placar.MockArmazenamento.Falha;
import com.gust.user.UserAlreadyExistsException;
import com.gust.user.UserNotFoundException;

class TestPlacar {
	private MockArmazenamento mockArmazenamento;
	private Placar placar;
	private String username1 = "user1", username2 = "user2", username3 = "user3", username4 = "user4",
			username5 = "user5";

	@BeforeEach
	void setUp() throws UserAlreadyExistsException {
		mockArmazenamento = new MockArmazenamento();
		placar = new Placar(mockArmazenamento);

		mockArmazenamento.addUser(username1);
		mockArmazenamento.addUser(username2);
		mockArmazenamento.addUser(username3);
		mockArmazenamento.addUser(username4);
		mockArmazenamento.addUser(username5);
	}

	/*
	 * Responsabilidade: RegistraPonto
	 */
	@Test
	void registraPonto_WhenRegistraUmaMoeda_ThenUsuarioPossuiUmaMoeda()
			throws UserNotFoundException, FalhaArmazenamentoException {
		placar.registraPonto(username1, TipoPonto.ESTRELA, 1);

		var relatorio = placar.consultaPontos(username1);
		assertEquals(1, relatorio.get(TipoPonto.ESTRELA));

	}

	@Test
	void registraPonto_WhenRegistraDuasMoedasNaMesmaChamada_ThenUsuarioPossuiDuasMoedas()
			throws UserNotFoundException, FalhaArmazenamentoException {
		placar.registraPonto(username1, TipoPonto.MOEDA, 2);

		var relatorio = placar.consultaPontos(username1);
		assertEquals(2, relatorio.get(TipoPonto.MOEDA));

	}

	@Test
	void registraPonto_WhenRegistraDuasMoedasEmChamadasSeparadas_ThenUsuarioPossuiDuasMoedas()
			throws UserNotFoundException, FalhaArmazenamentoException {

		placar.registraPonto(username1, TipoPonto.MOEDA, 1);
		placar.registraPonto(username1, TipoPonto.MOEDA, 1);

		var relatorio = placar.consultaPontos(username1);
		assertEquals(2, relatorio.get(TipoPonto.MOEDA));

	}

	@Test
	void registraPonto_WhenRegistraCincoMoedasTresEstrelas_ThenUsuarioPossuiCincoMoedasETresEstrelas()
			throws UserNotFoundException, FalhaArmazenamentoException {

		placar.registraPonto(username1, TipoPonto.MOEDA, 5);
		placar.registraPonto(username1, TipoPonto.ESTRELA, 4);

		var relatorio = placar.consultaPontos(username1);
		assertEquals(5, relatorio.get(TipoPonto.MOEDA));
		assertEquals(4, relatorio.get(TipoPonto.ESTRELA));

	}

	@Test
	void registraPonto_WhenUsuarioNulo_ThenShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> placar.registraPonto(null, TipoPonto.MOEDA, 5));
	}

	@Test
	void registraPonto_WhenUsuarioNaoExiste_ThenShouldThrowUserNotFoundException() {
		assertThrows(UserNotFoundException.class, () -> placar.registraPonto("userinexiste", TipoPonto.MOEDA, 5));
	}

	@Test
	void registraPonto_WhenTipoPontoNulo_ThenShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> placar.registraPonto(username1, null, 5));
	}

	@Test
	void registraPonto_WhenPontoZero_ThenShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> placar.registraPonto(username1, TipoPonto.MOEDA, 0));

	}

	@Test
	void registraPonto_WhenPontoNegativo_ThenShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> placar.registraPonto(username1, TipoPonto.MOEDA, -1));
	}

	/*
	 * Responsabilidade: ConsultaPontos
	 */
	@Test
	void consultaPontos_WhenUsuarioNaoTemPontosDeUmTipo_ThenResultadoNaoIncluiOTipo()
			throws UserNotFoundException, FalhaArmazenamentoException {

		Map<TipoPonto, Integer> pontosRegistrados = placar.consultaPontos(username1);
		assertEquals(null, pontosRegistrados.get(TipoPonto.CURTIDA));

	}

	@Test
	void consultaPontos_WhenUsuarioTemPontosDeUmTipo_ThenResultadoIncluiOTipo()
			throws UserNotFoundException, FalhaArmazenamentoException {

		placar.registraPonto(username1, TipoPonto.CURTIDA, 1);
		Map<TipoPonto, Integer> pontosRegistrados = placar.consultaPontos(username1);
		assertEquals(1, pontosRegistrados.get(TipoPonto.CURTIDA));

	}

	@Test
	void consultaPontos_WhenUserNulo_ThenShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> placar.consultaPontos(null));
	}

	/*
	 * Responsabilidade: ConsultaRankingTipoPonto
	 */
	@Test
	void consultaRankingTipoPonto_WhenApenasUmUserPossuiTipoPonto_ThenRankingContemApenasEle()
			throws UserNotFoundException, FalhaArmazenamentoException {

		placar.registraPonto(username1, TipoPonto.ESTRELA, 10);
		placar.registraPonto(username2, TipoPonto.CURTIDA, 2);

		ArrayList<ItemRanking> ranking = placar.consultaRanking(TipoPonto.ESTRELA);

		assertEquals(1, ranking.size());
		assertTrue(ranking.get(0).getUsername() == username1);

	}

	@Test
	void consultaRankingTipoPonto_WhenDoisUsersPossuemMesmaQuantidadePonto_ThenRankingContemUsersEmOrdemDecrescente()
			throws UserNotFoundException, FalhaArmazenamentoException {

		placar.registraPonto(username1, TipoPonto.ESTRELA, 1);
		placar.registraPonto(username2, TipoPonto.ESTRELA, 1);

		ArrayList<ItemRanking> ranking = placar.consultaRanking(TipoPonto.ESTRELA);

		assertEquals(2, ranking.size());
		assertTrue(isDecrescente(ranking));

	}

	@Test
	void consultaRankingTipoPonto_WhenDoisUsersPossuemPontoDoTipo_ThenRankingContemUsersEmOrdemDecrescente()
			throws UserNotFoundException, FalhaArmazenamentoException {

		placar.registraPonto(username1, TipoPonto.ESTRELA, 10);
		placar.registraPonto(username2, TipoPonto.ESTRELA, 2);

		ArrayList<ItemRanking> ranking = placar.consultaRanking(TipoPonto.ESTRELA);

		assertEquals(2, ranking.size());
		assertTrue(isDecrescente(ranking));

	}

	@Test
	void consultaRankingTipoPonto_WhenVariosUsersPossuemPontoDoTipo_ThenRankingContemUsersEmOrdemDecrescente()
			throws UserNotFoundException, FalhaArmazenamentoException {
		placar.registraPonto(username1, TipoPonto.ESTRELA, 20);
		placar.registraPonto(username2, TipoPonto.ESTRELA, 10);
		placar.registraPonto(username3, TipoPonto.ESTRELA, 50);
		placar.registraPonto(username4, TipoPonto.ESTRELA, 60);
		placar.registraPonto(username5, TipoPonto.ESTRELA, 20);

		ArrayList<ItemRanking> ranking = placar.consultaRanking(TipoPonto.ESTRELA);

		assertEquals(5, ranking.size());
		assertTrue(isDecrescente(ranking));

	}

	@Test
	void consultaRankingTipoPonto_WhenTipoDePontoNulo_ThenShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> placar.consultaRanking(null));
	}

	@Test
	void consultaRankingTipoPonto_WhenDependenciaLancaException_ThenShouldThrowFalhaArmazenamentoException() {
		mockArmazenamento.setFalha(Falha.FALHA_ARMAZENAMENTO);
		assertThrows(FalhaArmazenamentoException.class, () -> placar.consultaRanking(TipoPonto.MOEDA));
	}

	private boolean isDecrescente(ArrayList<ItemRanking> ranking) {
		Integer pontoAnterior = null;
		for (ItemRanking item : ranking) {
			if (pontoAnterior == null)
				pontoAnterior = item.getPontuacao();
			if (item.getPontuacao() > pontoAnterior)
				return false;
		}
		return true;
	}
}
