package com.andre.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.ItemPedido;
import com.andre.cursomc.domain.PagamentoComBoleto;
import com.andre.cursomc.domain.Pedido;
import com.andre.cursomc.domain.enums.EstadoPagamento;
import com.andre.cursomc.repositories.ItemPedidoRepository;
import com.andre.cursomc.repositories.PagamentoRepository;
import com.andre.cursomc.repositories.PedidoRepository;
import com.andre.cursomc.repositories.ProdutoRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Pedido nao encontrado ID: " + id + ", Tipo " + Pedido.class.getName());
		} else {
			return obj;
		}
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgtBoleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgtBoleto, obj.getInstante());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItem()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findOne(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);

		}
		itemPedidoRepository.save(obj.getItem());
		return obj;
	}

}
