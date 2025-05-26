package com.app.erp_backend_springboot.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.erp_backend_springboot.models.BookModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/books")
public class BookController {
	private List<BookModel> books = new ArrayList<>(
		Arrays.asList(
			new BookModel("1", "Book A", 100),
			new BookModel("2", "Book B", 300),
			new BookModel("3", "Book C", 500)
		)
	);
    
    @GetMapping
	public List<BookModel> getAllBooks() {
		return books;
	}

	@GetMapping("/{id}")
	public BookModel getBookById(@PathVariable String id) {
		return books.stream()
		.filter(book -> book.getId().equals(id))
		.findFirst()
		.orElseThrow(null);
	}

	@PostMapping
	public List<BookModel> addBook(@RequestBody BookModel book) {
		books.add(book);
		return books;
	}

	@PutMapping("/{id}")
	public List<BookModel> updateBook(@PathVariable String id, @RequestBody BookModel book) {
		for (int i = 0; i < books.size(); i++) {
			if(books.get(i).getId().equals(id)) {
				books.set(i, book);
				return books;
			}
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable String id) {
		books.removeIf(book -> book.getId().equals(id));
	}
	
}
