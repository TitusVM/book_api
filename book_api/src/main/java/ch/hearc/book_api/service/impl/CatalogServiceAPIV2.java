package ch.hearc.book_api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import ch.hearc.book_api.jms.log_producer.Log;
import ch.hearc.book_api.model.Book;
import ch.hearc.book_api.repository.BookRepository;
import ch.hearc.book_api.service.CatalogService;

@Service
public class CatalogServiceAPIV2 implements CatalogService {

	private BlockingQueue<Log> logs;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	JmsTemplate jmsTemplate;

	public CatalogServiceAPIV2() {
		logs = new LinkedBlockingQueue<>();
	}

	@Override
	public List<Book> fetchBooks() {
		logs.add(new Log("READ", "all books"));
		List<Book> result = new ArrayList<Book>();
		bookRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public Book createBook(Book book) {
		logs.add(new Log("CREATE", book.getName()));
        return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Book book) {
		logs.add(new Log("UPDATE", book.getName()));
        bookRepository.save(book);
        return book;
	}

	@Override
	public void deleteBook(Long id) {
		logs.add(new Log("DELETE", " book with id : " + id));
        bookRepository.deleteById(id);
	}

	@Override
	public Book getBookById(Long id) {
		logs.add(new Log("READ", id.toString()));
        return bookRepository.findById(Long.valueOf(id)).get();
	}

	@Override
	public BlockingQueue<Log> getLogs() {
		return this.logs;
	}
}
