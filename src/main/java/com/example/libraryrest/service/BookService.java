package com.example.libraryrest.service;

import com.example.libraryrest.exception.NotFoundException;
import com.example.libraryrest.model.Author;
import com.example.libraryrest.model.Book;
import com.example.libraryrest.repo.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;
    private final AuthorService authorService;

    public BookService(BookRepository repo, AuthorService authorService) {
        this.repo = repo;
        this.authorService = authorService;
    }

    public List<Book> findAll() {
        return repo.findAll();
    }

    public List<Book> findByAuthorId(Long authorId) {
        return repo.findByAuthorId(authorId);
    }

    public Book findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
    }

    public Book create(Book book, Long authorId) {
        Author author = authorService.findById(authorId);
        book.setId(null);
        book.setAuthor(author);
        return repo.save(book);
    }

    public Book update(Long id, Book book, Long authorId) {
        Book existing = findById(id);
        Author author = authorService.findById(authorId);

        existing.setTitle(book.getTitle());
        existing.setIsbn(book.getIsbn());
        existing.setPublishedYear(book.getPublishedYear());
        existing.setAuthor(author);

        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Book not found: " + id);
        }
        repo.deleteById(id);
    }
}
