package com.andre.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.andre.cursomc.domain.Categoria;
import com.andre.cursomc.domain.Produto;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	//O @Param pega a variavel e joga na query
	//Notacao para criar a implementacao do metodo
	//usando o metodo findDistinctByNomeContainingAndCategoriasIn elimina a necessidade de utilizar o @Query, as palavras vem do frameWork do Spring
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome")String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
