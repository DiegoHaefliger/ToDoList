package com.haefliger.ToDoList.resource;

import com.haefliger.ToDoList.dto.LoginDto;
import com.haefliger.ToDoList.entity.User;
import com.haefliger.ToDoList.jwt.service.TokenService;
import com.haefliger.ToDoList.repository.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Auth", description = "Autenticação JWT")
@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Sucesso"),
		@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
})
public class AuthResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/login")
	@ApiOperation(value = "Autenticação", response = String.class)
	public String login(@RequestBody LoginDto login) {

		var isUserExist = usuarioRepository.existsByLogin(login.getLogin());

		if (!isUserExist) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			usuarioRepository.save(new User("", login.getLogin(), passwordEncoder.encode(login.getSenha()), "A"));
		}

		var authenticationToken = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getSenha());
		var authentication = this.authenticationManager.authenticate(authenticationToken);
		var usuario = (User) authentication.getPrincipal();

		return this.tokenService.gerarToken(usuario);
	}

}
