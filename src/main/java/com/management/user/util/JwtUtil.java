package com.management.user.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.management.user.models.AuthRequest;
import com.management.user.models.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	@Autowired
    private CustomUserDetailsService service;

    private String secret = "userService";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(AuthRequest user) {
    	UserDetails userDetails = service.loadUserByUsername(user.getUserName());
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> auth =  userDetails.getAuthorities();
        String s = auth.toString().replace("[", "").replace("]", "");
        
		if (s.equalsIgnoreCase("admin"))claims.put("isAdmin", true);
		
		if(s.equalsIgnoreCase("write")) claims.put("isWriteAccess", true);
        return createToken(claims, user.getUserName());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final Claims claims = extractAllClaims(token);
        if(!claims.containsKey("isAdmin") && !claims.containsKey("isWriteAccess")) return false;
            
        if(claims.containsKey("isAdmin") && !claims.get("isAdmin").equals(true)) return false;
        if(claims.containsKey("isWriteAccess") && !claims.get("isWriteAccess").equals(true)) return false;
        
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
