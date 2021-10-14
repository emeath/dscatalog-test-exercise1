package com.ficticia.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ficticia.dscatalog.entities.Category;

// tipo generico JpaRepository<T, ID>
// Isso ja nos permite varias operacoes prontas para acessar banco de dados --> funciona para varios bd relacionais

// Registra a interface como componente de repositorie para injeção de dependencia
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
