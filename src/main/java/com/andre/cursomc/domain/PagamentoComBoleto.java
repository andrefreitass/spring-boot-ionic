package com.andre.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.andre.cursomc.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;

	private Date dataVencimento;
	private Date dataPagamento;

	// Construtores Padrao
	public PagamentoComBoleto() {
	}

	// Construtores Com Argumentos
	// E uma SubClasse de Pagamentos
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento,
			Date dataPagamento) {
		super(id, estado, pedido);
		this.setDataPagamento(dataPagamento);
		this.setDataVencimento(dataVencimento);

	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
