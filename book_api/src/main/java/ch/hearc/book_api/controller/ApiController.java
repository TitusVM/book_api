package ch.hearc.book_api.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.book_api.model.Book;
import ch.hearc.book_api.model.BookResponse;
import ch.hearc.book_api.service.CatalogService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	CatalogService catalogService;

	@Autowired
	HttpSession userSession;

	/*************************************************************
	 * 
	 * Restful Books
	 * 
	 * @throws URISyntaxException
	 *
	 **************************************************************/

	@GetMapping("/books")
	public ResponseEntity<List<BookResponse>> showBooks() {
		HttpHeaders responseHeader = new HttpHeaders();
		try {
			List<Book> books = catalogService.fetchBooks();
			List<BookResponse> booksResponse = new ArrayList<BookResponse>();
			for (Book book : books) {
				booksResponse.add(new BookResponse(book));
			}
			return new ResponseEntity<List<BookResponse>>(booksResponse, responseHeader, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<BookResponse>>(responseHeader, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<List<BookResponse>>(responseHeader, HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<BookResponse> showBook(@PathVariable("id") int id) throws URISyntaxException { // insert
		// Id starts at 1
		HttpHeaders responseHeader = new HttpHeaders();
		try {
			BookResponse book = new BookResponse(catalogService.getBookById((long) id));
			System.out.println(book);
			// URI location = new URI(""); Pas nécessaire pour un get, intéressant de
			// préciser l'emplacement de la nouvelle ressource p.ex.
			// responseHeader.setLocation(location);
			// responseHeader.set("MyResponseHeader", "MyValue"); Pas nécessaire, Spring le
			// fait tout seul
			return new ResponseEntity<BookResponse>(book, responseHeader, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			BookResponse book = new BookResponse(new Book());
			return new ResponseEntity<BookResponse>(book, responseHeader, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			BookResponse book = new BookResponse(new Book());
			return new ResponseEntity<BookResponse>(book, responseHeader, HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@PostMapping("/books")
	public ResponseEntity<BookResponse> newBook(@RequestBody Book book) {
		HttpHeaders responseHeader = new HttpHeaders();
		try {
			book = catalogService.createBook(book);
			URI location = new URI("/books/" + book.getId());
			responseHeader.setLocation(location);
			System.out.println("Created");
			return new ResponseEntity<BookResponse>(new BookResponse(book), responseHeader, HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<BookResponse>(null, responseHeader, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<BookResponse>(null, responseHeader, HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<String> replaceBook(@RequestBody Book book, @PathVariable("id") long id) {
		HttpHeaders responseHeader = new HttpHeaders();
		try {
			// if no books have this id a new one is created
			book.setId(id);
			book = catalogService.createBook(book);
			URI location = new URI("/books/" + book.getId());
			responseHeader.setLocation(location);
			return new ResponseEntity<String>("Book replaced successfully", responseHeader, HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<String>("No such book", responseHeader, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Why are you like this ?", responseHeader, HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
		HttpHeaders responseHeader = new HttpHeaders();
		try {
			catalogService.deleteBook(id);
			return new ResponseEntity<String>("Book deleted successfully", responseHeader, HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<String>("No such book", responseHeader, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Why are you like this ?", responseHeader, HttpStatus.I_AM_A_TEAPOT);
		}
	}
}
