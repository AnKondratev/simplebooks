package an.kondratev.books.service;

import an.kondratev.books.model.Book;
import an.kondratev.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBook() {
        Long id = 1L;
        Book book = new Book(id, "Test Title", "Test Author", "2023");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBook(id);

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getId()).isEqualTo(id);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = List.of(new Book(1L, "Test Title", "Test Author", "2023"));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> allBooks = bookService.getAllBooks();

        assertThat(allBooks).hasSize(1);
        assertThat(allBooks.get(0).getTitle()).isEqualTo("Test Title");
    }

    @Test
    public void testAddBook() {
        Book book = new Book(null, "Test Title", "Test Author", "2023");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertThat(savedBook.getTitle()).isEqualTo("Test Title");
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(1L, "Test Title", "Test Author", "2023");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(book);

        assertThat(updatedBook.getId()).isEqualTo(1L);
    }

    @Test
    public void testDeleteBook() {
        Long id = 1L;

        bookService.deleteBook(id);
        verify(bookRepository, times(1)).delete(id);
    }
}