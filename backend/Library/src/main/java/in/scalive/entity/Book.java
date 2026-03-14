package in.scalive.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="books")
@Builder
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	private String author;
	
	@Column(unique= true)
	private String isbn;
	
	private Integer totalQuantity;
	
	private Integer availableQuantity;
}
