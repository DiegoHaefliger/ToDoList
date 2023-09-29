package com.haefliger.ToDoList.service;

import com.haefliger.ToDoList.dto.CategoriaDto;
import com.haefliger.ToDoList.entity.Categoria;
import com.haefliger.ToDoList.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

	private static final String CATEGORIA_JA_CADASTRADA = "Categoria já cadastrada!";
	private static final String CATEGORIA_NAO_ENCONTRADA = "Categoria não encontrada!";
	private static final String CATEGORIA_NAO_PODE_SER_EXCLUIDA = "Categoria não pode ser deletada, pois existem tarefas associadas a ela!";

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private TarefaService tarefaService;

	public Categoria save(CategoriaDto dto) {
		var isExistCategoria = categoriaRepository.existsByDescricao(dto.getDescricao());

		if (isExistCategoria) {
			throw new RuntimeException(CATEGORIA_JA_CADASTRADA);
		}

		var categoria = new Categoria(dto.getDescricao());

		return categoriaRepository.save(categoria);
	}

	public Categoria update(Integer idCategoria, CategoriaDto dto) {
		var categoria = categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA));

		categoria.setDescricao(dto.getDescricao());

		return categoriaRepository.save(categoria);
	}

	public ResponseEntity<Integer> delete(Integer idCategoria) {

		var isExistsByCategoria = tarefaService.existsByCategoria(idCategoria);

		if (isExistsByCategoria) {
			throw new RuntimeException(CATEGORIA_NAO_PODE_SER_EXCLUIDA);
		}

		var categoria = categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA));

		categoriaRepository.delete(categoria);

		return ResponseEntity.ok(idCategoria);
	}

	public Categoria getById(Integer idCategoria) {
		return categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA));
	}

	public List<Categoria> getAll() {
		return categoriaRepository.findAll();
	}

}
