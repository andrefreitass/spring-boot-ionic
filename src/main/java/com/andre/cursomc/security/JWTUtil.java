package com.andre.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	//metodo para criacao de token
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	/*/
	 * Claims reivindicando o token(usuario/tempo expiracao)
	 */
	public Boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			//busca o usuario
			String username = claims.getSubject();
			Date dataExpiracao = claims.getExpiration();
			Date dataAgora = new Date(System.currentTimeMillis());
			
			if (username != null && dataExpiracao != null && dataAgora.before(dataExpiracao)) {
				return true;
			}
			
		}
		return false;
	}
	/*.
	 * Recupera os Claims a partir do token
	 */
	private Claims getClaims(String token) {
		try{
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch(Exception e) {
			return null;	
		}
		
	}

	/*
	 * Metodo para recuperar o Usuario a partir do TOKEN*/
	public String getUserName(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			//busca o usuario
			return claims.getSubject();
		}
		return null;
	}

}
