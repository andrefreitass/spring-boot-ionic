package com.andre.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.andre.cursomc.services.DBService;
import com.andre.cursomc.services.EmailService;
import com.andre.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DesConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String estrategia;
	
	// metodo para instaciar o banco de dados
	@Bean
	public Boolean instanciaDataBase() throws ParseException {
		if (!"create".equals(estrategia)) {
			return false;
		}
		dbService.instanciaDataBaseTest();

		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
