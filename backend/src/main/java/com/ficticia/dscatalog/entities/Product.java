package com.ficticia.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//annotation para fazer mapeamento e relação das tabelas no banco

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// OBS o proprio professor copiou e colou código. Então em alguns momentos eu poderi fazer o mesmo.
	
	// Annotation definir var id como ID no banco dados
	@Id
	// Annotation para o id ser autoincrementado no BD
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	// para que o campo aceite uma grande quantidade de caracteres -- estamos mapeando no banco o campo como "text" em vez de "varchar"
	@Column(columnDefinition = "TEXT")
	private String description;
	private Double price;
	private String imgUrl;
	
	// este campo nao esta no figma (os campos de la sao os que aparecem na aplicação)
	
	//colocar essa annotation em datas sempre (instruir banco de dados para armazenar dados no utc)
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant date;
	
	// um produto pode ter um a N categorias
	// usaremos SET porque implementa conceito de conjunto (nao aceita dado repetido. O produto nao tera categoria repetida)
	// SET é interface
	
	// Annotation para mapear Product com Category
	@ManyToMany
	// Especificar como será o mapeamento entre as entidades, espeficiar chaves estrangeiras...
	@JoinTable(name = "tb_product_category",
			joinColumns = @JoinColumn(name = "product_id"), 	// joinColumns (pega da propria classe) (...) @JoinColumn referencia a chave da entidade - classe onde voce esta a usando
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	Set<Category> categories = new HashSet<>(); // como o set usa Category, entao o JPA sabe que deve ser o id da classe Category e nao outra classe qualquer
	
	// Eh padrao java. Alguns Frameworks necessitam desse construtor vazio
	public Product() {
		
	}

	// nao informamos a colecao aqui
	public Product(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}
	
	public Set<Category> getCategories() {
		return categories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

}
