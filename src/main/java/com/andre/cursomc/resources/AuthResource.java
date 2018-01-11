package com.andre.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andre.cursomc.security.JWTUtil;
import com.andre.cursomc.security.Usuario;
import com.andre.cursomc.services.UsuarioService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	//Metodo para gerar um token novo
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		Usuario usuario = UsuarioService.usuarioAutenticado();
		String token = jwtUtil.generateToken(usuario.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

}
