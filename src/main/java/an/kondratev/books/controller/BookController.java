package an.kondratev.books.controller;

import an.kondratev.books.model.Book;
import an.kondratev.books.service.BookServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class BookController {
    private final BookServiceInterface service;

    @GetMapping("all_books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = service.getBook(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("new_book")
    public HttpStatus newBook(@RequestBody Book book) {
        service.addBook(book);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.updateBook(book), HttpStatus.OK);
    }

    @DeleteMapping("delete_book/{id}")
    public HttpStatus deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return HttpStatus.OK;
    }
}
