package auca.com.question1_library_api.controller;

import auca.com.question1_library_api.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // storing the books
    private List<Book> bookList = new ArrayList<>();

    // initialize sample data
    public BookController() {

        bookList.add(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 1925));
        bookList.add(new Book(2L, "To Kill a Mockingbird", "Harper Lee", "9780061120084", 1960));
        bookList.add(new Book(3L, "1984", "George Orwell", "9780451524935", 1949));
    }

    // GET /api/books - return all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookList;
    }

    // GET /api/books/{id} - return book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {

        Book book = bookList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (book != null) {
            return ResponseEntity.ok(book);  // HTTP 200
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    // GET /api/books/search?title={title} - search books by title (case-insensitive, partial match)
    @GetMapping("/search")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        return bookList.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    // POST /api/books - add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book newBook) {
        // Assign a new ID (simple approach: max id + 1)
        Long newId = bookList.stream().mapToLong(Book::getId).max().orElse(0) + 1;
        newBook.setId(newId);
        bookList.add(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook); // HTTP 201
    }

    // DELETE /api/books/{id} - delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = bookList.removeIf(b -> b.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }
}