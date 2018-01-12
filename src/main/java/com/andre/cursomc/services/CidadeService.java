package com.andre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cidade;
import com.andre.cursomc.repositories.CidadeRepository;


@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> buscaCidade(Integer estadoId) {
		return repo.findCidades(estadoId);
		
	}
	
	
	
	
	

}
