package com.andre.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(0, "Pedido Pendente"), 
	QUITADO(1, "Pedido Quitado"), 
	CANCELADO(2, "Pedido Cancelado");

	private Integer codigo;
	private String descricao;

	private EstadoPagamento(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPagamento toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id Situacao Invalido " + codigo);
	}
}
