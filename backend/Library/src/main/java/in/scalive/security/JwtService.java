package in.scalive.security;


import java.lang.invoke.StringConcatFactory;
import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.Function;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.security.web.authentication.ott.DefaultGenerateOneTimeTokenRequestResolver;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private static final String SECRET_KEY="mysecretkeymysecretkeymysecretkey12345";
	private static final long JWT_EXPIRATION = 1000 * 60 *60 * 24;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public boolean isTokenValid(String token,String username) {
		final String extractedUsername = extractUsername(token);
		return extractedUsername.equals(username) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token,Claims::getExpiration)
				.before(new Date());
	}
	
	private<T> T extractClaim(String token,Function<Claims, T> resolver) {
		final Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	private Claims 	extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
