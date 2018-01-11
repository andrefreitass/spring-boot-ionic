package com.andre.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.andre.cursomc.security.Usuario;

public class UsuarioService {

	// Buscar Usuario Autentica
	public static Usuario usuarioAutenticado() {
		try {
			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
