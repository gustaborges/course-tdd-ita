package com.gust.placar;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gust.user.UserAlreadyExistsException;
import com.gust.user.UserNotFoundException;

class TestPlacar {
	private MockArmazenamento mockArmazenamento;
	private Placar placar;
	private String username1 = "jujubs";
	
	@BeforeEach
	void setUp() {
		mockArmazenamento = new MockArmazenamento();
		placar = new Placar(mockArmazenamento);
	}
	/*
	 * Responsabilidade: RegistraPonto 
	 */
	@Test
	void registraPonto_WhenRegistraUmaMoeda_ThenUsuarioPossuiUmaMoeda() throws UserAlreadyExistsException, UserNotFoundException {
		mockArmazenamento.addUser(username1);
		// Registra o ponto
		placar.registraPonto(username1, TipoPonto.ESTRELA, 1);
		
		var relatorio = placar.consultaPontos(username1);
		assertEquals(1, relatorio.get(TipoPonto.ESTRELA));
	}
	
	@Test
	void registraPonto_WhenRegistraDuasMoedasNaMesmaChamada_ThenUsuarioPossuiDuasMoedas() throws UserAlreadyExistsException, UserNotFoundException {
		mockArmazenamento.addUser(username1);
		
		placar.registraPonto(username1, TipoPonto.MOEDA, 2);
		
		var relatorio = placar.consultaPontos(username1);
		assertEquals(2, relatorio.get(TipoPonto.MOEDA));
	}
	
	@Test
	void registraPonto_WhenRegistraDuasMoedasEmChamadasSeparadas_ThenUsuarioPossuiDuasMoedas() throws UserAlreadyExistsException, UserNotFoundException {
		mockArmazenamento.addUser(username1);
		
		placar.registraPonto(username1, TipoPonto.MOEDA, 1);
		placar.registraPonto(username1, TipoPonto.MOEDA, 1);
		
		var relatorio = placar.consultaPontos(username1);
		assertEquals(2, relatorio.get(TipoPonto.MOEDA));
	}
	
	@Test
	void registraPonto_WhenRegistraCincoMoedasTresEstrelas_ThenUsuarioPossuiCincoMoedasETresEstrelas() throws UserNotFoundException, UserAlreadyExistsException {
		mockArmazenamento.addUser(username1);
		
		placar.registraPonto(username1, TipoPonto.MOEDA, 5);
		placar.registraPonto(username1, TipoPonto.ESTRELA, 4);
		
		var relatorio = placar.consultaPontos(username1);
		assertEquals(5, relatorio.get(TipoPonto.MOEDA));
		assertEquals(4, relatorio.get(TipoPonto.ESTRELA));
	}
	
	@Test
	void registraPonto_WhenUsuarioNulo_ThenShouldThrowIllegalArgumentException() throws UserAlreadyExistsException {
		assertThrows(IllegalArgumentException.class, 
								() -> placar.registraPonto(null, TipoPonto.MOEDA, 5));	
	}
	
	@Test
	void registraPonto_WhenUsuarioNaoExiste_ThenShouldThrowUserNotFoundException() throws UserAlreadyExistsException {
		assertThrows(UserNotFoundException.class, 
								() -> placar.registraPonto(username1, TipoPonto.MOEDA, 5));	
	}
	
	@Test
	void registraPonto_WhenTipoPontoNulo_ThenShouldThrowIllegalArgumentException() throws UserAlreadyExistsException {
		mockArmazenamento.addUser(username1);
		assertThrows(IllegalArgumentException.class, 
								() -> placar.registraPonto(username1, null, 5));	
	}
	
	@Test
	void registraPonto_WhenPontoZero_ThenShouldThrowIllegalArgumentException() throws UserAlreadyExistsException {
		mockArmazenamento.addUser(username1);
		assertThrows(IllegalArgumentException.class, 
								() -> placar.registraPonto(username1, TipoPonto.MOEDA, 0));	
	}
	
	@Test
	void registraPonto_WhenPontoNegativo_ThenShouldThrowIllegalArgumentException() throws UserAlreadyExistsException {
		mockArmazenamento.addUser(username1);
		assertThrows(IllegalArgumentException.class, 
								() -> placar.registraPonto(username1, TipoPonto.MOEDA, -1));	
	}
	
	/*
	 * Responsabilidade: ConsultaPontos
	 */
	@Test
	void consultaPontos_WhenUsuarioNaoTemPontosDeUmTipo_ThenResultadoNaoIncluiOTipo() throws UserAlreadyExistsException, UserNotFoundException {
		mockArmazenamento.addUser(username1);
		Map<TipoPonto, Integer> pontosRegistrados = placar.consultaPontos(username1);
		assertEquals(null, pontosRegistrados.get(TipoPonto.CURTIDA));
	}
	
	@Test
	void consultaPontos_WhenUsuarioTemPontosDeUmTipo_ThenResultadoIncluiOTipo() throws UserAlreadyExistsException, UserNotFoundException {
		mockArmazenamento.addUser(username1);		
		placar.registraPonto(username1, TipoPonto.CURTIDA, 1);	
		Map<TipoPonto, Integer> pontosRegistrados = placar.consultaPontos(username1);
		assertEquals(1, pontosRegistrados.get(TipoPonto.CURTIDA));
	}
	
	@Test
	void consultaPontos_WhenUserNulo_ThenShouldThrowIllegalArgumentException() throws UserAlreadyExistsException, UserNotFoundException {
		assertThrows(IllegalArgumentException.class, 
					 () -> placar.consultaPontos(null));
	}
	
	/*
	 * Responsabilidade: ConsultaRankingTipoPonto
	 */
}
