package com.example.ecommerce.books.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.books.interfaces.UserRepository;
import com.example.ecommerce.books.model.UsersEntity;
import com.example.ecommerce.books.security.AesEncryptionDecryption;

@Service
public class AuthenticationService 
{
	@Autowired
	AesEncryptionDecryption enc;
	
	@Autowired
	UserRepository repo;

    public UsersEntity signup(UsersEntity user) {
    	user.setPassword(enc.encrypt(user.getPassword()));
        return repo.save(user);
    }

    public UsersEntity authenticate(UsersEntity user) {
    	Optional<UsersEntity> uEntity = repo.findByUsername(user.getUsername());
//		Tracer.debug(this.getClass(), "authenticate", enc.decrypt(uEntity.get().getPassword()) + " == " + request.getPassword());
    	if(enc.decrypt(uEntity.get().getPassword()).equals(user.getPassword()))
    	{
    		return uEntity.get();
    	}
    	else
    		return null;
    }
}
