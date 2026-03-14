package in.scalive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import in.scalive.dto.BookResponse;
import in.scalive.entity.Book;
import in.scalive.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	
	public BookResponse addBook(@RequestBody Book book) {
		if(bookRepository.existsByIsbn(book.getIsbn())) {
			throw new RuntimeException("Book with this ISBN already exists");
		}
		book.setAvailableQuantity(book.getTotalQuantity());
		Book savedBook = bookRepository.save(book);	
		return mapToResponse(savedBook);
		}
	
	public void deleteBook(Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException("Book not found"));
		bookRepository.delete(book);
	}
	
	public List<BookResponse> getAllBooks(){
		return bookRepository.findAll()
				.stream()
				.map(this::mapToResponse )
				.toList();
	}
	
	public BookResponse mapToResponse(Book book) {
		return BookResponse.builder()
				.id(book.getId())
				.title(book.getTitle())
				.author(book.getAuthor())
				.isbn(book.getIsbn())
				.totalQuantity(book.getTotalQuantity())
				.availableQuantity(book.getAvailableQuantity())
				.build();
	}
}
