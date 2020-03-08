package com.gust.armazenamento;

import java.util.List;
import com.gust.placar.TipoPonto;
import com.gust.user.UserNotFoundException;

public interface IArmazenamento {
	public void addPontos(String username, TipoPonto tipoPonto, int pontos) throws UserNotFoundException;
	public int getPontosPorTipo(String username, TipoPonto tipoPonto) throws UserNotFoundException;
	public List<TipoPonto> getTiposDePontoJaRegistrados(String username) throws UserNotFoundException;
	public List<String> getUsuariosPorTipoPonto(TipoPonto tipoPonto);
}
