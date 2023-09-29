package com.haefliger.ToDoList.converter;

import com.haefliger.ToDoList.enums.Prioridade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PrioridadeConverter implements AttributeConverter<Prioridade, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Prioridade prioridade) {
		if (prioridade == null) {
			return Prioridade.BAIXA.getCodigo();
		}

		return prioridade.getCodigo();
	}

	@Override
	public Prioridade convertToEntityAttribute(Integer codigo) {
		if (codigo == null) {
			return Prioridade.BAIXA;
		}

		return Prioridade.valueOf(codigo);
	}

}
