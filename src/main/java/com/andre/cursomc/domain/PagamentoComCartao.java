package com.andre.cursomc.domain;

import javax.persistence.Entity;

import com.andre.cursomc.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;

	private Integer numeroParcelas;

	// Construtores Padrao
	public PagamentoComCartao() {
	}

	// Construtores Com Argumentos
	//E uma SubClasse de Pagamentos
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroParcelas) {
		super(id, estado, pedido);
		this.setNumeroParcelas(numeroParcelas);
		
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}



	

}
