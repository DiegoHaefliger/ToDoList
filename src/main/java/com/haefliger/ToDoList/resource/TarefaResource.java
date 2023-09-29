package com.haefliger.ToDoList.resource;

import com.haefliger.ToDoList.dto.TarefaDto;
import com.haefliger.ToDoList.entity.Tarefa;
import com.haefliger.ToDoList.enums.Prioridade;
import com.haefliger.ToDoList.service.TarefaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tarefa")
@Api(tags = "Tarefas", description = "Tarefas a serem realizadas")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Sucesso"),
		@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
})
public class TarefaResource {

	@Autowired
	private TarefaService tarefaService;

	@PostMapping("/")
	@ApiOperation(value = "Insere uma nova Tarefa", response = String.class)
	public Tarefa save(@Valid @RequestBody TarefaDto dto) {
		return tarefaService.save(dto);
	}

	@PutMapping("/{idTarefa}")
	@ApiOperation(value = "Altera uma nova Tarefa já cadastrada", response = String.class)
	public Tarefa save(@PathVariable Integer idTarefa, @RequestBody TarefaDto dto) {
		return tarefaService.update(idTarefa, dto);
	}

	@PutMapping("/{idTarefa}")
	@ApiOperation(value = "Deleta uma nova Tarefa", response = String.class)
	public ResponseEntity<Integer> delete(@PathVariable Integer idTarefa) {
		return tarefaService.delete(idTarefa);
	}

	@GetMapping("/{idTarefa}")
	@ApiOperation(value = "Retorna uma única Tarefa", response = String.class)
	public Tarefa findById(@PathVariable Integer idTarefa) {
		return tarefaService.findById(idTarefa);
	}

	@GetMapping("/")
	@ApiOperation(value = "Retorna todas as Tarefa", response = String.class)
	public List<Tarefa> findAll() {
		return tarefaService.findAll();
	}

	@GetMapping("/concluida/{concluida}")
	@ApiOperation(value = "Retorna todas as Tarefa concluídas ou não conlcuídas", response = String.class)
	public List<Tarefa> findConcluida(@PathVariable Boolean concluida) {
		return tarefaService.findConcluida(concluida);
	}

	@GetMapping("/prioridade/{prioridade}")
	@ApiOperation(value = "Retorna todas as Tarefa por prioridade", response = String.class)
	public List<Tarefa> findPrioridade(@PathVariable Prioridade prioridade) {
		return tarefaService.findPrioridade(prioridade);
	}

	@GetMapping("/usuario/{idUsuario}")
	@ApiOperation(value = "Retorna todas as Tarefa por Usuário", response = String.class)
	public List<Tarefa> findUsuario(@PathVariable Integer idUsuario) {
		return tarefaService.findUsuario(idUsuario);
	}

}
