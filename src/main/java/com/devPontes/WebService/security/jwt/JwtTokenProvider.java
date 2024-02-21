package com.devPontes.WebService.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devPontes.WebService.DTO.security.TokenDTO;
import com.devPontes.WebService.exceptions.InvalidJwtAuthenticationException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:secret}")
	private Long validityInMilliSeconds = 3600000L;

	@Autowired
	private UserDetailsService services;

	Algorithm algorithm = null;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}

	public TokenDTO createAccessTOken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliSeconds);
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);
		return new TokenDTO(username, true, now, validity, accessToken, refreshToken);

	}
	
	
	public TokenDTO refreshToken(String refreshToken) {
		if(refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.substring("Bearer ".length());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(refreshToken);
			String username = decodedJWT.getSubject();
			List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
			return createAccessTOken(username, roles);
		} return null;
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + validityInMilliSeconds * 3);

		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshToken)
				.withSubject(username)
				.sign(algorithm)
				.strip();

	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.sign(algorithm)
				.strip();
	}

	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJwt = decodedToken(token);
		UserDetails userDetails = this.services.loadUserByUsername(decodedJwt.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public DecodedJWT decodedToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJwt = verifier.verify(token);
		return decodedJwt;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}

		return null;
	}
	
	public boolean validateToken(String token) {
		DecodedJWT decodedJwt = decodedToken(token);
		try {
			if(decodedJwt.getExpiresAt().before(new Date())) {
				return false;
			} return true;
			
		}catch(Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
		}
	}
}
