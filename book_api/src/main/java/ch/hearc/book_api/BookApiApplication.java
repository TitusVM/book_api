package ch.hearc.book_api;

import java.time.LocalDate;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.hearc.book_api.model.Book;
import ch.hearc.book_api.repository.BookRepository;

@SpringBootApplication
public class BookApiApplication {

	@Autowired
	BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookApiApplication.class, args);
	}

	@PostConstruct
	public void startApplication() {

		// create Books
		Book book = new Book("Maniefestation en France ?", "François Beaujoli", "Payot", LocalDate.now());
		Book book2 = new Book("Chevalier D'émeraude 1", "Mireille Magie", "Le rat conteur", LocalDate.now());
		Book book3 = new Book("La Quête Ewilan", "Miguel Richard", "A son conte", LocalDate.now());
		// save the Books
		bookRepository.saveAll(Arrays.asList(book, book2, book3));
	}

}
