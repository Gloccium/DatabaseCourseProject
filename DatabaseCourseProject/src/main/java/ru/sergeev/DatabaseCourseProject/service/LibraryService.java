package ru.sergeev.DatabaseCourseProject.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.sergeev.DatabaseCourseProject.model.Author;
import ru.sergeev.DatabaseCourseProject.model.Book;
import ru.sergeev.DatabaseCourseProject.model.Genre;
import ru.sergeev.DatabaseCourseProject.repository.AuthorRepository;
import ru.sergeev.DatabaseCourseProject.repository.BookRepository;
import ru.sergeev.DatabaseCourseProject.repository.GenreRepository;

import java.util.List;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public LibraryService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    public Book addBook(String title, int year, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName)
                .orElseGet(() -> authorRepository.save(new Author(authorName, null))); // Для простоты дату рождения не указываем

        Genre genre = genreRepository.findByName(genreName)
                .orElseGet(() -> genreRepository.save(new Genre(genreName)));

        Book newBook = new Book(title, year, author, genre);
        return bookRepository.save(newBook);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Книга с id " + id + " не найдена");
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book updateBookTitle(Long id, String newTitle) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с id " + id + " не найдена"));
        book.setTitle(newTitle);
        return bookRepository.save(book); // JPA сам поймет, что нужно сделать update
    }

    public List<Book> findBooksPublishedAfter(int year) {
        return bookRepository.findByPublicationYearGreaterThan(year);
    }

    public List<Book> searchBooks(String authorName, String titleKeyword) {
        return bookRepository.findBooksByAuthorNameAndTitleKeyword(authorName, titleKeyword);
    }
}