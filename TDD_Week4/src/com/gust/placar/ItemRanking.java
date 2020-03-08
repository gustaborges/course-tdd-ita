package com.gust.placar;

public class ItemRanking implements Comparable<ItemRanking> {
	private String username;
	private Integer pontuacao;
	
	public ItemRanking(String username, int pontuacao){
		this.username = username;
		this.pontuacao = pontuacao;
	}

	@Override
	public int compareTo(ItemRanking other) {
		// > 0 se posicao do this maior que outro
		// < 0 se posicao do this menor que outro
		//   0 se posicao do this igual que outro
		int posicao = 0;
		if (this.pontuacao < other.pontuacao)
		{
			posicao = 1;
		}
		else if (this.pontuacao > other.pontuacao)
		{
			posicao = -1;
		}
		return (posicao == 0) ? this.username.compareTo(other.username) : posicao;
	}

	@Override
	public boolean equals(Object outro) {
		if (outro == this) return true;
		if (!(outro instanceof ItemRanking)) return false;
		
		ItemRanking outroItem = (ItemRanking)outro;
		
		return this.username.equals(outroItem.username);
	}

	public Integer getPontuacao() {
		return this.pontuacao;
	}

	public String getUsername() {
		return this.username;
	}
}
