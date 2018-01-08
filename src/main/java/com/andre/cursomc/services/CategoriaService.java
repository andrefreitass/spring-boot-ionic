package com.andre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Categoria;
import com.andre.cursomc.dto.CategoriaDTO;
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
	
	// Metodo para buscar Todos Dados
	public List<Categoria> findAll() {
		List<Categoria> lista = repo.findAll();
		if (lista == null) {
			throw new ObjectNotFoundException(
					"Objeto nao encontrado ID: " + findAll() + ", Tipo " + Categoria.class.getName());
		} else {
			return repo.findAll();
		}

	}
	//Metodo para paginacao
	public Page<Categoria> findPage(Integer page,Integer linesPerPage,String orderBy,String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction) , orderBy);
		return repo.findAll(pageRequest);
		
	}
	//Converte DTO para Entity
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(),objDTO.getNome());
	}
	
}
