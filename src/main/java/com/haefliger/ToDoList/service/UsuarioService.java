package com.haefliger.ToDoList.service;

import com.haefliger.ToDoList.entity.User;
import com.haefliger.ToDoList.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private static final String USUÁRIO_NÃO_ENCONTRADO = "Usuário não encontrado!";

	public User getById(Integer idUser) {

		return usuarioRepository.findById(idUser)
				.orElseThrow(() -> new RuntimeException(USUÁRIO_NÃO_ENCONTRADO));
	}

	public List<User> getAll() {
		return usuarioRepository.findAll();
	}

}
