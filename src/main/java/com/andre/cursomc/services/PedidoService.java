package com.andre.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.ItemPedido;
import com.andre.cursomc.domain.PagamentoComBoleto;
import com.andre.cursomc.domain.Pedido;
import com.andre.cursomc.domain.enums.EstadoPagamento;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.repositories.ItemPedidoRepository;
import com.andre.cursomc.repositories.PagamentoRepository;
import com.andre.cursomc.repositories.PedidoRepository;
import com.andre.cursomc.repositories.ProdutoRepository;
import com.andre.cursomc.security.Usuario;
import com.andre.cursomc.services.exception.AuthorizationException;
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

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmailService emailService;

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
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
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
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);

		}
		itemPedidoRepository.save(obj.getItem());
		emailService.emailConfirmacaoPedido(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// Busca o cliente autenticado
		Usuario usuario = UsuarioService.usuarioAutenticado();

		if (usuario == null) {
			throw new AuthorizationException("Usuario invalido, acesso negado");
		}
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteRepository.findOne(usuario.getId());
		return repo.findByCliente(cliente, pageRequest);

	}

}
