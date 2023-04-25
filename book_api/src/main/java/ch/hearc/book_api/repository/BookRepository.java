package ch.hearc.book_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.hearc.book_api.model.Book;

public interface BookRepository extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

}