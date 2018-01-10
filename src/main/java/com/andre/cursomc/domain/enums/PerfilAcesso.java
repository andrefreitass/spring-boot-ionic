package com.andre.cursomc.domain.enums;

public enum PerfilAcesso {

	ADMIN(0, "ROLE_ADMINISTRADOR"),//o ROLE e exigencia do frameWork 
	CLIENTE(1, "ROLE_CLIENTE");

	private Integer codigo;
	private String descricao;

	private PerfilAcesso(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static PerfilAcesso toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		for (PerfilAcesso x : PerfilAcesso.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id Situacao Invalido " + codigo);
	}
}
