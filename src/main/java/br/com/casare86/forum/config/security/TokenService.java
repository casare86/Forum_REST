package br.com.casare86.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.casare86.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}") //prazo de validade da API em milisegundos
	private String expiration;
	
	@Value("${forum.jwt.secret}") //secret é a chave da sua API - usar algoritimos para gerar strings grandes (256)
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do forum da Alura - Curso Rest") //Emissor da API
				.setSubject(logado.getId().toString()) 	//nome do usuário que requisitou
				.setIssuedAt(hoje) 						//data emissão
				.setExpiration(dataExpiracao)			//data expiração - prazo de validade
				.signWith(SignatureAlgorithm.HS256, secret) //hash de login para usuario
				.compact();
	}

}
