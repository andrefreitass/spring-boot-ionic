package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Categoria;
import com.andre.cursomc.repositories.CategoriaRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto nao encontrado ID: " + id + ", Tipo " + Categoria.class.getName());
		} else {
			return obj;
		}

	}

}
