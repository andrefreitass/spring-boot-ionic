package com.andre.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.remetente}")
	private String emailPadraoEnvio;
	
	
	@Override
	public void emailConfirmacaoPedido(Pedido pedido) {
		SimpleMailMessage mensagem = preparaMensagemEmailEnvio(pedido);
		envioEmail(mensagem);
		
	}

	protected SimpleMailMessage preparaMensagemEmailEnvio(Pedido pedido) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setTo(pedido.getCliente().getEmail());
		mensagem.setFrom(emailPadraoEnvio);
		mensagem.setSubject("Pedido Confirmado! Codigo: " + pedido.getId());
		mensagem.setSentDate(new Date(System.currentTimeMillis()));
		mensagem.setText(pedido.toString() + " \n" + " \n" + "PAGA O QUE VOCE ME DEVE");		
		return mensagem;
	}
	
	@Override
	public void envioNovaSenhaEmail(Cliente cliente,String novaSenha) {
		SimpleMailMessage mensagem = preparaSenhaEmailEnvio(cliente,novaSenha);
		envioEmail(mensagem);	
	}

	protected SimpleMailMessage preparaSenhaEmailEnvio(Cliente cliente, String novaSenha) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setTo(cliente.getEmail());
		mensagem.setFrom(emailPadraoEnvio);
		mensagem.setSubject("Solicitação de Nova Senha");
		mensagem.setSentDate(new Date(System.currentTimeMillis()));
		mensagem.setText("Nova Senha: " + novaSenha);		
		return mensagem;
	}

}
