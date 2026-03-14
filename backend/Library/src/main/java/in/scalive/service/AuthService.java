package in.scalive.service;

import java.util.Set;

import org.antlr.v4.runtime.misc.TestRig;
import org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.scalive.dto.AuthResponse;
import in.scalive.dto.LoginRequest;
import in.scalive.dto.RegisterRequest;
import in.scalive.entity.Role;
import in.scalive.entity.User;
import in.scalive.repository.RoleRepository;
import in.scalive.repository.UserRepository;
import in.scalive.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private  final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthResponse register (RegisterRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("User already exists");
		}
		
		Role role = roleRepository.findByName("ROLE_"+request.getRole().toUpperCase())
				.orElseThrow(() -> new RuntimeException("Role not found"));
		
		System.out.println("Register attempt: " + request.getUsername());
	    System.out.println("Password entered: " + request.getPassword());
	    System.out.println("Role: "+request.getRole());
	    System.out.println("Fullname: "+request.getFullName());
		User user = User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.fullName(request.getFullName())
				.roles(Set.of(role))
				.enabled(true)
				.build();
		
		userRepository.save(user);
		System.out.println("role.getName(): "+role.getName());
		System.out.println("request.getRole(): "+request.getRole());
		String token = jwtService.generateToken(request.getUsername());
		System.out.println("token: "+token);
		System.out.println("role: "+role.getName());
		return new AuthResponse(token,role.getName());
	}
	
	public AuthResponse login(LoginRequest request) {
		
		System.out.println("Login attempt: " + request.getUsername());
	    System.out.println("Password entered: " + request.getPassword());
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), 
						request.getPassword())
				
				);
		
		User user = userRepository.findByUsername(request.getUsername())
	            .orElseThrow();
		
		String token = jwtService.generateToken(request.getUsername());
		
		String role = user.getRoles().iterator().next().getName();
		System.out.println("token: "+token);
		System.out.println("role : "+ role);
		return new AuthResponse(token,role);
	}
	
}
