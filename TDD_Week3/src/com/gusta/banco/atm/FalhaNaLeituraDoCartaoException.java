package com.gusta.banco.atm;

@SuppressWarnings("serial")
public class FalhaNaLeituraDoCartaoException extends Exception {
	
	public FalhaNaLeituraDoCartaoException(String message) {
		super(message);
	}
}
