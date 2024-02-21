package com.devPontes.WebService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devPontes.WebService.DTO.security.AccountCredentialsDTO;
import com.devPontes.WebService.DTO.security.TokenDTO;
import com.devPontes.WebService.model.entities.User;
import com.devPontes.WebService.repositories.UserRepository;
import com.devPontes.WebService.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@SuppressWarnings("rawtypes")
	public ResponseEntity signIn(AccountCredentialsDTO data) {
		try {
			String username = data.getUsername();
			String password = data.getPassword();

			// Verifica se o usuário existe e se a senha fornecida pelo usuário corresponde
			// à senha criptografada armazenada no banco de dados
			User user = repository.findByUsername(username);
			if (user != null && passwordEncoder.matches(password, user.getPassword())) {
				// Autentica o usuário
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

				// Gera o token de acesso
				TokenDTO tokenResponse = tokenProvider.createAccessTOken(username, user.getRoles());
				return ResponseEntity.ok(tokenResponse);
			} else {
				throw new BadCredentialsException("Invalid username/password supplied!");
			}
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
			var user = repository.findByUsername(username);
			var tokenResponse = new TokenDTO();
			if (user != null) {
				tokenResponse = tokenProvider.refreshToken(refreshToken);
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			return ResponseEntity.ok(tokenResponse);
		}
	}

