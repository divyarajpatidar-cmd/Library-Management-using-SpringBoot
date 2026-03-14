package in.scalive.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibrarianIssuedBookResponse {
	
	private String studentName;
	private Long bookId;
	private String bookTitle;
	private LocalDate issueDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private String status;
	private double fine;
	
}
