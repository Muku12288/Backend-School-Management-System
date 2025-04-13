package com.school.jwtFilter;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private String secretKey = "";
	
	// constructor for generating secreteKey
	public JwtService() {
		
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
//			System.out.println(secretKey);
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//			System.out.println(secretKey);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public String generateToken(String name) {
		Map<String, Object> claims = new HashMap<>();
		
		
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(name)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() +600*60*30))
				.and()
				.signWith(getKey())
				.compact();
		
	}
	
	
	private SecretKey getKey() {
		//converting string into byte, because hmacShaKeyFor() take only byte value
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	// #####  Token validation   #######
	
	public String extractUserName(String token) {
		//extract the username from jwt token
		return extractClaim(token, Claims::getSubject);
	}
	
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
		
	}
	
	private Claims extractAllClaims(String token) {
		// in newer version parserBuilder()=> parser(), setSigningKey()=>verifyWith()
		// parseClaimsJws()=>parseSignedClaims(), getBody()=>getPayload();
		return Jwts.parser()
				.verifyWith(getKey())
				.build().parseSignedClaims(token).getPayload();
		
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		final String userName = extractUserName(token);
		System.out.println(userName);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}
	
}
