package com.haefliger.ToDoList.dto;

import com.haefliger.ToDoList.enums.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TarefaDto {

	private Integer idUsuario;

	private Integer idCategoria;

	@NotBlank(message = "O Nome é obrigatório")
	@Length(min = 1, max = 150, message = "O campo Nome deverá ter no máximo {max} caracteres")
	private String titulo;

	private String descricao;

	private Boolean concluido;

	private Prioridade prioridade;

	private LocalDateTime dataInicio;

	private LocalDateTime dataFim;

}
