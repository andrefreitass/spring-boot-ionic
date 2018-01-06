package com.andre.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andre.cursomc.domain.Cidade;
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
