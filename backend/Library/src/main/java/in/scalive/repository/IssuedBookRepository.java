package in.scalive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.scalive.entity.Book;
import in.scalive.entity.IssuedBook;
import in.scalive.entity.User;


public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
	List<IssuedBook> findByUser(User user);
	
	List<IssuedBook> findByStatus(String status);
	
	Optional<IssuedBook> findByUserAndBookAndStatus(User user,Book book,String status);
}
