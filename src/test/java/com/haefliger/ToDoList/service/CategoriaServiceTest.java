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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoriaServiceTest {

	@Mock
	CategoriaRepository categoriaRepository;

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

			assertEquals("Categoria já cadastrada!", exception.getMessage());
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

	private Categoria categoriaMock() {
		return new Categoria("descricao");
	}

}