package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.security.Usuario;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepository.findByEmail(email);
		
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
			
		return new Usuario(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfilAcesso());
	}

}
