package com.gust.user;
import com.gust.placar.TipoPonto;

public class User {
	
	// Atributos
	private String username;
	
	private int pontosEstrela = 0;
	private int pontosComentario = 0;
	private int pontosCurtida = 0;
	private int pontosMoeda = 0;
	private int pontosTopico = 0;
	
	/*
	 * Construtor
	 */
	public User(String username) {
		setUsername(username);
	}
	
	/*------------------------------------------------------
	 *  Getters e Setters
	 */
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public void addPontos(TipoPonto tipo, int pontos) {
		switch(tipo) {
			case ESTRELA 	: this.pontosEstrela 	+= pontos; break;
			case CURTIDA 	: this.pontosCurtida 	+= pontos; break;
			case MOEDA	 	: this.pontosMoeda 		+= pontos; break;
			case TOPICO	 	: this.pontosTopico 	+= pontos; break;
			case COMENTARIO : this.pontosComentario += pontos; break;
			default 		: throw new IllegalArgumentException("Tipo invalido");
		}
	}
	
	public int getPontos(TipoPonto tipo) {
		switch(tipo) {
			case ESTRELA 	: return this.pontosEstrela;
			case CURTIDA 	: return this.pontosCurtida;
			case MOEDA	 	: return this.pontosMoeda;
			case TOPICO	 	: return this.pontosTopico;
			case COMENTARIO : return this.pontosComentario;
			default 		: throw new IllegalArgumentException("Tipo invalido");
		}
	}

}
