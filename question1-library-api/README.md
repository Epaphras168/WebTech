# Test with Postman

Run the application (main class LibraryApiApplication).
# Endpoints:
GET all books: GET http://localhost:8080/api/books → should return the three sample books.

GET book by ID: GET http://localhost:8080/api/books/1 → returns book with id 1.

Search by title: GET http://localhost:8080/api/books/search?title=great → returns "The Great Gatsby".

Add a new book: POST http://localhost:8080/api/books with JSON body:

{
    "title": "Brave New World",
    "author": "Aldous Huxley",
    "isbn": "9780060850524",
    "publicationYear": 1932
}

Delete a book: DELETE http://localhost:8080/api/books/2 → returns 204. Try to GET that ID again → 404.
