package com.haefliger.ToDoList.repository;

import com.haefliger.ToDoList.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	boolean existsByDescricao(String descricao);

}
