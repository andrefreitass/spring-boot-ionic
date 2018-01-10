package com.andre.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.andre.cursomc.security.JWTFiltroAutenticacao;
import com.andre.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] ACESSO_LIBERADO = { "/h2-console/**" };

	// caminho apenas de leitura
	private static final String[] ACESSO_LIBERADO_CONSULTA = { "/produtos/**", "/categorias/**", "/clientes/**" };

	// Coisas do frameWork
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// libera o acesso ao h2, especifico do frameWork
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.GET, ACESSO_LIBERADO_CONSULTA).permitAll()
				.antMatchers(ACESSO_LIBERADO).permitAll().anyRequest().authenticated();
		http.addFilter(new JWTFiltroAutenticacao(authenticationManager(), jwtUtil));
		// esse metodo garante que o back end nao cria sessao de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	// Metodo que veio do site de apoio, configuracao padrao, pemite o acesso aos
	// EndPoint com configuracao basica
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Bean
	public BCryptPasswordEncoder criptografaSenha() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(criptografaSenha());
		
	}
	

}
