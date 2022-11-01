package dev.dracarys.com.hospitalquerysystem.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.dracarys.com.hospitalquerysystem.configs.DetailsUserData;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;


@Component
@Log4j2
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);


	public static final int TOKEN_EXPIRATION = 600_000;
	protected static final byte[] TOKEN_PASSWORD = "2a502fb2-5596-11ed-bdc3-0242ac120002".getBytes();
	
	public static String generateAccessToken(DetailsUserData userData) {
		
		return JWT.create()
                .withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .withClaim("roles", userData.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toString())
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));
	}
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(TOKEN_PASSWORD).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired", ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or only whitespace", ex);
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed");
		}
		
		return false;
	}
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	public static Claims parseClaims(String token) {

		return Jwts.parser()
				.setSigningKey(TOKEN_PASSWORD)
				.parseClaimsJws(token)
				.getBody();
	}
}
