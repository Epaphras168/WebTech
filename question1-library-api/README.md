# Test with Postman

Run the application (main class LibraryApiApplication).
Open Postman and test each endpoint:
GET all books: GET http://localhost:8080/api/books → should return the three sample books.
GET book by ID: GET http://localhost:8080/api/books/1 → returns book with id 1.
Search by title: GET http://localhost:8080/api/books/search?title=great → returns "The Great Gatsby".
Add a new book: POST http://localhost:8080/api/books with JSON body: