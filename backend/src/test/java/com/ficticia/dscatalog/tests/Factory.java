package com.ficticia.dscatalog.tests;

import java.time.Instant;

import com.ficticia.dscatalog.dto.ProductDTO;
import com.ficticia.dscatalog.entities.Category;
import com.ficticia.dscatalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 983.43, "https://img.com/img.png",
				Instant.parse("2021-10-14T04:02:56Z"));
		product.getCategories().add(new Category(2L, "Electronics"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
		
	}
}
