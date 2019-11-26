package com.example.blog.controller;

import com.example.blog.entity.Author;
import com.example.blog.entity.Category;
import com.example.blog.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author")

public class AuthorController {

    private final AuthorRepository authorRepository;

@PostMapping
    public ResponseEntity create(@RequestBody Author author) {
    author.setId(UUID.randomUUID().toString());
    Author newAuthor = authorRepository.save(author);
    return ResponseEntity.ok(newAuthor);
    }

@GetMapping
    public ResponseEntity getAll() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }

@GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable String id ){

    Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
        return ResponseEntity.ok(author.get());
        } else {
        return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody Author author) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            if(author.getFirstName() != null){
                existingAuthor.setFirstName(author.getFirstName());
            }
            if(author.getLastName() != null){
                existingAuthor.setLastName(author.getLastName());
            }
            Author savedAuthor = authorRepository.save(existingAuthor);
            return ResponseEntity.ok(savedAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            authorRepository.delete(optionalAuthor.get());
            return ResponseEntity.ok("Delete successful.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


