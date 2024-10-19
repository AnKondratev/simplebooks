package an.kondratev.books.service;

import an.kondratev.books.model.Book;
import an.kondratev.books.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class BookService implements BookServiceInterface {

    private BookRepository repository;

    @Override
    public Optional<Book> getBook(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return repository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        repository.delete(id);
    }
}
