package com.ficticia.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ficticia.dscatalog.entities.Product;
import com.ficticia.dscatalog.tests.Factory;

// Boa pratica: caso em que a classe original contenha método estático
// Fazer classe de testes no mesmo pacote em que a classe original a ser testada

@DataJpaTest
public class ProductRepositoryTests {

	private long existingID;
	private long nonExistingID;
	private Product product;
	private long countTotalProducts;
	
	// Injetar repositorio para ter capacidade de deletar
	@Autowired
	private ProductRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		existingID = 1L;
		nonExistingID = 999L;
		product = Factory.createProduct();
		countTotalProducts = 25L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(existingID);

		Optional<Product> result = repository.findById(existingID);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingID);
		});
	}

	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		//Product product = new Product(...)
		//Product product = Factory.createProduct();
		product.setId(null); //garantir que é nulo
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}



}
