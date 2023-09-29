package com.haefliger.ToDoList.entity;

import com.haefliger.ToDoList.enums.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TAREFA")
public class Tarefa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "ID_CATEGORIA")
	private Categoria categoria;

	@OneToOne
	@JoinColumn(name = "ID_USER")
	private User user;

	@Column(name = "TITULO", nullable = false, length = 100)
	private String titulo;

	@Column(name = "DESCRICAO", nullable = false, length = 200)
	private String descricao;

	@Column(name = "CONCLUIDO", nullable = false)
	private Boolean concluido;

	@Column(name = "PRIORIDADE")
	private Prioridade prioridade;

	@Column(name = "DATA_INICIO", nullable = false)
	private LocalDateTime dataInicio;

	@Column(name = "DATA_FIM")
	private LocalDateTime dataFim;

	public Tarefa(Categoria categoria, User user, String titulo, String descricao, Boolean concluido, Prioridade prioridade, LocalDateTime dataInicio, LocalDateTime dataFim) {
		this.categoria = categoria;
		this.user = user;
		this.titulo = titulo;
		this.descricao = descricao;
		this.concluido = concluido;
		this.prioridade = prioridade;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

}
