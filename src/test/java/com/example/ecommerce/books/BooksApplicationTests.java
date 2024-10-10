package com.example.ecommerce.books;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksApplicationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void corsWithAnnotation() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.exchange(
				RequestEntity.get(uri("/auth/reachable")).header(HttpHeaders.ORIGIN, "http://localhost:4200").build(),
				String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals("http://localhost:4200", entity.getHeaders().getAccessControlAllowOrigin());
		String greeting = entity.getBody();
		assertEquals("reachable", greeting);
	}

	private URI uri(String path) {
		return restTemplate.getRestTemplate().getUriTemplateHandler().expand(path);
	}
}
