package com.example.ecommerce.books.api;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.books.interfaces.UserRepository;
import com.example.ecommerce.books.model.LoginResponse;
import com.example.ecommerce.books.model.UsersEntity;
import com.example.ecommerce.books.payload.ErrorResp;
import com.example.ecommerce.books.services.AuthenticationService;
import com.example.ecommerce.books.services.JwtService;
import com.example.ecommerce.books.utility.Tracer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public UserController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UsersEntity request)
	{
		try {
			Tracer.debug(this.getClass(), "signup", (new ObjectMapper()).writeValueAsString(request));
		} catch (JsonProcessingException e) {
			Tracer.error(this.getClass(), "signup", e.getMessage());
		}
		if((repo.findByUsername(request.getUsername())).isEmpty())
		{
			authenticationService.signup(request);
			return ResponseEntity.created(null).body("");
		} else
			try {
				return ResponseEntity.badRequest().body((new ObjectMapper()).writeValueAsString(new ErrorResp("Username already exists")));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body("");
			}
	}
		
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody UsersEntity request)
	{
		try {
			Tracer.debug(this.getClass(), "login", (new ObjectMapper()).writeValueAsString(request));
		} catch (JsonProcessingException e) {
			Tracer.error(this.getClass(), "login", e.getMessage());
		}
		Optional<UsersEntity> uEntity = repo.findByUsername(request.getUsername());
		if(uEntity.isPresent())
		{
			UsersEntity authenticatedUser = authenticationService.authenticate(request);
			if( authenticatedUser!=null )
			{
				String jwtToken = jwtService.generateToken(authenticatedUser);
				LoginResponse loginResponse = new LoginResponse();
				loginResponse.setToken(jwtToken);
				loginResponse.setExpiresIn(jwtService.getExpirationTime());
				return ResponseEntity.ok(loginResponse);
			}
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
