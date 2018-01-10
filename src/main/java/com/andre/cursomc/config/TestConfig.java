package com.andre.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.andre.cursomc.services.DBService;
import com.andre.cursomc.services.EmailService;
import com.andre.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	// metodo para instaciar o banco de dados
	@Bean
	public Boolean instanciaDataBase() throws ParseException {
		dbService.instanciaDataBaseTest();

		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
