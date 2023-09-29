package com.haefliger.ToDoList.repository;

import com.haefliger.ToDoList.entity.Tarefa;
import com.haefliger.ToDoList.entity.User;
import com.haefliger.ToDoList.enums.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
	List<Tarefa> findByConcluido(Boolean concluida);

	List<Tarefa> findByPrioridade(Prioridade prioridade);

	List<Tarefa> findByUser(User user);

	boolean existsByCategoriaId(Integer idCategoria);

}
