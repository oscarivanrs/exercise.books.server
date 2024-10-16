package com.example.ecommerce.books.interfaces;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.books.model.BooksEntity;

@Repository
public interface BookRepository extends MongoRepository<BooksEntity, Long>
{
	List<BooksEntity> findByTitle(String title);
	
	@Query("{ 'title': { $regex: '.*Code.*' } }")
	List<BooksEntity> findByTitleContaining(String title);
}