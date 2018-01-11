package com.andre.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.Pedido;

public interface EmailService {
	
	void emailConfirmacaoPedido(Pedido pedido);
	
	void envioEmail(SimpleMailMessage mensagem);
	
	void envioNovaSenhaEmail(Cliente cliente,String novaSenha);

}
