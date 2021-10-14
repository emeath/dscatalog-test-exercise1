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
import com.ficticia.dscatalog.entities.Category;
import com.ficticia.dscatalog.repositories.CategoryRepository;
import com.ficticia.dscatalog.services.exceptions.DatabaseException;
import com.ficticia.dscatalog.services.exceptions.ResourceNotFoundException;

// Essa annotation registra a classe como um componente que participa da injeção de dependencia controlada pelo Spring
// Frameworks modernos possuem mecanimos de automatização de injeção de dependencias

@Service
public class CategoryService {

	// criando dependencia
	@Autowired // o spring faz a injeção de dependencia para voce
	private CategoryRepository repository; // te disponibilza varios e varios metodos prontos

	// poderia ser qualquer nome em vez de findAll

	// annotation implementa transação de banco de dados nesse método do serviço
	// readOnly define para não ocorrer lock no bd
	// Dessa forma abaixo, a entidade está navegando até chegar no controller rest.
	// Não é o nosso objetivo
	// @Transactional(readOnly = true)
	// public List<Category> findAll() {
	// return repository.findAll();
	// }

	// Para garantir que a entidade nao chegue no controller rest. Quem chegará será
	// o DTO
	// implementação didatica

	// public List<CategoryDTO> findAll() {
	// List<Category> list = repository.findAll();
	//
	// List<CategoryDTO> listDto = new ArrayList<>();
	// for (Category cat : list) {
	// listDto.add(new CategoryDTO(cat));
	// }
	//
	// return listDto;
	// }

	// Com expressão lambda para ficar mais enxuto
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);

		// recurso da java 8.0 -> possibilidade de trabalhar com função de ordem maior.
		// Programação funcional. Expresão lambda
		// aplica função para cada elemento da lista... Semelhante ao javascript
		// transformando uma lista em outra
		// transformando lista para stream e depois reconvertemos para lista novamente
//		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList()); // para retornar findall sem paginacao
		return list.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		// objeto optional veio no java 8. É uma abordagem para trabalhar com valores
		// nulos.
		// a busca nunca sera nulo. Sera Optional. Nela podera conter Category ou não
		Optional<Category> obj = repository.findById(id);
//		Category entity = obj.get();
		// tentar acessar o category, se ele não existir, lançar exceção personalizada
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		// Devemos retornar o DTO!!! nao a entity
		return new CategoryDTO(entity);

	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		entity.setName(dto.getName());
		// nao definimos o id. Enviamos nullo e o banco de dados que o define.

		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {

		// e se o id nao existir no banco de dados? O repository estoura uma exceção

		// Como atualizar registro na JPA
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found " + id);
		}

	}

	// nao vamos colocar transaction para que possamos capturar exception do banco
	public void delete(Long id) {
		// se tentar deletar id que nao existe?
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException f) {
			// integridade referencial do banco
			throw new DatabaseException("Integrity violation");
		}

	}

}
