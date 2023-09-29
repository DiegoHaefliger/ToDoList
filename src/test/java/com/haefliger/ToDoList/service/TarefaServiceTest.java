package com.haefliger.ToDoList.service;

import com.haefliger.ToDoList.dto.TarefaDto;
import com.haefliger.ToDoList.entity.Categoria;
import com.haefliger.ToDoList.entity.Tarefa;
import com.haefliger.ToDoList.entity.User;
import com.haefliger.ToDoList.enums.Prioridade;
import com.haefliger.ToDoList.repository.CategoriaRepository;
import com.haefliger.ToDoList.repository.TarefaRepository;
import com.haefliger.ToDoList.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TarefaServiceTest {

	private static final String CATEGORIA_NAO_ENCONTRADA = "Categoria não encontrada!";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado!";
	private static final String TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada!";

	@Mock
	private TarefaRepository tarefaRepository;

	@Mock
	private CategoriaRepository categoriaRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private TarefaService service;

	@Nested
	class Save {

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar categoria")
		void deveLancarExcecaoQuandoNaoEncontrarCategoria() {
			var dto = tarefaDtoMock();
			var exception = assertThrows(RuntimeException.class, () -> service.save(dto));

			assertEquals(CATEGORIA_NAO_ENCONTRADA, exception.getMessage());

		}

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar usuário")
		void deveLancaExcecaoQuandoNaoEncontrarUsuario() {

			var dto = tarefaDtoMock();
			var categoria = categoriaMock();

			when(categoriaRepository.findById(dto.getIdCategoria())).thenReturn(java.util.Optional.of(categoria));
			var exception = assertThrows(RuntimeException.class, () -> service.save(dto));

			assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());

		}

		@Test
		@DisplayName("Deve salvar tarefa")
		void deveSalvarTarefa() {
			var dto = tarefaDtoMock();
			var categoria = categoriaMock();
			var user = userMock();

			when(categoriaRepository.findById(dto.getIdCategoria())).thenReturn(Optional.of(categoria));
			when(usuarioRepository.findById(dto.getIdUsuario())).thenReturn(Optional.of(user));

			service.save(dto);

			var captor = ArgumentCaptor.forClass(Tarefa.class);
			verify(tarefaRepository).save(captor.capture());
			var tarefa = captor.getValue();

			verify(tarefaRepository).save(any());

			assertEquals(dto.getTitulo(), tarefa.getTitulo());
			assertEquals(dto.getDescricao(), tarefa.getDescricao());
			assertEquals(dto.getConcluido(), tarefa.getConcluido());
			assertEquals(dto.getPrioridade(), tarefa.getPrioridade());
			assertEquals(dto.getDataInicio(), tarefa.getDataInicio());
			assertEquals(dto.getDataFim(), tarefa.getDataFim());
		}

	}

	@Nested
	class FindAll {

		@Test
		@DisplayName("Deve retornar todas as tarefas")
		void deveRetornarTodasAsTarefas() {
			var tarefa = tarefaMock();

			when(tarefaRepository.findAll()).thenReturn(List.of(tarefa));

			var retorno = service.findAll();
			verify(tarefaRepository).findAll();

			assertEquals(1, retorno.size());
		}

	}

	@Nested
	class FindById {

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar tarefa")
		void deveLancarExcecaoQuandoNaoEncontrarTarefa() {
			var exception = assertThrows(RuntimeException.class, () -> service.findById(1));

			assertEquals(TAREFA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve retornar tarefa")
		void deveRetornarTarefa() {
			var tarefa = tarefaMock();

			when(tarefaRepository.findById(any())).thenReturn(Optional.of(tarefa));

			var retorno = service.findById(1);

			assertEquals(tarefa, retorno);
		}

	}

	@Nested
	class FindConcluida {

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		@DisplayName("Deve retornar lista de tarefas concluídas")
		void deveRetornarListaDeTarefasConcluidas(boolean isConcluido) {
			var tarefa = tarefaMock();

			when(tarefaRepository.findByConcluido(any())).thenReturn(List.of(tarefa));

			var retorno = service.findConcluida(isConcluido);

			verify(tarefaRepository).findByConcluido(isConcluido);

			assertEquals(1, retorno.size());
		}

	}

	@Nested
	class FindPrioridade {

		@ParameterizedTest
		@EnumSource(Prioridade.class)
		@DisplayName("Deve retornar lista de tarefas por prioridade")
		void deveRetornarListaDeTarefasPorPrioridade(Prioridade prioridade) {
			var tarefa = tarefaMock();

			when(tarefaRepository.findByPrioridade(any())).thenReturn(List.of(tarefa));

			var retorno = service.findPrioridade(prioridade);

			verify(tarefaRepository).findByPrioridade(prioridade);

			assertEquals(1, retorno.size());
		}

	}

	@Nested
	class FindUsuario {

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar usuário")
		void deveLancarExcecaoQuandoNaoEncontrarUsuario() {
			var exception = assertThrows(RuntimeException.class, () -> service.findUsuario(1));

			assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
		}

		@Test
		@DisplayName("Deve retornar lista de tarefas por usuário")
		void deveRetornarListaDeTarefasPorUsuario() {
			var tarefa = tarefaMock();

			when(usuarioRepository.findById(any())).thenReturn(Optional.of(userMock()));
			when(tarefaRepository.findByUser(any())).thenReturn(List.of(tarefa));

			var retorno = service.findUsuario(1);

			verify(tarefaRepository).findByUser(any());

			assertEquals(1, retorno.size());
		}

	}

	@Nested
	class Update {

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar tarefa")
		void deveLancarExcecaoQuandoNaoEncontrarTarefa() {
			var exception = assertThrows(RuntimeException.class, () -> service.update(1, tarefaDtoMock()));

			assertEquals(TAREFA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar categoria")
		void deveLancarExcecaoQuandoNaoEncontrarCategoria() {
			var tarefa = tarefaMock();
			var dto = tarefaDtoMock();

			when(tarefaRepository.findById(any())).thenReturn(Optional.of(tarefa));

			var exception = assertThrows(RuntimeException.class, () -> service.update(1, dto));

			assertEquals(CATEGORIA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar usuário")
		void deveLancarExcecaoQuandoNaoEncontrarUsuario() {
			var tarefa = tarefaMock();
			var dto = tarefaDtoMock();
			var categoria = categoriaMock();

			when(tarefaRepository.findById(any())).thenReturn(Optional.of(tarefa));
			when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoria));

			var exception = assertThrows(RuntimeException.class, () -> service.update(1, dto));

			assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
		}

		@Test
		@DisplayName("Deve atualizar tarefa")
		void deveAtualizarTarefa() {
			var tarefa = tarefaMock();
			var dto = tarefaDtoMock();
			var user = userMock();

			when(tarefaRepository.findById(any())).thenReturn(Optional.of(tarefa));
			when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoriaMock()));
			when(usuarioRepository.findById(any())).thenReturn(Optional.of(user));

			service.update(1, dto);

			verify(tarefaRepository).save(any());
		}

	}

	@Nested
	class Delete {

		@Test
		@DisplayName("Deve lançar exceção quando não encontrar tarefa")
		void deveLancarExcecaoQuandoNaoEncontrarTarefa() {
			var exception = assertThrows(RuntimeException.class, () -> service.delete(1));

			assertEquals(TAREFA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve deletar tarefa")
		void deveDeletarTarefa() {
			var tarefa = tarefaMock();

			when(tarefaRepository.findById(any())).thenReturn(Optional.of(tarefa));

			service.delete(1);

			verify(tarefaRepository).delete(any());
		}

	}

	private TarefaDto tarefaDtoMock() {
		return new TarefaDto(
				1,
				1,
				"titulo",
				"descricao",
				false,
				Prioridade.BAIXA,
				LocalDateTime.now(),
				LocalDateTime.now().plusMonths(1)
		);
	}

	private Categoria categoriaMock() {
		return new Categoria("descricao");
	}

	private User userMock() {
		return new User("nome", "email", "senha", "role");
	}

	private Tarefa tarefaMock() {
		return new Tarefa(
				categoriaMock(),
				userMock(),
				"titulo",
				"descricao",
				false,
				Prioridade.BAIXA,
				LocalDateTime.now(),
				LocalDateTime.now().plusMonths(1)
		);
	}

}