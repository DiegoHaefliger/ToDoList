package com.haefliger.ToDoList.enums;

public enum Prioridade {

	BAIXA(1),
	MEDIA(2),
	ALTA(3);

	private int codigo;

	private Prioridade(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public static Prioridade valueOf(int codigo) {
		for (Prioridade prioridade : Prioridade.values()) {
			if (prioridade.getCodigo() == codigo) {
				return prioridade;
			}
		}
		throw new IllegalArgumentException("Prioridade inválida!");
	}

}
