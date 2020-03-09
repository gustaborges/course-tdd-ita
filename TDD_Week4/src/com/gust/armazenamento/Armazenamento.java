package com.gust.armazenamento;

import java.util.List;
import com.gust.placar.TipoPonto;
import com.gust.user.UserNotFoundException;

public abstract class Armazenamento {
	public abstract void addPontos(String username, TipoPonto tipoPonto, int pontos) throws UserNotFoundException, FalhaArmazenamentoException;
	public abstract int getPontos(String username, TipoPonto tipoPonto) throws UserNotFoundException, FalhaArmazenamentoException;
	public abstract List<TipoPonto> getTiposDePontosJaRegistrados(String username) throws UserNotFoundException, FalhaArmazenamentoException;
	public abstract List<String> getUsuariosPorTipoPonto(TipoPonto tipoPonto) throws FalhaArmazenamentoException;
}
