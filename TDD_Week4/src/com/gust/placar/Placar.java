package com.gust.placar;

import java.util.HashMap;
import java.util.Map;

import com.gust.armazenamento.IArmazenamento;
import com.gust.user.UserNotFoundException;

public class Placar {

	private IArmazenamento armazenamento;	
	
	public Placar(IArmazenamento armazenamento) {
		setArmazenamento(armazenamento);
	}

	public void registraPonto(String username, TipoPonto tipoPonto, int quantidade) throws UserNotFoundException {
		if (username == null) throw new IllegalArgumentException("Usuário inválido.");
		if (tipoPonto == null) throw new IllegalArgumentException("Argumento nulo.");
		if (quantidade <= 0) throw new IllegalArgumentException("Quantidade de pontos deve ser maior que zero.");
		
		armazenamento.addPontos(username, tipoPonto, quantidade);
	}
	
	public Map<TipoPonto, Integer> consultaPontos(String username) throws UserNotFoundException {
		if (username == null) throw new IllegalArgumentException("Usuário inválido.");
		
		HashMap<TipoPonto, Integer> relacaoDePontos = new HashMap<TipoPonto, Integer>();
		for (TipoPonto tipo : armazenamento.getTiposDePontoJaRegistrados(username)) {
			int totalPontos = armazenamento.getPontosPorTipo(username, tipo);
			if (totalPontos > 0)
				relacaoDePontos.put(tipo, totalPontos);
		}
		return relacaoDePontos;
	}
	
	// Getters e Setters
	private void setArmazenamento(IArmazenamento armazenamento) {
		this.armazenamento = armazenamento;
	}	
}

