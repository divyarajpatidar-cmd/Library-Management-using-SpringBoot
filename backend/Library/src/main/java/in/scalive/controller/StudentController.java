package in.scalive.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.scalive.dto.BookResponse;
import in.scalive.dto.IssuedBookResponse;
import in.scalive.entity.IssuedBook;
import in.scalive.service.BookService;
import in.scalive.service.IssueService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
	
	private final BookService bookService;
	private final IssueService issueService;
	
	@GetMapping("/books")
	public ResponseEntity<List<BookResponse>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@PostMapping("/issue/{bookId}")
	public ResponseEntity<String> issueBook(@PathVariable Long bookId) {
	    return ResponseEntity.ok(issueService.issueBook(bookId));
	}
	
	@PostMapping("/return/{bookId}")
	public ResponseEntity<String> returnBook(@PathVariable Long bookId){
		return ResponseEntity.ok(issueService.returnBook(bookId));
	}
	
	@GetMapping("/my-books")
	public ResponseEntity<List<IssuedBookResponse>> getMyBooks(){
		return ResponseEntity.ok(issueService.getMyIssuedBooks());
	}
}
