package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Pedido;
import com.andre.cursomc.repositories.PedidoRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Pedido nao encontrado ID: " + id + ", Tipo " + Pedido.class.getName());
		} else {
			return obj;
		}

	}

}
