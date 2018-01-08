package com.andre.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andre.cursomc.domain.Pagamento;
//Vai acessar o banco de dados e buscar os dados de Categoria
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	
}
