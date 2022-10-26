package com.main.sistema_moedas.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_STRING = "Usuário não encontrado";

	public UserNotFoundException() {
		super(MESSAGE_STRING);
	}

}
