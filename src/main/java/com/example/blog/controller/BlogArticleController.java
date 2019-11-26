package com.example.blog.controller;

import com.example.blog.entity.BlogArticle;
import com.example.blog.entity.Category;
import com.example.blog.repository.AuthorRepository;
import com.example.blog.repository.BlogArticleRepository;
import com.example.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import model.BlogArticleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog-article")

public class BlogArticleController {
    private final BlogArticleRepository blogArticleRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody BlogArticle blogArticle) {
        LocalTime localTime = LocalTime.now();
        blogArticle.setId(UUID.randomUUID().toString());
        BlogArticle newBlogArticle = blogArticleRepository.save(blogArticle);
        return ResponseEntity.ok(newBlogArticle);
    }

    @GetMapping("/list")
    public ResponseEntity getAll() {
        List<BlogArticle> blogArticles = blogArticleRepository.findAll();
        return ResponseEntity.ok(blogArticles.stream().sorted(Comparator.comparing(BlogArticle::getCreatDate, Comparator.reverseOrder())));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        Optional<BlogArticle> blogArticle = blogArticleRepository.findById(id);
        if (blogArticle.isPresent()) {
            return ResponseEntity.ok(blogArticle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody BlogArticle blogArticle) {
        Optional<BlogArticle> optionalBlogArticle = blogArticleRepository.findById(id);
        if (optionalBlogArticle.isPresent()) {
            BlogArticle existingBlogArticle = optionalBlogArticle.get();
            if (blogArticle.getName() != null) {
                existingBlogArticle.setName(blogArticle.getName());
            }
            if (blogArticle.getTag() != null) {
                existingBlogArticle.setTag(blogArticle.getTag());
            }
            if (blogArticle.getCreatDate() != null) {
                existingBlogArticle.setCreatDate(blogArticle.getCreatDate());
            }
            if (blogArticle.getModifyDate() != null) {
                existingBlogArticle.setModifyDate(blogArticle.getModifyDate());
            }
            if (blogArticle.getAuthor() != null) {
                existingBlogArticle.setAuthor(authorRepository.getOne(blogArticle.getAuthor().getId()));
            }
            if (blogArticle.getCategory() != null) {
                existingBlogArticle.setCategory(categoryRepository.getOne(blogArticle.getCategory().getId()));
            }
            BlogArticle savedBlogArticle = blogArticleRepository.save(existingBlogArticle);
            return ResponseEntity.ok(savedBlogArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<BlogArticle> optionalBlogArticle = blogArticleRepository.findById(id);
        if (optionalBlogArticle.isPresent()) {
            blogArticleRepository.delete(optionalBlogArticle.get());
            return ResponseEntity.ok("Delete successful.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
