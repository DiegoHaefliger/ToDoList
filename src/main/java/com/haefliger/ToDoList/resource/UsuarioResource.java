package com.haefliger.ToDoList.resource;

import com.haefliger.ToDoList.entity.User;
import com.haefliger.ToDoList.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
@Api(tags = "Usuários", description = "Usuários do sistema")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Sucesso"),
		@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
})
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/{idUser}")
	@ApiOperation(value = "Retorna um único Usuário", response = String.class)
	public User getById(@PathVariable Integer idUser) {
		return usuarioService.getById(idUser);
	}

	@GetMapping("/")
	@ApiOperation(value = "Retorna todos os Usuários", response = String.class)
	public List<User> getAll() {
		return usuarioService.getAll();
	}

}
