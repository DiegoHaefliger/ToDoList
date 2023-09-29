package com.haefliger.ToDoList.service;

import com.haefliger.ToDoList.dto.TarefaDto;
import com.haefliger.ToDoList.entity.Tarefa;
import com.haefliger.ToDoList.enums.Prioridade;
import com.haefliger.ToDoList.repository.CategoriaRepository;
import com.haefliger.ToDoList.repository.TarefaRepository;
import com.haefliger.ToDoList.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

	private static final String CATEGORIA_NAO_ENCONTRADA = "Categoria não encontrada!";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado!";
	private static final String TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada!";

	@Autowired
	private TarefaRepository tarefaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Tarefa save(TarefaDto dto) {

		var categoria = categoriaRepository.findById(dto.getIdCategoria())
				.orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA));

		var user = usuarioRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));

		var tarefa = new Tarefa(
				categoria,
				user,
				dto.getTitulo(),
				dto.getDescricao(),
				dto.getConcluido(),
				dto.getPrioridade(),
				dto.getDataInicio(),
				dto.getDataFim()
		);

		return tarefaRepository.save(tarefa);

	}

	public List<Tarefa> findAll() {
		return tarefaRepository.findAll();
	}

	public Tarefa findById(Integer idTarefa) {
		return tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new RuntimeException(TAREFA_NAO_ENCONTRADA));
	}

	public List<Tarefa> findConcluida(Boolean concluida) {
		return tarefaRepository.findByConcluido(concluida);
	}

	public List<Tarefa> findPrioridade(Prioridade prioridade) {
		return tarefaRepository.findByPrioridade(prioridade);
	}

	public List<Tarefa> findUsuario(Integer idUsuario) {
		var usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));

		return tarefaRepository.findByUser(usuario);
	}

	public boolean existsByCategoria(Integer idCategoria) {
		return tarefaRepository.existsByCategoriaId(idCategoria);
	}

	public Tarefa update(Integer idTarefa, TarefaDto dto) {
		var tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new RuntimeException(TAREFA_NAO_ENCONTRADA));

		var categoria = categoriaRepository.findById(dto.getIdCategoria())
				.orElseThrow(() -> new RuntimeException(CATEGORIA_NAO_ENCONTRADA));

		var user = usuarioRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException(USUARIO_NAO_ENCONTRADO));

		tarefa.setCategoria(categoria);
		tarefa.setUser(user);
		tarefa.setTitulo(dto.getTitulo());
		tarefa.setDescricao(dto.getDescricao());
		tarefa.setConcluido(dto.getConcluido());
		tarefa.setPrioridade(dto.getPrioridade());
		tarefa.setDataInicio(dto.getDataInicio());
		tarefa.setDataFim(dto.getDataFim());

		return tarefaRepository.save(tarefa);
	}

	public ResponseEntity<Integer> delete(Integer idTarefa) {
		var tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(() -> new RuntimeException(TAREFA_NAO_ENCONTRADA));

		tarefaRepository.delete(tarefa);

		return ResponseEntity.ok(idTarefa);
	}

}
