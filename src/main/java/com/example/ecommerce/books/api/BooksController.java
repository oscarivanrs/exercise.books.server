package com.example.ecommerce.books.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.books.interfaces.BookRepository;
import com.example.ecommerce.books.model.BooksEntity;
import com.example.ecommerce.books.model.UsersEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.ecommerce.books.utility.Tracer;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/books")
public class BooksController
{
	@Autowired
	BookRepository repo;
	
	@GetMapping("/reachable")
    public ResponseEntity<String> reachable()
    {
    	return ResponseEntity.ok("reachable");
    }

    @GetMapping("/me")
    public ResponseEntity<UsersEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsersEntity currentUser = (UsersEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
	
	@GetMapping("/list")
	public ResponseEntity<List<BooksEntity>> listBooks()
	{
		Tracer.debug(this.getClass(), "listBooks", "()");
		return ResponseEntity.ok(repo.findAll());
	}
	
	@PostMapping("/add")
	public ResponseEntity<BooksEntity> populate(@RequestBody BooksEntity request)
	{
		BooksEntity saved = repo.save(request);
		return ResponseEntity.created(null).body(saved);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody BooksEntity request)
	{
		List<BooksEntity> titles = repo.findByTitle(request.getTitle());
		Tracer.debug(getClass(), "delete", "found "+titles.size()+" occurrences.");
		for (BooksEntity title : titles)
		{
			Tracer.debug(getClass(), "delete", "Author: "+title.getAuthor());
			if( title.getAuthor().equals(request.getAuthor()) )
			{
				repo.deleteById(title.getId());
				Tracer.debug(getClass(), "delete", "Id: "+title.getId()+" deleted");
				return ResponseEntity.accepted().body("");
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping("/populate")
	public ResponseEntity<List<BooksEntity>> populate(@RequestBody List<BooksEntity> request)
	{
		repo.deleteAll();
		List<BooksEntity> saved = repo.saveAll(request);
		return ResponseEntity.created(null).body(saved);
	}
}
