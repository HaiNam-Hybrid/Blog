package com.example.blog.repository;

import com.example.blog.entity.BlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogArticleRepository extends JpaRepository<BlogArticle, String> {

}
