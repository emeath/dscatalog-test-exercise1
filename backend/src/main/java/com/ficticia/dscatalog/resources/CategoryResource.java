package com.ficticia.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ficticia.dscatalog.dto.CategoryDTO;
import com.ficticia.dscatalog.services.CategoryService;

// Recurso REST (Resource -- termo técnico)
// Implementa Controlador REST - veja diagrama do Nelio back e front end
// -> Entrega API para aplicação web ou mobile consumir informação

// Recurso relacionado a entidade seguir o padrao: EntidadeResource

// Isso é uma annotation: forma enxuta e simples de configurar algo no código. Te livra de usar if-else e implementar lógica do 0
// Essa lógica foi implementada pela equipe Spring que disponibilizou para nós essa infra
// Annotation é usada no momento da pré-processamento no momento da compilção compilação (flashcard)
// Framework Spring utilizada bastante annotation.

// @RequestMapping definir a rota (geralmente nomeada no plural) do recurso

// Estamos implementando um endpoint

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	// Encapsula uma resposta HTTP
	// payload = corpo da resposta
	
//  Dessa forma abaixo o controller esta retornando os dados que chegaram ate ele quando a entidade chegava nele. Nao é nosso objetivo
//	@GetMapping
//	public ResponseEntity<List<Category>> findAll() {
//		
//		/*
//		// List é uma interface. Não é possível instanciar. Deve instanciar classe que implementa interface.
//		List<Category> list = new ArrayList<>();
//		
//		
//		// 1L -> o L simboliza que o número será um Long
//		list.add(new Category(1L, "Books"));
//		list.add(new Category(2L, "Eletronics"));
//		
//		
//		//.ok() responder 200 -> requisicao com sucesso
//		// se quiser, pode colocar a resposta (list) dentro de ok()
//		*/
//		
//		List<Category> list = service.findAll();		
//		return ResponseEntity.ok().body(list);
//	}
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {
		
		/*
		// List é uma interface. Não é possível instanciar. Deve instanciar classe que implementa interface.
		List<Category> list = new ArrayList<>();
		
		
		// 1L -> o L simboliza que o número será um Long
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Eletronics"));
		
		
		//.ok() responder 200 -> requisicao com sucesso
		// se quiser, pode colocar a resposta (list) dentro de ok()
		*/
		
		
				
		Page<CategoryDTO> list = service.findAllPaged(pageable);
		
		return ResponseEntity.ok().body(list);
	}
	
	// fazer endpoint para buscar category por id
	// para o parametro de id que sera acrescentado ao /category
	
	// é melhor colocar um nome longo, autoexplicativo em vez de colocar comentarios. Pense nos usos do metodo em outros locais...
	
	// id tem que ser o mesmo nome que o outro id
	// anotation preprocessamento para receber o parametro vindo da rota e casar com a var do java
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {	
		CategoryDTO dto = service.findById(id);
		
		// voce pode implementar dessa forma, porem teria que colocar try/catch em varios trechos de codigo
		// o spring possui um recurso para agilizar essa parte -> controlador deve ser enxuto!
//		try {
//			CategoryDTO dto = service.findById(id);
//		}
//		catch (EntityNotFoundException e) {
//			
//		}
//		
		return ResponseEntity.ok().body(dto);
	}

	// os dados de insercao virao como um objeto
	// por padrao REST usamos o metodo post para inserir dados
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
	
		// geralmente quando criamos recurso com suceso retornamos o codigo 201 e nao 200.
		// "é um comando gigante mas é assim que fazemos no framework..."
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	// PUT é um metodo HTTP nao idempotente
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build(); // retorna 204
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

