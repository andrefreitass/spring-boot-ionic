package com.andre.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;



@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder senhaCriptografada;
	
	@Autowired
	private EmailService emailService;
		
	private Random aleatorio = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("Email nao Encontrado");			
		}
		
		String novaSenha = novaSenha();
		cliente.setSenha(senhaCriptografada.encode(novaSenha));
		
		clienteRepository.save(cliente);
		
		emailService.envioNovaSenhaEmail(cliente, novaSenha);
	}

	private String novaSenha() {
		char[] senha = new char[10];
		for (int i = 0; i < 10; i++) {
			senha[i] = letraAleatoria();			
		}
		return new String(senha);
		
	}

	private char letraAleatoria() {
		int opt = aleatorio.nextInt(3);
		if (opt == 0) {//Gera um Digito
			return (char) (aleatorio.nextInt(10) + 48);
		}else if (opt == 1 ){//Gera Letra Maiuscula
			return (char) (aleatorio.nextInt(26) + 65);
		}else {//Gera Letra Miniscula
			return (char) (aleatorio.nextInt(10) + 97);
		}
		
	}
}
