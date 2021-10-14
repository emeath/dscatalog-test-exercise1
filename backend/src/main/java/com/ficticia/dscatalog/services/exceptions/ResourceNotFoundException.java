package com.ficticia.dscatalog.services.exceptions;

// extends => herdar de uma classe
// em java podemos herdar de RuntimeException ou Exception
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg); // repassando o argumento para o construtor da classe mãe
	}
}
