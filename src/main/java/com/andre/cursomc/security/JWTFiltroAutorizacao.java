package com.andre.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTFiltroAutorizacao extends BasicAuthenticationFilter{

		@Autowired
		private JWTUtil jwtUtil;

		@Autowired
		private UserDetailsService userDetailsService;
		
		
		//Metodo que filtra a requisicao
	public JWTFiltroAutorizacao(AuthenticationManager authenticationManager,JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	//Metodo para capturar o TOKEEN, e liberar o acesso ao End-Point
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain chain) throws IOException, ServletException {
		
		String header = request.getHeader("Authorization");
		
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken  autenticacaoToken = getAuthentication(header.substring(7));
			if (autenticacaoToken != null) {
				SecurityContextHolder.getContext().setAuthentication(autenticacaoToken);
				
			}
			
		}
		chain.doFilter(request, response);
	}
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUserName(token);
			UserDetails usuario = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
		}
		return null;
	}


}
