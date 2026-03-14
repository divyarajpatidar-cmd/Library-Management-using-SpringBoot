package in.scalive.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.scalive.repository.BookRepository;
import in.scalive.repository.IssuedBookRepository;
import in.scalive.repository.UserRepository;
import in.scalive.entity.User;
import in.scalive.dto.IssuedBookResponse;
import in.scalive.dto.LibrarianIssuedBookResponse;
import in.scalive.entity.Book;
import in.scalive.entity.IssuedBook;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final BookRepository bookRepository;
	private final UserRepository userRepository;
	private final IssuedBookRepository issuedBookRepository;
	
	public String issueBook(Long bookId) {
		String username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException("Book not found"));
		
		if(book.getAvailableQuantity()<=0) {
			throw new RuntimeException("Book not available");
		}
		
		boolean alreadyIssued = issuedBookRepository
				.findByUserAndBookAndStatus(user, book, "ISSUED")
				.isPresent();
		
		if(alreadyIssued) {
			throw new RuntimeException("You already issued this book");
		}
		
		LocalDate issueDate = LocalDate.now();
		LocalDate dueDate = issueDate.plusDays(15);
		
		IssuedBook issuedBook = IssuedBook.builder()
				.user(user)
				.book(book)
				.issueDate(issueDate)
				.dueDate(dueDate)
				.status("ISSUED")
				.fine(0)
				.build();
		
		book.setAvailableQuantity(book.getAvailableQuantity() -1);
		bookRepository.save(book);
		issuedBookRepository.save(issuedBook);
		
		return "Book issued succesfully. Due date: "+dueDate;
	}
	
	public String returnBook(Long bookId) {
		String username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException("Book not found"));
		
		IssuedBook issuedBook = issuedBookRepository
				.findByUserAndBookAndStatus(user, book, "ISSUED")
				.orElseThrow(() -> new RuntimeException("This book is not currently issued by you"));
		
		LocalDate returnDate = LocalDate.now();
		LocalDate dueDate = issuedBook.getDueDate();
		
		double fine=0;
		
		if(returnDate.isAfter(dueDate)) {
			long lateDays = java.time.temporal.ChronoUnit.DAYS
					.between(dueDate, returnDate);
			fine = lateDays * 10;
		}
		
		issuedBook.setReturnDate(returnDate);
		issuedBook.setStatus("RETURNED");
		issuedBook.setFine(fine);
		
		book.setAvailableQuantity(book.getAvailableQuantity() + 1 );
		bookRepository.save(book);
		issuedBookRepository.save(issuedBook);
		
		if(fine > 0) {
			return "Book returned successfully. Fine: Rs."+fine;
		}
		
		return "Book returned succesfully. No fine.";
		
	}
	
	public List<IssuedBookResponse> getMyIssuedBooks() {

	    String username = SecurityContextHolder
	            .getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<IssuedBook> issuedBooks =
	            issuedBookRepository.findByUser(user);

	    return issuedBooks.stream()
	            .map(issuedBook -> IssuedBookResponse.builder()
	            		.bookId(issuedBook.getBook().getId())
	                    .bookTitle(issuedBook.getBook().getTitle())
	                    .issueDate(issuedBook.getIssueDate())
	                    .dueDate(issuedBook.getDueDate())
	                    .returnDate(issuedBook.getReturnDate())
	                    .status(issuedBook.getStatus())
	                    .fine(issuedBook.getFine())
	                    .build())
	            .toList();
	}
	
	public List<LibrarianIssuedBookResponse> getAllIssuedBooks() {

	    List<IssuedBook> issuedBooks = issuedBookRepository.findAll();

	    return issuedBooks.stream()
	            .map(issuedBook ->
	                    LibrarianIssuedBookResponse.builder()
	                            .studentName(issuedBook.getUser().getFullName())
	                            .bookId(issuedBook.getBook().getId())
	                            .bookTitle(issuedBook.getBook().getTitle())
	                            .issueDate(issuedBook.getIssueDate())
	                            .dueDate(issuedBook.getDueDate())
	                            .returnDate(issuedBook.getReturnDate())
	                            .status(issuedBook.getStatus())
	                            .fine(issuedBook.getFine())
	                            .build()
	            )
	            .collect(java.util.stream.Collectors.toList());
	}
}
