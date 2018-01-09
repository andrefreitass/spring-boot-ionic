package com.andre.cursomc.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.andre.cursomc.domain.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	//Busca no banco de dados apenas os email
	@Transactional(readOnly=true)//Faz com que o campo nao seja envolvido na transacao
	Cliente findByEmail(String email);

}
