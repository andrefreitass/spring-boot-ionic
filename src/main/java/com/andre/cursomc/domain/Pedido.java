package com.andre.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Definindo geracao automatica dos IDs
	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	private Date instante;

	// Criando Associacoes
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;

	// Criando Associacoes
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	// Criando Associacoes
	@ManyToOne
	@JoinColumn(name = "endereco_entrega_id")
	private Endereco enderecoEntrega;

	// Criando associacao dos Itens Pedidos
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> item = new HashSet<>();

	// Construtores Padrao
	public Pedido() {

	}

	// Construtores Com Argumentos
	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoEntrega = enderecoEntrega;
	}

	public Double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido ip : item) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Set<ItemPedido> getItem() {
		return item;
	}

	public void setItem(Set<ItemPedido> item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		builder.append("Pedido Numero: ");
		builder.append(getId());
		builder.append(" , Data Hora Compra: ");
		builder.append(dataFormatada.format(getInstante()));
		builder.append(" , Cliente ");
		builder.append(getCliente().getNome());
		builder.append(" , Situacao Pagamento: ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\n Detalhes \n");
		for (ItemPedido ip : getItem()) {
			builder.append(ip.toString());

		}
		builder.append("Valor Total: ");
		builder.append(numeroFormatado.format(getValorTotal()));
		return builder.toString();
	}

}
