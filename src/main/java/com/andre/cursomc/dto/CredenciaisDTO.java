package com.andre.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class CredenciaisDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@NotEmpty(message="Preenchimento Obrigatorio")
	private String email;
	@NotEmpty(message="Preenchimento Obrigatorio")
	private String senha;
	
	public CredenciaisDTO(){
		
	}
		
	public CredenciaisDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
