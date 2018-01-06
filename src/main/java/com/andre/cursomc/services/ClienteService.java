package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Cliente nao encontrado ID: " + id + ", Tipo " + Cliente.class.getName());
		} else {
			return obj;
		}

	}
}
