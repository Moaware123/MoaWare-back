package com.greedy.moaware.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {
	
	private static final String AUTHORITIES_KEY = "auth";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 200;	// 200분
	private static final String BEARER_TYPE = "bearer";
	private final Key key;
	
	private final UserDetailsService userDetailsService;
	
	public TokenProvider(@Value("${jwt.secret}") String secretKey,  UserDetailsService userDetailsService) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.userDetailsService = userDetailsService;
	}


	public TokenDto generateTokenDto(AuthEmpDto emp) {

		log.info("[TokenProvider] generateTokenDto Start =====================================");
	    
		Claims claims = Jwts.claims().setSubject(emp.getEmpId());

		/* 권한 담기 */
		List<String> roles = emp.getRoleList()
							    .stream()
							    .map(role -> role.getAuth().getAuthTitle())
							    .collect(Collectors.toList());

		claims.put(AUTHORITIES_KEY, roles);

		long now = new Date().getTime();
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setExpiration(accessTokenExpiresIn)
				.signWith(key,SignatureAlgorithm.HS512)
				.compact();
		log.info("[TokenProvider] generateTokenDto end =====================================");

		return new TokenDto(BEARER_TYPE, emp.getEmpName(), accessToken, accessTokenExpiresIn.getTime());
	}


	public boolean validateToken(String jwt) {
		
		Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
		
		return true;
	}
	
	public Authentication getAuthentication(String jwt) {
		
		Claims claims = parseClaims(jwt);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private Claims parseClaims(String jwt) {
		
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
	}

}

