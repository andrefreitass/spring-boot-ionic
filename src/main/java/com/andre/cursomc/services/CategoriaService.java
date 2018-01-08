package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Categoria;
import com.andre.cursomc.repositories.CategoriaRepository;
import com.andre.cursomc.services.exception.DataIntegrityException;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	// Metodo para buscar dados
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto nao encontrado ID: " + id + ", Tipo " + Categoria.class.getName());
		} else {
			return obj;
		}

	}

	// Metodo para Insercao
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	// Metodo para atualizacao
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	// Metodo para Exclusao
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao e possivel excluir uma categoria que possui produtos");
			
		}
		
		
	}


}
