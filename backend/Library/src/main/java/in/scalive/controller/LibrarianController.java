package in.scalive.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.scalive.dto.BookResponse;
import in.scalive.dto.LibrarianIssuedBookResponse;
import in.scalive.entity.Book;
import in.scalive.service.BookService;
import in.scalive.service.IssueService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
public class LibrarianController {
	private final BookService bookService;
	private final IssueService issueService;
	
	@PostMapping("/books")
	public ResponseEntity<BookResponse> addBook(@RequestBody Book book){
		return ResponseEntity.ok(bookService.addBook(book));
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id){
		bookService.deleteBook(id);
		return ResponseEntity.ok("Book deleted successfully");
	}
	
	@GetMapping("/books")
	public ResponseEntity<List<BookResponse>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@GetMapping("/issued-books")
	public ResponseEntity<List<LibrarianIssuedBookResponse>> getAllIssuedBooks(){
		return ResponseEntity.ok(issueService.getAllIssuedBooks());
	}
}
