package com.ficticia.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

//primeira a ser implementada em relacao ao product
// Serializable: padrao da ling. java - possibilita converter objeto em bytes para poder ser gravado em arquivo, trafegado em rede, etc.
// continua sendo utilizado por boa pratica

// Fazer com base na especificação - importar o javax
// Colocar annotation para...
// Ordem das annotation importa?

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Annotation definir var id como ID no banco dados
	@Id
	// Annotation para o id ser autoincrementado no BD
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	// dado para auditoria
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // Salvar no banco de dados no GMT 0 (UTC). Recomendação prof.
	private Instant createdAt;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	// Nao sera possivel realizar set na "mão"
	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public Category() {	
	}

	//opcional para um programador
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * comparar um objeto com o outro. Sao iguais?
	 * 
	 * hashCode eh mais rapido. Nao eh 100% confiavel. Em algum caso pode dizer que um obj eh igual a outro.
	 * 
	 * equals eh mais lenta mas eh muito mais confiavel.
	 * 
	 * hashCode eh mais usado para varrer uma colecao enorme. Depois voce pode usar o equals para validar mesmo.
	 * 
	 * */
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj; // Realiza o downcast de Object para Category e depois compara atributo por atrib.
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	
	// metodos abaixo para obter o valor atual antes de criar dado ou atualiza-lo
	
	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
}
