package com.example.ecommerce.books.interfaces;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.books.model.UsersEntity;

@Repository
public interface UserRepository extends CrudRepository<UsersEntity, Long>
{
	Optional<UsersEntity> findByUsername(String username);
}