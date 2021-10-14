package com.ficticia.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ficticia.dscatalog.entities.Product;

// Boa pratica: caso em que a classe original contenha método estático
// Fazer classe de testes no mesmo pacote em que a classe original a ser testada

@DataJpaTest
public class ProductRepositoryTests {
	
	//Injetar repositorio para ter capacidade de deletar
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		long existingID = 1L;
		
		repository.deleteById(existingID);
		
		Optional<Product> result = repository.findById(existingID);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		
		long nonExistingID = 999L;
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingID);
		});
	}
}
