package com.andre.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andre.cursomc.domain.Cidade;
import com.andre.cursomc.domain.Estado;
import com.andre.cursomc.dto.CidadeDTO;
import com.andre.cursomc.dto.EstadoDTO;
import com.andre.cursomc.services.CidadeService;
import com.andre.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> buscaTodosEstados() {
		List<Estado> lista = estadoService.buscaTodosEstados();
		List<EstadoDTO> listaDTO = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);

	}
	
	@RequestMapping(value="/{estadoId}/cidades",method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> buscaTodosCidades(@PathVariable Integer estadoId) {
		List<Cidade> lista = cidadeService.buscaCidade(estadoId);
		List<CidadeDTO> listaDTO = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);

	}
	

}
