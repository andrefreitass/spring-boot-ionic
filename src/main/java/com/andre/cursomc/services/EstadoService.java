package com.andre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Estado;
import com.andre.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> buscaTodosEstados(){
		return repo.findAllByOrderByNome();
	}

}
