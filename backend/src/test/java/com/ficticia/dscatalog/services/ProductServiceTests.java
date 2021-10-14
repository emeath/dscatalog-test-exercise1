package com.ficticia.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ficticia.dscatalog.repositories.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	//Este elemento ProductService depende de ProductRepository e 
	//Vamos mockar as dependencias para realizar o teste de unidade
	
	@Mock
	private ProductRepository repository;
	
	private long existingID;
	private long nonExistingID;
	
	@BeforeEach
	void setUp() throws Exception {
		existingID = 1L;
		nonExistingID = 1000L;
		
		//Configurar o comportamento do deleteById do repository mockado quando ele for chamado
		Mockito.doNothing().when(repository).deleteById(existingID);
		
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingID);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		//Pode ou não colocar nada ou usar a assertion abaixo
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingID);
		});
		
		// verify(OBJETO_MOCKADO) obtem a interface do OBJETO_MOCADO
		// É analisado se o método do objeto mockado foi chamado pelo service acima
		Mockito.verify(repository).deleteById(existingID);
		
		//Para testar se o metodo foi chamado n vezes
		//Mockito.verify(repository, Mockito.times(1)).deleteById(existingID);
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			service.delete(nonExistingID);
		});
		
		Mockito.verify(repository).deleteById(nonExistingID);
	}
}
