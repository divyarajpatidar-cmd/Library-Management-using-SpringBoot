package in.scalive.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="issued_books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id" , nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="book_id" , nullable=false)
	private Book book;
	
	private LocalDate issueDate;
	
	private LocalDate dueDate;
	
	private LocalDate returnDate;
	
	private double fine;
	
	private String status;
}
