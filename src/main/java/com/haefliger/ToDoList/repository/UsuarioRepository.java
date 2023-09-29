package com.haefliger.ToDoList.repository;

import com.haefliger.ToDoList.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Integer> {

	User findByLogin(String login);

	boolean existsByLogin(String login);

}