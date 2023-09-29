package com.haefliger.ToDoList.service;

import com.haefliger.ToDoList.dto.CategoriaDto;
import com.haefliger.ToDoList.entity.Categoria;
import com.haefliger.ToDoList.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoriaServiceTest {

	private static final String CATEGORIA_JA_CADASTRADA = "Categoria já cadastrada!";
	private static final String CATEGORIA_NAO_ENCONTRADA = "Categoria não encontrada!";
	private static final String CATEGORIA_NAO_PODE_SER_EXCLUIDA = "Categoria não pode ser deletada, pois existem tarefas associadas a ela!";
	private static final Integer ID_CATEGORIA = 1;

	@Mock
	CategoriaRepository categoriaRepository;

	@Mock
	private TarefaService tarefaService;

	@InjectMocks
	CategoriaService service;

	@Nested
	class Save {

		@Test
		@DisplayName("Deve lançar exceção quando já existir descrição")
		void deveLancarExcecaoQuandoJaExistirDescricao() {

			var dto = new CategoriaDto("descricao");

			when(categoriaRepository.existsByDescricao(any())).thenReturn(true);

			var exception = assertThrows(RuntimeException.class, () -> service.save(dto));

			assertEquals(CATEGORIA_JA_CADASTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve salvar categoria")
		void deveSalvarCategoria() {

			var dto = new CategoriaDto("descricao");

			when(categoriaRepository.existsByDescricao(any())).thenReturn(false);

			service.save(dto);

			var captor = ArgumentCaptor.forClass(Categoria.class);
			verify(categoriaRepository).save(captor.capture());
			var resultado = captor.getValue();

			assertEquals(dto.getDescricao(), resultado.getDescricao());

		}

	}

	@Nested
	class Update {

		@Test
		@DisplayName("Deve lançar exceção quando não existir categoria")
		void deveLancarExcecaoQuandoNaoExistirCategoria() {

			var dto = new CategoriaDto("descricao");

			var exception = assertThrows(RuntimeException.class, () -> service.update(ID_CATEGORIA, dto));

			assertEquals(CATEGORIA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve atualizar categoria")
		void deveAtualizarCategoria() {

			var dto = new CategoriaDto("nova descricao");
			var categoria = categoriaMock();

			when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoria));

			service.update(ID_CATEGORIA, dto);

			var captor = ArgumentCaptor.forClass(Categoria.class);
			verify(categoriaRepository).save(captor.capture());
			var resultado = captor.getValue();

			assertEquals(dto.getDescricao(), resultado.getDescricao());

		}

	}

	@Nested
	class Delete {

		@Test
		@DisplayName("Deve lançar exceção quando não existir categoria")
		void deveLancarExcecaoQuandoNaoExistirCategoria() {

			when(tarefaService.existsByCategoria(any())).thenReturn(false);

			var exception = assertThrows(RuntimeException.class, () -> service.delete(ID_CATEGORIA));

			assertEquals(CATEGORIA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve lançar exceção quando existir tarefa associada a categoria")
		void deveLancarExcecaoQuandoExistirTarefaAssociadaACategoria() {

			when(tarefaService.existsByCategoria(any())).thenReturn(true);

			var exception = assertThrows(RuntimeException.class, () -> service.delete(ID_CATEGORIA));

			assertEquals(CATEGORIA_NAO_PODE_SER_EXCLUIDA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve deletar categoria")
		void deveDeletarCategoria() {

			var categoria = categoriaMock();

			when(tarefaService.existsByCategoria(any())).thenReturn(false);
			when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoria));

			service.delete(ID_CATEGORIA);

			var captor = ArgumentCaptor.forClass(Categoria.class);
			verify(categoriaRepository).delete(captor.capture());
			var resultado = captor.getValue();

			assertEquals(categoria, resultado);

		}

	}

	@Nested
	class GetById {

		@Test
		@DisplayName("Deve lançar exceção quando não existir categoria")
		void deveLancarExcecaoQuandoNaoExistirCategoria() {

			var exception = assertThrows(RuntimeException.class, () -> service.getById(ID_CATEGORIA));

			assertEquals(CATEGORIA_NAO_ENCONTRADA, exception.getMessage());
		}

		@Test
		@DisplayName("Deve retornar categoria")
		void deveRetornarCategoria() {

			var categoria = categoriaMock();

			when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoria));

			var resultado = service.getById(ID_CATEGORIA);

			assertEquals(categoria, resultado);

		}

	}

	@Nested
	class GetAll {

		@Test
		@DisplayName("Deve retornar lista de categorias")
		void deveRetornarListaDeCategorias() {

			var categoria = categoriaMock();

			when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

			var retorno = service.getAll();

			verify(categoriaRepository).findAll();

			assertEquals(1, retorno.size());

		}

	}

	private Categoria categoriaMock() {
		return new Categoria("descricao");
	}

}