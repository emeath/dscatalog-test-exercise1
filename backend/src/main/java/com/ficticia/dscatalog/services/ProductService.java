package com.ficticia.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ficticia.dscatalog.dto.CategoryDTO;
import com.ficticia.dscatalog.dto.ProductDTO;
import com.ficticia.dscatalog.entities.Category;
import com.ficticia.dscatalog.entities.Product;
import com.ficticia.dscatalog.repositories.CategoryRepository;
import com.ficticia.dscatalog.repositories.ProductRepository;
import com.ficticia.dscatalog.services.exceptions.DatabaseException;
import com.ficticia.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	// para usar com o getOne
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = repository.findAll(pageable);
		return list.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
//		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		// No caso de produtos, teremos mais dados para definir (set)
		//entity.setName(dto.getName());
		
		// nao vamos colocar set, set, set, set... vamos criar um metodo auxiliar
		
		copyDTOtoEntity(dto, entity);
		
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
//			entity.setName(dto.getName());
			copyDTOtoEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException f) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	//metodo auxiliar, para ser usado apenas nessa clase
	private void copyDTOtoEntity(ProductDTO dto, Product entity) {
		
		//OBS: NAO ATUALIZAMOS O ID NA HORA DE ATUALIZAR E NAO O INFORMAMOS NA HORA DE INSERIR
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		// DTO pode carregar também as categorias
		entity.getCategories().clear(); // limpar categorias que podem estar na entidade
		for (CategoryDTO catDTO : dto.getCategories()) {
			//instanciar entidade sem tocar no banco de dados --> usar getOne
			Category category = categoryRepository.getOne(catDTO.getId()); // voce nao eh obrigado a usar this o tempo todo
			entity.getCategories().add(category);
		}
		
		
	}
}
