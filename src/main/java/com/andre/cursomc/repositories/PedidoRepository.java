package com.andre.cursomc.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.Pedido;

//Vai acessar o banco de dados e buscar os dados de Categoria
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	@Transactional(readOnly=true)//Faz com que o campo nao seja envolvido na transacao, reduz lock
	Page<Pedido>findByCliente(Cliente cliente, Pageable pageRequest);
	
}
	