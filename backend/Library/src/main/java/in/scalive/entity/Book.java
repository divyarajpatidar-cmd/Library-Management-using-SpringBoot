package in.scalive.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	
	@NotBlank
	@Column(nullable = false)
	private String title;
	
	@NotNull
	private String author;
	
	@Column(unique= true)
	private String isbn;
	
	
	private Integer totalQuantity;
	
	private Integer availableQuantity;
}
