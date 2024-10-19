package an.kondratev.books.repository;

import an.kondratev.books.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Book book = new Book(null, "Test Title", "Test Author", "2023");

        bookRepository.save(book);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(jdbcTemplate, times(1)).update(sqlCaptor.capture(), any(), any(), any());
        assertThat(sqlCaptor.getValue()).contains("INSERT INTO books");
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        bookRepository.delete(id);

        verify(jdbcTemplate, times(1)).update("DELETE FROM books WHERE id = ?", id);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Book expectedBook = new Book();
        expectedBook.setId(id);
        expectedBook.setTitle("Test Title");
        expectedBook.setAuthor("Test Author");
        expectedBook.setPublication_year("2023");

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(List.of(expectedBook));
        Optional<Book> book = bookRepository.findById(id);
        assertThat(book).isPresent();
        assertThat(book.get().getId()).isEqualTo(id);
        assertThat(book.get().getTitle()).isEqualTo("Test Title");
        assertThat(book.get().getAuthor()).isEqualTo("Test Author");
        assertThat(book.get().getPublication_year()).isEqualTo("2023");
    }

    @Test
    public void testFindAll() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(new Book(1L, "Test Title", "Test Author", "2023")));

        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Title");
    }
}