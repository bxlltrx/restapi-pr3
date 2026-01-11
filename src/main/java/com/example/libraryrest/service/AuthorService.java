package com.example.libraryrest.service;

import com.example.libraryrest.exception.NotFoundException;
import com.example.libraryrest.model.Author;
import com.example.libraryrest.repo.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repo;

    public AuthorService(AuthorRepository repo) {
        this.repo = repo;
    }

    public List<Author> findAll() {
        return repo.findAll();
    }

    public Author findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id));
    }

    public Author create(Author author) {
        author.setId(null);
        return repo.save(author);
    }

    public Author update(Long id, Author author) {
        Author existing = findById(id);
        existing.setFullName(author.getFullName());
        existing.setCountry(author.getCountry());
        existing.setBirthDate(author.getBirthDate());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Author not found: " + id);
        }
        repo.deleteById(id);
    }
}
