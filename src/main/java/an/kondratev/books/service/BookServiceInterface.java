package an.kondratev.books.service;

import an.kondratev.books.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {
    Optional<Book> getBook(Long id);

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Long id);
}
