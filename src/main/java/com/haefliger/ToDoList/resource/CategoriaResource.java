package com.haefliger.ToDoList.resource;

import com.haefliger.ToDoList.dto.CategoriaDto;
import com.haefliger.ToDoList.entity.Categoria;
import com.haefliger.ToDoList.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categoria")
@Api(tags = "Categoria", description = "Categoria de tarefas")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Sucesso"),
		@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
})
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@PostMapping("/")
	@ApiOperation(value = "Insere uma nova Categoria", response = String.class)
	public Categoria save(@RequestBody CategoriaDto dto) {
		return categoriaService.save(dto);
	}

	@PutMapping("/{idCategoria}")
	@ApiOperation(value = "Atualiza uma Categoria já cadastrada", response = String.class)
	public Categoria update(@PathVariable Integer idCategoria, @RequestBody CategoriaDto dto) {
		return categoriaService.update(idCategoria, dto);
	}

	@DeleteMapping("/{idCategoria}")
	@ApiOperation(value = "Deleta uma Categoria", response = String.class)
	public ResponseEntity<Integer> delete(@PathVariable Integer idCategoria) {
		return categoriaService.delete(idCategoria);
	}

	@GetMapping("/{idCategoria}")
	@ApiOperation(value = "Retorna uma única Categoria", response = String.class)
	public Categoria getById(@PathVariable Integer idCategoria) {
		return categoriaService.getById(idCategoria);
	}

	@GetMapping("/")
	@ApiOperation(value = "Retorna todas as Categorias", response = String.class)
	public List<Categoria> getAll() {
		return categoriaService.getAll();
	}

}
