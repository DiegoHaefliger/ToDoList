package com.haefliger.ToDoList.jwt.configurations.security;


import com.haefliger.ToDoList.jwt.service.TokenService;
import com.haefliger.ToDoList.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String token;

		var authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			token = authorizationHeader.replace("Bearer ", "");
			var subject = this.tokenService.getSubject(token);

			var usuario = this.usuarioRepository.findByLogin(subject);

			var authenticathion = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authenticathion);

		}

		filterChain.doFilter(request, response);

	}

}
