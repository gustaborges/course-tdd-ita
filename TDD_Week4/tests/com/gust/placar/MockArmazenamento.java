package com.gust.placar;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gust.armazenamento.IArmazenamento;
import com.gust.user.*;

public class MockArmazenamento implements IArmazenamento {
	
	Map<String, User> userBD = new HashMap<String, User>();
	
	private User getUser(String username) throws UserNotFoundException {
		if (userBD.containsKey(username))
			return userBD.get(username);
		
		throw new UserNotFoundException("User not found.");
	}
	
	public void addUser(String username) throws UserAlreadyExistsException {
		if (userBD.containsKey(username))
			throw new UserAlreadyExistsException("User already exists.");
		
		userBD.put(username, new User(username));
	}	
	
	
	@Override
	public void addPontos(String username, TipoPonto tipoPonto, int pontos) throws UserNotFoundException {
		getUser(username).addPontos(tipoPonto, pontos);
	}


	@Override
	public int getPontos(String username, TipoPonto tipoPonto) throws UserNotFoundException {
		return getUser(username).getPontos(tipoPonto);
	}

	@Override
	public List<TipoPonto> getTiposDePontosJaRegistrados(String username) throws UserNotFoundException {
		List<TipoPonto> listaTiposPonto = new ArrayList<TipoPonto>();
		User user = getUser(username);	
		boolean possuiPontoRegistrado;
		
		for (TipoPonto tipoPonto : TipoPonto.values()) {
			possuiPontoRegistrado = user.getPontos(tipoPonto) != 0;			
			if (possuiPontoRegistrado)
				listaTiposPonto.add(tipoPonto);
		}
		return listaTiposPonto;
	}

	@Override
	public List<String> getUsuariosPorTipoPonto(TipoPonto tipoPonto) {
		List<String> listaUsers = new ArrayList<String>();
		for (String username : userBD.keySet()) {
			try
			{
				if (getUser(username).getPontos(tipoPonto) > 0)
					listaUsers.add(username);
			}
			catch (UserNotFoundException e) { }
		}
		return listaUsers;
	}



}
