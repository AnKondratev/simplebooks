package an.kondratev.books.repository;

import an.kondratev.books.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public Book save(Book book) {
        String save = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
        jdbcTemplate.update(save, book.getTitle(), book.getAuthor(), book.getPublication_year());
        return book;
    }

    public void delete(Long id) {
        String del = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(del, id);
    }

    public Optional<Book> findById(Long id) {
        String findByIdQuery = "SELECT * FROM books WHERE id = ?";
        List<Book> books = jdbcTemplate.query(findByIdQuery, new Object[]{id}, (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPublication_year(rs.getString("publication_year"));
            return book;
        });
        return books.stream().findFirst();
    }

    public List<Book> findAll() {
        String all = "SELECT * FROM \"books\"";
        return jdbcTemplate.query(all, (rs, rowNum) ->
                new Book(rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publication_year")));
    }
}