package ch.hearc.book_api.service;

import java.util.List;

import ch.hearc.book_api.model.Book;

public interface CatalogService {

	// Book
	public List<Book> fetchBooks();

	public Book createBook(Book book);

	public Book updateBook(Book book);

	public void deleteBook(Long id);

	public Book getBookById(Long id);

}
